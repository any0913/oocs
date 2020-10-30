package api.dongsheng.service.impl;

import api.dongsheng.common.RetResult;
import api.dongsheng.model.dao.*;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.entity.*;
import api.dongsheng.common.RetCode;
import api.dongsheng.service.ResourceService;
import api.dongsheng.util.*;
import api.dongsheng.model.vo.AlbumVO;
import api.dongsheng.model.vo.MusicVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/7 15:34
 **/
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private MusicDao musicDao;
    @Autowired
    private TencentActivateDao tencentActivateDao;


    @Value("${service.license.url}")
    private String serviceLicenseUrl;

    @Value("${rsaCipher}")
    private String rsaCipher;

    /**
     * 获取所有分类
     *
     * @param params
     * @return
     */
    @Override
    public List<ChannelCategory> getCategory(Map<String, Object> params) {
        List<ChannelCategory> list = channelDao.findChannelCategoryList(params);
        log.info(">>>>>>>>>>>>>>>>>>>>获取分类成功,共获取:" + list.size() + "条.>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return list;
    }


    /**
     * 获取分类下专辑
     *
     * @param params
     * @return
     */
    @Override
    public List<AlbumVO> albumTypeList(Map<String, Object> params) {
        //判断渠道是否有当前分类的权限
        ChannelCategory category = channelDao.getChannelCategory(params);
        List<AlbumVO> list = new ArrayList<>();
        if (category != null) {
            // 根据渠道id获取渠道数据类型
            ChannelData channelData = channelDao.getChannelDataBySourceId(params);
            List<Album> albums = new ArrayList<>();
            if (channelData != null) {
                if (channelData.getData_type() > 0) {
                    params.put("isFree", channelData.getData_type());
                }
                // 获取当前渠道来源下分类专辑
                getPageInfo(params,params);
                List<Album> albumList = albumDao.findAlbumList(params);
                PageInfo<Album> pageInfo = new PageInfo<>();
                albums.addAll(albumList);
            }
            if (albums.size() > 0) {
                for (Album album : albums) {
                    AlbumVO albumVO = new AlbumVO();
                    BeanUtils.copyProperties(album, albumVO);
                    albumVO.setAlbumId(album.getThirdId());
                    list.add(albumVO);
                }
                // 添加访问记录
                log.info(">>>>>>>>>>>>>>>>>>>>获取专辑成功,共获取" + category.getCategoryId() + "分类下:" + list.size() + "条.>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }
        } else {
            throw new BaseException(RetCode.CHANNEL_NOT_ALLOW.getCode(), RetCode.CHANNEL_NOT_ALLOW.getMsg());
        }
        return list;
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


    /**
     * 获取专辑下资源
     *
     * @param params
     * @return
     */
    @Override
    public List<MusicVO> musicList(Map<String, Object> params) {
        Long albumId = Long.valueOf(params.get("albumId").toString());
        List<MusicVO> list = new ArrayList<>();
        Album album = albumDao.findAlbum(params);
        if (album != null) {
            if (getChannelSource(params, album.getSourceId(), album.getIsFree())) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("albumId", albumId);
                map.put("sourceId", params.get(StringUtil.SOURCEID));
                getPageInfo(map,params);
                List<Music> musics = musicDao.findMusicList(map);
                PageInfo<Music> pageInfo = new PageInfo<>(musics);
                if (musics.size() > 0) {
                    for (Music music : musics) {
                        MusicVO musicVO = new MusicVO();
                        BeanUtils.copyProperties(music, musicVO);
                        musicVO.setSyncOrder(album.getSyncOrder());
                        list.add(musicVO);
                    }
                    // 添加访问记录
                    log.info("<<<<<<<<<<<<<<<<<"+params.get(StringUtil.CHANNELID)+"渠道获取音频列表成功,共获取" + albumId + "专辑下:" + list.size() + "条.");
                }
            } else {
                throw new BaseException(RetCode.CHANNEL_NOT_ALLOW.getCode(), RetCode.CHANNEL_NOT_ALLOW.getMsg());
            }
        } else {
            throw new BaseException(RetCode.ALBUM_NOT_FOUNT.getCode(), RetCode.ALBUM_NOT_FOUNT.getMsg());
        }
        return list;
    }

    /**
     * 获取播放地址
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> entityPath(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>(2);
        // 用户兑换码验证
        boolean flag = userCodeVerif(params);
        if(flag){
            if(params.containsKey(StringUtil.MUSICID)){
                // 根据音频id获取音频信息,判断音频ID是否存在
                Music music = musicDao.findMusicById(params);
                if(music != null){
                    if (getChannelSource(params, music.getSourceId(), music.getIsFree())) {
                        // 当有数据权限，验证是否为懒人听书付费内容
                        if (music.getIsFree() == 3 && music.getSourceId() == 4) {
                            // 付费,为懒人听书时特殊处理，必须同步订单
                            Orders order = ordersDao.findOrder(params);
                            if (order == null) {
                                // 当整本专辑同步订单为null时，查询单曲同步订单
                                order = ordersDao.findOrderByMusicId(params);
                            }
                            // 为懒人,判断是否同步过订单
                            if (order != null) {
                                flag = true;
                            }else{
                                flag = false;
                            }
                        }
                        // 当为true可以访问
                        if (flag) {
                            map = getPath(params);
                            log.info("<<<<<<<<<<<<<<<<<<<<渠道："+params.get(StringUtil.CHANNELID)+"获取"+params.get("musicId")+"音频播放地址成功!<<<<<<<<<<<<<<<<<<<<");
                        } else {
                            //添加访问记录
                            params.put("visitStatus", 2);
                            params.put("visitExplain", "获取播放地址失败,未购买当前音频!");
                            log.info("<<<<<<<<<<<<<<<<<<<<渠道："+params.get(StringUtil.CHANNELID)+"获取"+params.get(StringUtil.MUSICID)+"音频播放地址失败!<<<<<<<<<<<<<<<<<<<<");
                            log.info("<<<<<<<<<<<<<<<<<<<<失败原因："+RetCode.AUDIO_NOT_PURCHASED.getMsg());
                            throw new BaseException(RetCode.AUDIO_NOT_PURCHASED.getCode(), RetCode.AUDIO_NOT_PURCHASED.getMsg());
                        }
                    }else {
                        log.info("<<<<<<<<<<<<<<<<<<<<渠道："+params.get(StringUtil.CHANNELID)+"获取"+params.get(StringUtil.MUSICID)+"音频播放地址失败!<<<<<<<<<<<<<<<<<<<<");
                        log.info("<<<<<<<<<<<<<<<<<<<<失败原因："+RetCode.CHANNEL_NOT_ALLOW.getMsg());
                        throw new BaseException(RetCode.CHANNEL_NOT_ALLOW.getCode(), RetCode.CHANNEL_NOT_ALLOW.getMsg());
                    }
                } else {
                    log.info("<<<<<<<<<<<<<<<<<<<<渠道："+params.get(StringUtil.CHANNELID)+"获取"+params.get(StringUtil.MUSICID)+"音频播放地址失败!<<<<<<<<<<<<<<<<<<<<");
                    log.info("<<<<<<<<<<<<<<<<<<<<失败原因："+RetCode.MUSIC_NOT_FOUNT.getMsg());
                    throw new BaseException(RetCode.MUSIC_NOT_FOUNT.getCode(), RetCode.MUSIC_NOT_FOUNT.getMsg());
                }
            } else if(params.containsKey(StringUtil.HASH)){
                // 根据hash直接获取播放地址
                map = getPlayUrlByHash(params);
            }
        }else {
            //添加访问记录
            params.put("visitStatus", 2);
            params.put("visitExplain", "获取播放地址失败,未购买当前音频!");
            log.info("<<<<<<<<<<<<<<<<<<<<渠道："+params.get(StringUtil.CHANNELID)+"获取"+params.get(StringUtil.MUSICID)+"音频播放地址失败!<<<<<<<<<<<<<<<<<<<<");
            log.info("<<<<<<<<<<<<<<<<<<<<失败原因："+RetCode.AUDIO_NOT_PURCHASED.getMsg());
            throw new BaseException(RetCode.AUDIO_NOT_PURCHASED.getCode(), RetCode.AUDIO_NOT_PURCHASED.getMsg());
        }
        return map;
    }

    /**
     * 用户兑换码验证
     *
     * @param params
     */
    private boolean userCodeVerif(Map<String, Object> params) {
        boolean flag = false;
        // 判断用户使用兑换码情况
        JSONObject json = StringUtil.getJsonToMap(params);
        String result = HttpUtil.doHttpPost(serviceLicenseUrl+StringUtil.CHANNEL_QUERY_LICENSE,json);
        Integer useStatus = JSONObject.parseObject(result).getInteger("data");
        if(useStatus == 1){
            flag = true;
        }else if(useStatus == 0){
            log.info("<<<<<<<<<<<<<<<<<<<<"+params.get(StringUtil.CHANNELID)+"渠道"+params.get(StringUtil.PASSPORT)+"用户未使用兑换码绑定！<<<<<<<<<<<<<<<<<<<<");
            throw new BaseException(RetCode.AUDIO_NOT_PURCHASED.getCode(), RetCode.AUDIO_NOT_PURCHASED.getMsg());
        }else{
            log.info("<<<<<<<<<<<<<<<<<<<<"+params.get(StringUtil.CHANNELID)+"渠道"+params.get(StringUtil.PASSPORT)+"用户绑定兑换码的兑换码已过期！<<<<<<<<<<<<<<<<<<<<");
            throw new BaseException(RetCode.CODE_DUE.getCode(), RetCode.CODE_DUE.getMsg());
        }
        return flag;
    }


    /**
     * 获取播放地址
     * @param map
     * @return
     */
    public Map<String, Object> getPath(Map<String, Object> map) {
        Map<String, Object> param;
        Music music = musicDao.findMusicById(map);
        if(music.getSourceId() == 2){
            param = getQtPlayUrl(map,music);
        }else if(music.getSourceId() == 3){
            param = getQtPlayUrl(map,music);
        }else if(music.getSourceId() == 4){
            param = gerLrtsPlayUrl(map,music);
        }else{
            param = getTxyyPlayUrl(map,music);
        }
        return param;
    }

    /**
     * 获取腾讯音乐播放地址
     * @param map
     * @param music
     * @return
     */
    private Map<String, Object> getTxyyPlayUrl(Map<String, Object> map, Music music) {
        Map<String, Object> param = new HashMap<>(2);
        // 判断userId和token是否存在
        if(!map.containsKey(StringUtil.USER_ID) || !map.containsKey(StringUtil.TOKEN)){
            throw new BaseException(RetCode.DEVICE_NO_ACTIVATE.getCode(),RetCode.DEVICE_NO_ACTIVATE.getMsg());
        }
        // 获取渠道的类型
        channelType(map);
        String payUrl = TxyyUtil.getPlayUrl(music.getPlayUrl(),music.getAlbumId().intValue(),map);
        if(payUrl != null){
            JSONObject json = JSONObject.parseObject(payUrl);
            if(json.getInteger("status") == 1){
                JSONObject jsonObject = JSONObject.parseObject(json.getString("data"));
                param.put("code",0);
                param.put("playUrl",jsonObject.getString("url"));
            }else{
                param.put("code",json.getInteger("error_code"));
            }
        }
        return param;
    }

    /**
     * 根据用户传的歌曲hash值获取腾讯音乐播放地址
     * @param map
     * @return
     */
    private Map<String, Object> getPlayUrlByHash(Map<String, Object> map) {
        Map<String, Object> param = new HashMap<>(2);
        // 判断userId和token是否存在
        if(!map.containsKey(StringUtil.USER_ID) || !map.containsKey(StringUtil.TOKEN)){
            throw new BaseException(RetCode.DEVICE_NO_ACTIVATE.getCode(),RetCode.DEVICE_NO_ACTIVATE.getMsg());
        }
        // 获取渠道的类型
        channelType(map);
        String payUrl = TxyyUtil.getPlayUrl(map.get(StringUtil.HASH).toString(),Integer.valueOf(map.get(StringUtil.ALBUMID).toString()),map);
        if(payUrl != null){
            JSONObject json = JSONObject.parseObject(payUrl);
            if(json.getInteger("status") == 1){
                JSONObject jsonObject = JSONObject.parseObject(json.getString("data"));
                param.put("code",0);
                param.put("playUrl",jsonObject.getString("url"));
            }else{
                param.put("code",json.getInteger("error_code"));
            }
        }
        return param;
    }

    /**
     * 获取蜻蜓儿童播放地址
     * @param map
     * @param music
     * @return
     */
    private Map<String, Object> getQtPlayUrl(Map<String, Object> map, Music music) {
        Map<String, Object> param = new HashMap<>(2);
         String cid = null;
        if(music.getIsFree() == 3){
            JSONObject data = getRspLicense(map,music.getSourceId());
            cid = data.getLong("cid").toString();
            if(data.getInteger("status") == 0){
                // 注册新用户并使用兑换码
                String license = data.getString("license");
                QtfmUtil.getUseCode(music.getSourceId(),cid,license);
            }
        }
        String returnString = QtfmUtil.getPlayURL(music,cid);
        log.info(">>>>>>>>>>>>>>>>>>>>>蜻蜓播放地址："+returnString);
        JSONObject jsonObject = JSONObject.parseObject(returnString);
        if(jsonObject.getInteger("errcode") == 0){
            String data = jsonObject.getString("data");
            JSONArray jsonArray = JSONObject.parseObject(data).getJSONArray("editions");
            if(jsonArray.size() > 0){
                JSONObject json = (JSONObject) jsonArray.get(0);
                JSONArray jw = JSONArray.parseArray(json.getString("urls"));
                String playUrl = (String) jw.get(0);
                param.put("code",0);
                param.put("playUrl",playUrl);
            }
        }else{
            param.put("code",jsonObject.getInteger("errcode"));
        }
        return param;
    }

    /**
     * 获取懒人听书播放地址
     * @param map
     * @param music
     * @return
     */
    private Map<String, Object> gerLrtsPlayUrl(Map<String, Object> map, Music music) {
        Map<String, Object> param = new HashMap<>(2);
        String result = LrtsUtil.entityPath(music.getAlbumId(),music.getThirdId());
        JSONObject jsonObject = JSONObject.parseObject(result).getJSONObject("result");
        if(jsonObject.getInteger("status") == 0){
            JSONObject json = JSONObject.parseObject(jsonObject.getString("data"));
            String playUrl = json.getString("filePath");
            param.put("code",0);
            param.put("playUrl",playUrl);
        }else{
            param.put("code",jsonObject.getInteger("status"));
            param.put("msg",jsonObject.getString("msg"));
        }
        return param;
    }

    /**
     * 新增访问记录
     *
     * @param params
     */
    public void insertVisitRecord(Map<String, Object> params) {
        VisitRecord record = new VisitRecord();
        if (params.containsKey(StringUtil.CHANNELID)) {
            record.setChannelId(Long.valueOf(params.get(StringUtil.CHANNELID).toString()));
        }
        if (params.containsKey(StringUtil.PASSPORT)) {
            record.setPassport(params.get(StringUtil.PASSPORT).toString());
        }
        if (params.containsKey(StringUtil.ALBUMID)) {
            record.setAlbumId(Long.valueOf(params.get(StringUtil.ALBUMID).toString()));
        }
        if (params.containsKey(StringUtil.MUSICID)) {
            record.setMusicId(Long.valueOf(params.get(StringUtil.MUSICID).toString()));
        }
        if (params.containsKey(StringUtil.IMEI)) {
            record.setImei(params.get(StringUtil.IMEI).toString());
        }
        record.setVisitUrl(params.get(StringUtil.URL_PATH).toString());
        record.setVisitIp(params.get(StringUtil.IP).toString());
        record.setVisitStatus(Integer.valueOf(params.get("visitStatus").toString()));
        record.setVisitExplain(params.get("visitExplain").toString());
        record.setVisitTime(new Timestamp(System.currentTimeMillis()));
        channelDao.inserVisitRecord(record);
    }


    /**
     * 同步订单
     *
     * @param params
     * @return
     */
    @Override
    public void syncOrder(Map<String, Object> params) {
        Channel channel = channelDao.getChannel(Long.valueOf(params.get("channelId").toString()));
        if (channel != null) {
            Orders order = null;
            try {
                order = StringUtil.convertMap2Bean(params, Orders.class);
            } catch (Exception e) {
                log.info("=====Map转换成对象异常=====");
                e.printStackTrace();
            }
            if (ordersDao.findOrderByNo(order) == null) {
                order.setCreateTime(new Timestamp(System.currentTimeMillis()));
                if(order.getOrderType() == 1){
                    order.setMusicId("");
                }
                ordersDao.syncOrder(order);
            } else {
                log.info("=====当前渠道" + order.getChannelId() + "已同步过" + order.getOrderNo() + "订单=====");
            }
        } else {
            throw new BaseException(RetCode.CHANNEL_ID_ERROR.getCode(), RetCode.CHANNEL_ID_ERROR.getMsg());
        }
    }

    /**
     * 使用兑换码
     * @param map
     */
    @Override
    public void useCode(Map<String, Object> map) {
        JSONObject json = StringUtil.getJsonToMap(map);
        // 访问license服务，使用license
        String result = useLicense(json);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(jsonObject.getInteger("code") == 200){
            if(jsonObject.getInteger("data") == 0){
                throw new BaseException(RetCode.CODE_NOT_FOUNT.getCode(), RetCode.CODE_NOT_FOUNT.getMsg());
            }
            if(jsonObject.getInteger("data") == 2){
                throw new BaseException(RetCode.CODE_USED.getCode(), RetCode.CODE_USED.getMsg());
            }
            if(jsonObject.getInteger("data") == 3){
                throw new BaseException(RetCode.USER_IMEI.getCode(), RetCode.USER_IMEI.getMsg());
            }
            if(jsonObject.getInteger("data") == 4){
                throw new BaseException(RetCode.USER_USE_LICENSE.getCode(), RetCode.USER_USE_LICENSE.getMsg());
            }
            if(jsonObject.getInteger("data") == 5){
                throw new BaseException(RetCode.CODE_DUE.getCode(), RetCode.CODE_DUE.getMsg());
            }
        }else{
            throw new BaseException(jsonObject.getInteger("code"), jsonObject.getString("msg"));
        }
    }


    /**
     * 生成指定渠道数量的兑换码
     *
     * @param generateNum
     * @param channelId
     */
    @Override
    public void generateCode(Long generateNum, Long channelId) {

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
     * 获取歌词
     * @param map
     * @return
     */
    @Override
    public String getLyric(Map<String, Object> map) {
        String result = "";
        Music music = musicDao.findMusicById(map);
        if(music != null){
            if(music.getSourceId() == 5){
                String data = TxyyUtil.getYric(music.getPlayUrl(),music.getDuration(),music.getTitle());
                if(!StringUtils.isEmpty(data)){
                    JSONObject json = JSONObject.parseObject(data).getJSONObject("data");
                    JSONArray jsonArray = JSONArray.parseArray(json.getString("candidates"));
                    json = (JSONObject) jsonArray.get(0);
                    String accesskey = json.getString("accesskey");
                    Integer id = json.getInteger("id");
                    result = TxyyUtil.getYricDownload(id,accesskey);
                }
            }else{
                log.info("当前"+map.get(StringUtil.MUSICID)+"音频id不是腾讯音乐的，不能获取歌词！");
            }
        }else{
            log.info("当前"+map.get(StringUtil.MUSICID)+"音频id不存在！");
        }
        return result;
    }


    /**
     * 激活牛方案会员
     * @param map
     */
    @Override
    public String activate(Map<String, Object> map) {
        // 访问license服务，查询是否使用license
        String res = null;
        JSONObject jsonObject = StringUtil.getJsonToMap(map);
        jsonObject.put("passportType",2);
        String channelLicense = HttpUtil.doHttpPost(serviceLicenseUrl+StringUtil.CHANNEL_QUERY_LICENSE,jsonObject);
        JSONObject jb = JSONObject.parseObject(channelLicense);
        if(jb.getInteger("code") == 200){
            Integer useStatus = jb.getInteger("data");
            if(useStatus == 1){
                // 渠道用户已完成设备绑定，可以进行激活，查询渠道对应的类型1音响，2LOT
                Channel channel = channelDao.getChannel(Long.valueOf(map.get(StringUtil.CHANNELID).toString()));
                map.put("channelType",channel.getChannelType());
                if(channel.getChannelType() == 0){
                    // 等于0测试环境
                    res = TxyyUtil.getActivate(map);
                }else{
                    // 否则正式环境获取license,如果
                    JSONObject data =  getRspLicense(map,channel.getChannelType());
                    // 请不要重复激活
                    res = TxyyUtil.getLicenseActivate(map,data.getString("license"));
                }
                if(res != null){
                    JSONObject json = JSONObject.parseObject(res);
                    if(json.getInteger("status") == 1){
                        // 根据设备唯一标识判断是否存在
                        String sn = map.get("sn").toString();
                        TencentActivate activate = tencentActivateDao.getTencentActivateBySn(sn);
                        if(activate == null){
                            Long channelId = Long.valueOf(map.get(StringUtil.CHANNELID).toString());
                            String kguserId = map.get(StringUtil.USER_ID).toString();
                            String token = map.get(StringUtil.TOKEN).toString();
                            String passport = map.get(StringUtil.PASSPORT).toString();
                            activate = new TencentActivate();
                            activate.setPassport(passport);
                            activate.setChannelId(channelId);
                            activate.setSn(sn);
                            activate.setKguserId(kguserId);
                            activate.setToken(token);
                            tencentActivateDao.insertTencentActivate(activate);
                        }
                    }else{
                        log.info(">>>>>>>>>>>"+map.get(StringUtil.CHANNELID)+"渠道下设备"+map.get(StringUtil.SN)+"激活结果："+res);
                    }
                }
            }else if(useStatus == 0){
                log.info("<<<<<<<<<<<<<<<<<<<<"+map.get(StringUtil.CHANNELID)+"渠道"+map.get(StringUtil.PASSPORT)+"用户"+map.get(StringUtil.SN)+"设备未绑定license！<<<<<<<<<<<<<<<<<<<<");
                throw new BaseException(RetCode.DEVICE_NO_LICENSE.getCode(), RetCode.DEVICE_NO_LICENSE.getMsg());
            }else{
                log.info("<<<<<<<<<<<<<<<<<<<<"+map.get(StringUtil.CHANNELID)+"渠道"+map.get(StringUtil.PASSPORT)+"用户绑定的兑换码已过期，请重新绑定！<<<<<<<<<<<<<<<<<<<<");
                throw new BaseException(RetCode.CODE_DUE.getCode(), RetCode.CODE_DUE.getMsg());
            }
        }
        return res;
    }

    /**
     * 获取第三方license
     * @param map
     * @param rspId 类型，2蜻蜓儿童，3蜻蜓成人，35腾讯音响，7腾讯LOT
     * @return lincese信息
     */
    private JSONObject getRspLicense(Map<String, Object> map, Integer rspId) {
        JSONObject data = null;
        JSONObject json = StringUtil.getJsonToMap(map);
        json.put("rspId",rspId);
        json.put("passportType",0);
        String license = getRspLicense(json);
        JSONObject jsonObject = JSONObject.parseObject(license);
        if(jsonObject.getInteger("code") == 200){
            data = JSONObject.parseObject(jsonObject.getString("data"));
            if(data.size() == 0){
                // TODO license已用完
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>"+rspId+"分类的license已用完！<<<<<<<<<<<<<<<<<<<<<<<<<<");
                throw new BaseException(RetCode.SERVER_ERROR.getCode(),RetCode.SERVER_ERROR.getMsg());
            }
        }
        return data;
    }

    /**
     * 刷新token
     * @param map
     * @return
     */
    @Override
    public String refreshToken(Map<String, Object> map) {
        channelType(map);
        String result = TxyyUtil.getRefreshToken(map);
        log.info(map.get(StringUtil.SN).toString()+"设备刷新token结果："+result);
        return result;
    }

    /**
     * 获取分类列表
     * @param map
     * @return
     */
    @Override
    public RetResult getCategoryList(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getCategory(map));
    }

    /**
     * 获取分类歌单
     * @param map
     * @return
     */
    @Override
    public RetResult getCategorySpecial(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getCategorySpercial(map));
    }

    /**
     * 获取歌单歌曲
     * @param map
     * @return
     */
    @Override
    public RetResult getSpecialSong(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getSpercialSong(map));
    }


    /**
     * 获取歌手列表
     * @param map
     * @return
     */
    @Override
    public RetResult getSingerList(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getSinger(map));
    }

    /**
     * 获取歌手单曲
     * @param map
     * @return
     */
    @Override
    public RetResult getSingerSong(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getSingerSong(map));
    }

    /**
     * 获取歌手专辑
     * @param map
     * @return
     */
    @Override
    public RetResult getSingerAlbum(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getSingerAlbum(map));
    }

    /**
     * 获取排行榜
     * @param map
     * @return
     */
    @Override
    public RetResult getRankList(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getRankList(map));
    }

    /**
     * 获取榜单歌曲
     * @param map
     * @return
     */
    @Override
    public RetResult getRankSong(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getRankSong(map));
    }

    /**
     * 获取推荐电台
     * @param map
     * @return
     */
    @Override
    public RetResult getFmList(Map<String, Object> map) {
        RetResult result = new RetResult();
        channelType(map);
        String value = TxyyUtil.getFmList(map);
        JSONObject json = JSONObject.parseObject(value);
        if(json.getInteger("error_code") == 0){
            result.setData(json.getJSONArray("data"));
        }else{
            result.setCode(json.getInteger("error_code"));
        }
        return result;
    }

    /**
     * 获取电台歌曲
     * @param map
     * @return
     */
    @Override
    public RetResult getFmSong(Map<String, Object> map) {
        RetResult result = new RetResult();
        channelType(map);
        String value = TxyyUtil.getFmSong(map);
        JSONObject json = JSONObject.parseObject(value);
        if(json.getInteger("error_code") == 0){
            result.setData(json.getJSONArray("data"));
        }else{
            result.setCode(json.getInteger("error_code"));
        }
        return result;
    }

    /**
     * 收藏一首歌曲
     * @param map
     * @return
     */
    @Override
    public RetResult collectSong(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.collectSong(map));
    }


    /**
     * 获取收藏的歌曲列表
     * @param map
     * @return
     */
    @Override
    public RetResult getCollectList(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getCollectList(map));
    }


    /**
     * 获取渠道类型
     * @param map
     */
    private void channelType(Map<String, Object> map){
        Long channelId = Long.valueOf(map.get(StringUtil.CHANNELID).toString());
        Channel channel = channelDao.getChannel(channelId);
        map.put("channelType",channel.getChannelType());
    }

    /**
     * 返回
     * @param map
     * @param collectList
     * @return
     */
    private RetResult getRetResult(Map<String, Object> map, String collectList) {
        RetResult result = new RetResult();
        String value = collectList;
        JSONObject json = JSONObject.parseObject(value);
        if(json.getInteger("error_code") == 0){
            result.setData(json.getJSONObject("data"));
        }else{
            result.setCode(json.getInteger("error_code"));
            result.setMsg("fail");
        }
        return result;
    }

    /**
     * 修改license绑定的用户
     * @param map
     * @return
     */
    @Override
    public RetResult updatePassport(Map<String, Object> map) {
        Channel channel = channelDao.getChannel(Long.valueOf(map.get(StringUtil.CHANNELID).toString()));
        if(channel.getModifyAuth() == 1){
            // 可以修改,根据原始用户和license
            JSONObject json = StringUtil.getJsonToMap(map);
            String license = updateLicense(json);
            JSONObject jsonObject = JSONObject.parseObject(license);
            if(jsonObject.getInteger("code") == 200){
                Integer data = jsonObject.getInteger("data");
                if(data == 1){
                    return new RetResult();
                }else if(data == 2){
                    throw new BaseException(RetCode.LICENSE_UNBOUND.getCode(),RetCode.LICENSE_UNBOUND.getMsg());
                }else{
                    throw new BaseException(RetCode.CODE_NOT_FOUNT.getCode(),RetCode.CODE_NOT_FOUNT.getMsg());
                }
            }
        }else{
            throw new BaseException(RetCode.CHANNEL_NOT_ALLOW.getCode(),RetCode.CHANNEL_NOT_ALLOW.getMsg());
        }
        return null;
    }


    /**
     * 激活酷码
     * @param map
     * @return
     */
    @Override
    public RetResult activateCode(Map<String, Object> map) {
        RetResult result = new RetResult();
        // 查询当前酷码状态
        JSONObject json = StringUtil.getJsonToMap(map);
        String coolCode = getRspCoolCode(json);
        JSONObject jsonObject = JSONObject.parseObject(coolCode);
        if(jsonObject.getInteger("code") == 200){
            JSONObject data = jsonObject.getJSONObject("data");
            if(data.getInteger("status") == 0 || data.getInteger("status") == 2){
                map.put("coolCode",data.getString("coolCode"));
                String value = TxyyUtil.activateCode(map,rsaCipher);
                log.info(">>>>>>>>>>酷码激活结果："+value);
                JSONObject jb = JSONObject.parseObject(value);
                if(jb.getInteger("error_code") == 0 || jb.getInteger("error_code") == 200102){
                    // 激活成功，修改当前酷码的状态
                    updateRspCoolCode(json);
                    log.info(">>>>>>>>>>修改当前"+data.getString("coolCode")+"酷码状态成功！");
                }else{
                    result.setCode(jb.getInteger("error_code"));
                    result.setMsg("fail");
                }
            }else if(data.getInteger("status") == 1){
                throw new BaseException(RetCode.COOLCODE_USE.getCode(),RetCode.COOLCODE_USE.getMsg());
            }else if(data.getInteger("status") == 3){
                // 已过期，修改酷码状态
                updateRspCoolCode(json);
                throw new BaseException(RetCode.COOLCODE_EXPIRATION.getCode(),RetCode.COOLCODE_EXPIRATION.getMsg());
            }else{
                throw new BaseException(RetCode.COOLCODE_NO_FOUND.getCode(),RetCode.COOLCODE_NO_FOUND.getMsg());
            }
        }else{
            JSONObject jsonObject1 = JSONObject.parseObject(coolCode);
            throw new BaseException(jsonObject1.getInteger("code"),jsonObject1.getString("msg"));
        }
        return result;
    }

    /**
     * 批量查询歌曲权限
     * @param map
     * @return
     */
    @Override
    public RetResult getResPrivilege(Map<String, Object> map) {
        RetResult result = new RetResult();
        channelType(map);
        try {
            JSONArray jsonArray = JSONArray.parseArray(map.get("resource").toString());
            if(jsonArray.size() <= 50){
                String value = TxyyUtil.getResPrivilege(map,jsonArray);
                JSONObject json = JSONObject.parseObject(value);
                if(json.getInteger("error_code") == 0){
                    result.setData(json.getJSONArray("data"));
                }else{
                    result.setCode(json.getInteger("error_code"));
                }
            }else{
                throw new BaseException(RetCode.MAX_NUMBER.getCode(),RetCode.MAX_NUMBER.getMsg());
            }
        }catch (JSONException e){
            throw new BaseException(RetCode.JSON_ERREO.getCode(),RetCode.JSON_ERREO.getMsg());
        }
        return result;
    }

    /**
     * 获取酷狗用户信息
     * @param map
     * @return
     */
    @Override
    public RetResult getUserInfo(Map<String, Object> map) {
        channelType(map);
        return getRetResult(map, TxyyUtil.getUserInfo(map));
    }


    /**-----------------------------------license服务--------------------------------------------**/
    /**
     * 使用license
     * @param json
     * @return
     */
    private synchronized String useLicense(JSONObject json){
        return HttpUtil.doHttpPost(serviceLicenseUrl+StringUtil.CHANNEL_USE_LICENSE,json);
    }

    /**
     * 修改license绑定的用户
     * @param json
     * @return
     */
    private synchronized String updateLicense(JSONObject json){
        return HttpUtil.doHttpPost(serviceLicenseUrl+StringUtil.UPDATE_LICENSE,json);
    }

    /**
     * 获取第三方license
     * @param json
     * @return
     */
    private synchronized String getRspLicense(JSONObject json){
        return HttpUtil.doHttpPost(serviceLicenseUrl+StringUtil.RSP_LICENSE,json);
    }

    /**
     * 获取第三方酷码
     * @param json
     * @return
     */
    private synchronized String getRspCoolCode(JSONObject json){
        return HttpUtil.doHttpPost(serviceLicenseUrl+StringUtil.RSP_COOL_CODE,json);
    }

    /**
     * 修改酷码状态
     * @param json
     * @return
     */
    private synchronized String updateRspCoolCode(JSONObject json){
        return HttpUtil.doHttpPost(serviceLicenseUrl+StringUtil.UPDATE_COOL_CODE,json);
    }

    /**-----------------------------------license服务--------------------------------------------**/
}
