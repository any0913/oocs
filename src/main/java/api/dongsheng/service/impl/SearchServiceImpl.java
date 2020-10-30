package api.dongsheng.service.impl;

import api.dongsheng.exception.BaseException;
import api.dongsheng.model.dao.ChannelDao;
import api.dongsheng.model.dao.MusicDao;
import api.dongsheng.model.dao.TencentActivateDao;
import api.dongsheng.model.dto.ResourceDTO;
import api.dongsheng.model.entity.Channel;
import api.dongsheng.model.entity.TencentActivate;
import api.dongsheng.common.RetCode;
import api.dongsheng.service.ResourceService;
import api.dongsheng.service.SearchService;
import api.dongsheng.util.StringUtil;
import api.dongsheng.util.TxyyUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:16
 **/
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private MusicDao musicDao;
    @Autowired
    private TencentActivateDao txyyAuthDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private ResourceService resourceService;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private Gson gson;


    /**
     * 搜索
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> search(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>(2);
        ResourceDTO resourceDTO = null;
        Channel channel = channelDao.getChannel(Long.valueOf(map.get(StringUtil.CHANNELID).toString()));
        map.put("channelType",channel.getChannelType());
        if(channel.getResType() == 1){
            // 只有酷狗音乐资源，透传查酷狗搜索
            result = searchKgWordRecommend(map);
        }else if(channel.getResType() == 2){
            // 没有酷狗资源，直接动声es搜索
            resourceDTO = searchDsWordRecommend(map);
        }else{
            // 混合资源，先搜动声es，不存在，搜酷狗
            resourceDTO = searchDsWordRecommend(map);
            if(resourceDTO == null || resourceDTO.getId() == null){
                result = searchKgWordRecommend(map);
            }
        }
        if(!result.containsKey("code")){
            if(resourceDTO != null){
                // 根据设备号查询绑定的酷狗音乐帐号
                TencentActivate auth = txyyAuthDao.getTencentActivateBySn(map.get("sn").toString());
                if(auth != null){
                    map.put("userId",auth.getKguserId());
                    map.put("token",auth.getToken());
                }
                map.put("musicId",resourceDTO.getId());
                map.put("useType",map.get("type"));
                // 搜索到音频，鉴权并获取播放地址
                result = resourceService.entityPath(map);
            }else{
                result.put("code", RetCode.MUSIC_NOT_FOUNT.getCode());
                result.put("msg",RetCode.MUSIC_NOT_FOUNT.getMsg());
            }
        }
        return result;
    }

    /**
     * 动声es搜索
     * @param map
     */
    private ResourceDTO searchDsWordRecommend(Map<String, Object> map) {
        ResourceDTO resourceDTO = null;
        String name = map.get("name").toString();
        if(!map.containsKey(StringUtil.ARTIST)){
            // 当作者为空已名称搜索
            resourceDTO = getResByName(name);
        }else{
            // 否则按当前歌手/作者下的名称搜索
            String artist = map.get(StringUtil.ARTIST).toString();
            resourceDTO = getResByNameAndArtist(name,artist);
        }
        return resourceDTO;
    }

    /**
     * 酷狗搜索
     * @param map
     * @return
     */
    private Map<String, Object> searchKgWordRecommend(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>(2);
        // 根据设备号查询绑定的酷狗音乐帐号
        TencentActivate auth = txyyAuthDao.getTencentActivateBySn(map.get("sn").toString());
        // 搜索成功，验证是否绑定权限
        if(auth != null) {
            String name = map.get("name").toString();
            String word = null;
            if(map.containsKey(StringUtil.ARTIST)){
                String artist = map.get(StringUtil.ARTIST).toString();
                word = artist +"-"+ name;
            }else{
                word = name;
            }
            map.put("userId",auth.getKguserId());
            map.put("token",auth.getToken());
            String data = TxyyUtil.getWordRecommend(map,word);
            JSONObject jsonObject = JSONObject.parseObject(data);
            if(jsonObject.getInteger("status") == 1){
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("song");
                if(jsonArray.size() > 0){
                    JSONObject music =jsonArray.getJSONObject(0);
                    String payUrl = TxyyUtil.getPlayUrl(music.getString("hash"),music.getLong("albumid").intValue(),map);
                    if(payUrl != null){
                        JSONObject json = JSONObject.parseObject(payUrl);
                        if(json.getInteger("status") == 1){
                            JSONObject jb = JSONObject.parseObject(json.getString("data"));
                            result.put("code",0);
                            result.put("playUrl",jb.getString("url"));
                        }else{
                            result.put("code",json.getInteger("error_code"));
                        }
                    }
                }else{
                    throw new BaseException(RetCode.NO_AUDIO_FOUND.getCode(), RetCode.NO_AUDIO_FOUND.getMsg());
                }
            }
        }else{
            throw new BaseException(RetCode.DEVICE_NO_ACTIVATE.getCode(), RetCode.DEVICE_NO_ACTIVATE.getMsg());
        }
        return result;
    }

    /**
     * 根据内容名称搜索
     * @param name 内容名称
     * @return ResourceDTO
     */
    public ResourceDTO getResByName(String name) {
        ResourceDTO resourceDTO = null;
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("name", name));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest("test-resource");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if ((searchResponse != null ? searchResponse.getHits().getHits().length : 0) > 0) {
            resourceDTO = gson.fromJson(searchResponse.getHits().getHits()[0].getSourceAsString(), ResourceDTO.class);
        }
        log.info(String.format("getResByName, name:{%s}, result:{%s}", name, gson.toJson(resourceDTO)));
        return resourceDTO;
    }

    /**
     * 根据内容艺术家和名称搜索
     * @param name 内容名称
     * @param artist 艺术家
     * @return ResourceDTO
     */
    public ResourceDTO getResByNameAndArtist(String name, String artist) {
        ResourceDTO resourceDTO = null;
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", name));
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("nickname", artist));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest("test-resource");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        if ((searchResponse != null ? searchResponse.getHits().getHits().length : 0) > 0) {
            resourceDTO = gson.fromJson(searchResponse.getHits().getHits()[0].getSourceAsString(), ResourceDTO.class);
        }
        log.info(String.format("getResByNameAndArtist, name:{%s}, artist:{%s}, result:{%s}", name, artist, gson.toJson(resourceDTO)));
        return resourceDTO;
    }

}
