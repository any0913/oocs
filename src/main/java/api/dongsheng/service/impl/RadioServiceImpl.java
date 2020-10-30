package api.dongsheng.service.impl;


import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.dao.ChannelDao;
import api.dongsheng.model.dao.MusicDao;
import api.dongsheng.model.dao.RadioDao;
import api.dongsheng.model.entity.*;
import api.dongsheng.model.vo.ProgramVO;
import api.dongsheng.model.vo.RadioVO;
import api.dongsheng.service.RadioService;
import api.dongsheng.util.QtfmUtil;
import api.dongsheng.util.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:16
 **/
@Slf4j
@Service
public class RadioServiceImpl implements RadioService {


    @Autowired
    private RadioDao radioDao;
    @Autowired
    private MusicDao musicDao;
    @Autowired
    private ChannelDao channelDao;

    /**
     * 根据分类获取电台
     * @param map
     * @return
     */
    @Override
    public List<RadioVO> getRadio(Map<String, Object> map) {
        List<RadioVO> radioVOS = new ArrayList<>();
        //判断渠道是否有当前分类的权限
        ChannelCategory category = channelDao.getChannelCategory(map);
        if(category != null){
            // 根据渠道id获取渠道数据类型
            ChannelData channelData = channelDao.getChannelDataBySourceId(map);
            if(channelData != null){
                if (channelData.getData_type() > 0) {
                    map.put("isFree", channelData.getData_type());
                }
                // 获取当前渠道来源下分类专辑
                getPageInfo(map,map);
                List<Radio> radios = radioDao.getRadio(map);
                PageInfo<Radio> pageInfo = new PageInfo<>();
                for(Radio radio : radios){
                    RadioVO radioVO = new RadioVO();
                    BeanUtils.copyProperties(radio,radioVO);
                    radioVO.setId(radio.getThirdId());
                    radioVOS.add(radioVO);
                }
            }else{
                throw new BaseException(RetCode.CHANNEL_NOT_ALLOW.getCode(), RetCode.CHANNEL_NOT_ALLOW.getMsg());
            }
        }else{
            throw new BaseException(RetCode.CHANNEL_NOT_ALLOW.getCode(), RetCode.CHANNEL_NOT_ALLOW.getMsg());
        }
        return radioVOS;
    }


    /**
     * 获取电台下节目
     * @param params
     * @return
     */
    @Override
    public List<ProgramVO> getProgram(Map<String, Object> params) {
        Long radioId = Long.valueOf(params.get(StringUtil.RADIOID).toString());
        List<ProgramVO> list = new ArrayList<>();
        Radio radio = radioDao.getRadioById(params);
        if (radio != null) {
            if (getChannelSource(params, radio.getSourceId(), radio.getIsFree())) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("albumId", radioId);
                map.put("sourceId", params.get(StringUtil.SOURCEID));
                getPageInfo(map,params);
                List<Music> musics = musicDao.findMusicList(map);
                PageInfo<Music> pageInfo = new PageInfo<>(musics);
                for (Music music : musics) {
                    ProgramVO programVO = new ProgramVO();
                    BeanUtils.copyProperties(music, programVO);
                    programVO.setRadioId(music.getAlbumId());
                    list.add(programVO);
                }
                // 添加访问记录
                log.info("<<<<<<<<<<<<<<<<<"+params.get(StringUtil.CHANNELID)+"渠道获取节目列表成功,共获取" + radioId + "电台下:" + list.size() + "条.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            } else {
                throw new BaseException(RetCode.CHANNEL_NOT_ALLOW.getCode(), RetCode.CHANNEL_NOT_ALLOW.getMsg());
            }
        } else {
            throw new BaseException(RetCode.ALBUM_NOT_FOUNT.getCode(), RetCode.ALBUM_NOT_FOUNT.getMsg());
        }
        return list;
    }

    /**
     * 获取电台播放连接
     * @param radioId
     * @return
     */
    @Override
    public RetResult getRadioUrl(String radioId) {
        RetResult retResult = new RetResult();
        Music music = new Music();
        music.setType(3);
        music.setAlbumId(Long.valueOf(radioId));
        music.setIsFree(1);
        music.setSourceId(3);
        String value = QtfmUtil.getPlayURL(music,null);
        JSONObject json = JSONObject.parseObject(value);
        if(json.getInteger("errcode") == 0){
            JSONObject jsonObject = new JSONObject();
            JSONObject data = json.getJSONObject("data");
            JSONArray array = data.getJSONArray("editions");
            if(array.size() > 1){
                for(int i = 0 ;i<array.size();i++){
                    JSONObject editions = array.getJSONObject(i);
                    if(editions.getInteger("bitrate") == 64){
                        Object urls = editions.getJSONArray("urls").get(0);
                        jsonObject.put("urls",urls);
                    }
                }
            }else{
                Object urls = array.getJSONObject(0).getJSONArray("urls").get(0);
                jsonObject.put("urls",urls);
            }
            jsonObject.put("expire",data.getInteger("expire"));
            String voice = "http://wbso.ihomebot.cn/resource/radio/" + radioId+".mp3";
            jsonObject.put("voice",voice);
            retResult.setData(jsonObject);
        }else{
            retResult.setCode(json.getInteger("errcode"));
            retResult.setMsg(json.getString("errmsg"));
        }
        return retResult;
    }

    @Override
    public List<Integer> getRadioList(Integer categoryId) {
        return radioDao.getRadioList(categoryId);
    }

    /**
     * 判断是否有获取当前资源的权限
     *
     * @param map
     * @return
     */
    public boolean getChannelSource(Map<String, Object> map, Integer sourceId, Integer isFree) {
        boolean flag = false;
        // 获取当前渠道的数据类型
        map.put("sourceId",sourceId);
        ChannelData channelData = channelDao.getChannelDataBySourceId(map);
        if (channelData != null) {
            // 当该渠道来源为全部
            if (channelData.getChannel_source() == 0) {
                // 数据类型为全部，返回true
                if (channelData.getData_type() == 0) {
                    flag = true;
                } else if (channelData.getData_type().equals(isFree)) {
                    // 或者数据类型等于获取的音频数据类型
                    flag = true;
                }
            }
            // 当来源相同
            if (channelData.getChannel_source().equals(sourceId)) {
                // 数据类型为全部，也返回true
                if (channelData.getData_type() == 0) {
                    flag = true;
                } else if (channelData.getData_type().equals(isFree)) {
                    // 或者数据类型等于获取的音频数据类型
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 获取分页
     * @param map
     * @param params
     */
    private void getPageInfo(Map<String,Object> map,Map<String,Object> params){
        if(!params.containsKey("pageSize")){
            map.put("pageSize",30);
        }else{
            Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
            if(pageSize <= 500){
                map.put("pageSize",pageSize);
            }else{
                map.put("pageSize",30);
            }
        }
        if(!params.containsKey("pageNum")){
            map.put("pageNum",1);
        }else{
            map.put("pageNum",Integer.valueOf(params.get("pageNum").toString()));
        }
    }
}
