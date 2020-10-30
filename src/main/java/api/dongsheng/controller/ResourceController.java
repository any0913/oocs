package api.dongsheng.controller;

import api.dongsheng.exception.BaseException;
import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.service.ResourceService;
import api.dongsheng.util.StringUtil;
import api.dongsheng.model.entity.ChannelCategory;
import api.dongsheng.model.vo.AlbumVO;
import api.dongsheng.model.vo.MusicVO;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author rx
 * @Description api接口
 * @Date 2019/8/6 19:38
 **/
@RestController
@RequestMapping("/v2/open/ocs")
public class ResourceController {


    @Autowired
    private ResourceService resourceService;

    /**
     * 获取分类
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCategory")
    public RetResult getCategory(HttpServletRequest request) {
        RetResult result = new RetResult();
        Map<String, Object> map = StringUtil.getParam(request);
        map.put("type",0);
        List<ChannelCategory> value = resourceService.getCategory(map);
        result.setData(value);
        return result;
    }


    /**
     * 获取分类下专辑
     *
     * @param request
     * @return
     */
    @RequestMapping("/getAlbum")
    public RetResult getAlbum(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.CATEGORYID)) || StringUtils.isEmpty(request.getParameter(StringUtil.SOURCEID))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        List<AlbumVO> albums = resourceService.albumTypeList(map);
        result.setData(albums);
        return result;
    }


    /**
     * 获取专辑下音频
     *
     * @param request
     * @return
     */
    @RequestMapping("/getMusic")
    public RetResult getMusic(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.ALBUMID)) || StringUtils.isEmpty(request.getParameter(StringUtil.SOURCEID))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        List<MusicVO> musics = resourceService.musicList(map);
        result.setData(musics);
        return result;
    }

    /**
     * 使用兑换码
     *
     * @param request
     * @return
     */
    @RequestMapping("/useLicense")
    public RetResult useLicense(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.PASSPORT)) || StringUtils.isEmpty(request.getParameter(StringUtil.LICENSE))
                || StringUtils.isEmpty(request.getParameter(StringUtil.PLATFORM))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        typeInvalid(request);
        Map<String, Object> map = StringUtil.getParam(request);
        resourceService.useCode(map);
        return result;
    }


    /**
     * 生成兑换码
     *
     * @param request
     * @return
     */
    @RequestMapping("/generateCode")
    public RetResult generateCode(HttpServletRequest request) {
        RetResult result = new RetResult();
        String generateNum = request.getParameter("generateNum");
        String channelId = request.getParameter(StringUtil.CHANNELID);
        if (StringUtils.isEmpty(generateNum) || StringUtils.isEmpty(channelId)) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        resourceService.generateCode(Long.valueOf(generateNum), Long.valueOf(channelId));
        return result;
    }


    /**
     * 获取播放地址
     *
     * @param request
     * @return
     */
    @RequestMapping("/getRealPath")
    public RetResult entityPath(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.ALBUMID)) || StringUtils.isEmpty(request.getParameter(StringUtil.PASSPORT))
                || StringUtils.isEmpty(request.getParameter(StringUtil.PLATFORM))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        typeInvalid(request);
        Map<String, Object> map = StringUtil.getParam(request);
        map = resourceService.entityPath(map);
        if(Integer.valueOf(map.get("code").toString()) == 0){
            result.setData(map.get("playUrl"));
        }else{
            result.setCode(Integer.valueOf(map.get("code").toString()));
            result.setMsg(Integer.valueOf(map.get("code").toString())==101165?"token不合法":"fail");
        }
        return result;
    }

    /**
     *  激活牛方案
     * @param request
     * @return
     */
    @RequestMapping("/activate")
    public RetResult activate(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID)) || StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN)) || StringUtils.isEmpty(request.getParameter(StringUtil.DEVICE_IP))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        String data = resourceService.activate(map);
        return result.setData(data);
    }

    /**
     *  刷新token
     * @param request
     * @return
     */
    @RequestMapping("/refreshToken")
    public RetResult refreshToken(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN)) || StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        String data = resourceService.refreshToken(map);
        JSONObject json = JSONObject.parseObject(data);
        if(json.getInteger("status") == 1){
            result.setData(json.getJSONObject("data"));
        }else{
            result.setCode(json.getInteger("error_code"));
            result.setMsg(json.getInteger("error_code") == 101165?"token不合法":"刷新异常");
        }
        return result;
    }

    /**
     * 获取歌词
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLyric")
    public RetResult getLyric(HttpServletRequest request) {
        RetResult result = new RetResult();
        if (StringUtils.isEmpty(request.getParameter(StringUtil.MUSICID))|| StringUtils.isEmpty(request.getParameter(StringUtil.PASSPORT))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        String value = resourceService.getLyric(map);
        return result.setData(value);
    }


    /**
     * 参数验证
     *
     * @param request
     */
    public void typeInvalid(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getParameter(StringUtil.PASSPORTTYPE)) || StringUtils.isEmpty(request.getParameter(StringUtil.SN))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        if(request.getParameter(StringUtil.PASSPORTTYPE).equals("2") && StringUtils.isEmpty(request.getParameter(StringUtil.DEVICE_IP))){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
    }


    /**
     * 获取分类列表
     * @param request
     * @return
     */
    @RequestMapping("/getCategoryList")
    public RetResult getCategoryList(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getCategoryList(map);
        return result;
    }

    /**
     * 获取分类歌单
     * @param request
     * @return
     */
    @RequestMapping("/getCategorySpecial")
    public RetResult getCategorySpecial(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN)) || StringUtils.isEmpty(request.getParameter("categoryid"))
                || StringUtils.isEmpty(request.getParameter("page")) || StringUtils.isEmpty(request.getParameter("pagesize"))
                || StringUtils.isEmpty(request.getParameter("sort"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getCategorySpecial(map);
        return result;
    }

    /**
     * 获取歌单歌曲
     * @param request
     * @return
     */
    @RequestMapping("/getSpecialSong")
    public RetResult getSpecialSong(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN)) || StringUtils.isEmpty(request.getParameter("specialid"))
                || StringUtils.isEmpty(request.getParameter("page")) || StringUtils.isEmpty(request.getParameter("pagesize"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getSpecialSong(map);
        return result;
    }


    /**
     * 获取歌手列表
     * @param request
     * @return
     */
    @RequestMapping("/getSingerList")
    public RetResult getSingerList(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))|| StringUtils.isEmpty(request.getParameter(StringUtil.SN))
                || StringUtils.isEmpty(request.getParameter("type")) || StringUtils.isEmpty(request.getParameter("sort"))
                || StringUtils.isEmpty(request.getParameter("sextype")) || StringUtils.isEmpty(request.getParameter("showtype"))
                || StringUtils.isEmpty(request.getParameter("musician")) || StringUtils.isEmpty(request.getParameter("page")) || StringUtils.isEmpty(request.getParameter("pagesize"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getSingerList(map);
        return result;
    }

    /**
     * 获取歌手单曲
     * @param request
     * @return
     */
    @RequestMapping("/getSingerSong")
    public RetResult getSingerSong(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))|| StringUtils.isEmpty(request.getParameter(StringUtil.SN))
                || StringUtils.isEmpty(request.getParameter("singerid")) || StringUtils.isEmpty(request.getParameter("sorttype"))
                || StringUtils.isEmpty(request.getParameter("page")) || StringUtils.isEmpty(request.getParameter("pagesize"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getSingerSong(map);
        return result;
    }

    /**
     * 获取歌手专辑
     * @param request
     * @return
     */
    @RequestMapping("/getSingerAlbum")
    public RetResult getSingerAlbum(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))|| StringUtils.isEmpty(request.getParameter(StringUtil.SN))
                || StringUtils.isEmpty(request.getParameter("singerid")) || StringUtils.isEmpty(request.getParameter("page")) || StringUtils.isEmpty(request.getParameter("pagesize"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getSingerAlbum(map);
        return result;
    }

    /**
     * 获取排行榜
     * @param request
     * @return
     */
    @RequestMapping("/getRankList")
    public RetResult getRankList(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))|| StringUtils.isEmpty(request.getParameter(StringUtil.SN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getRankList(map);
        return result;
    }

    /**
     * 获取榜单单曲
     * @param request
     * @return
     */
    @RequestMapping("/getRankSong")
    public RetResult getRankSong(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))|| StringUtils.isEmpty(request.getParameter(StringUtil.SN))
                || StringUtils.isEmpty(request.getParameter("rankid")) || StringUtils.isEmpty(request.getParameter("ranktype"))
                || StringUtils.isEmpty(request.getParameter("page")) || StringUtils.isEmpty(request.getParameter("pagesize"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getRankSong(map);
        return result;
    }


    /**
     * 获取推荐电台
     * @param request
     * @return
     */
    @RequestMapping("/getFmList")
    public RetResult getFmList(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID))|| StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getFmList(map);
        return result;
    }


    /**
     * 获取电台歌曲
     * @param request
     * @return
     */
    @RequestMapping("/getFmSong")
    public RetResult getFmSong(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID)) || StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN)) || StringUtils.isEmpty(request.getParameter("offset"))
                || StringUtils.isEmpty(request.getParameter("fmid")) || StringUtils.isEmpty(request.getParameter("fmtype"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getFmSong(map);
        return result;
    }


    /**
     * 收藏歌曲
     * @param request
     * @return
     */
    @RequestMapping("/collectSong")
    public RetResult collectSong(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID)) || StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN)) || StringUtils.isEmpty(request.getParameter("name"))
                || StringUtils.isEmpty(request.getParameter("hash")) || StringUtils.isEmpty(request.getParameter("size"))
                || StringUtils.isEmpty(request.getParameter("timelen")) || StringUtils.isEmpty(request.getParameter("bitrate"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.collectSong(map);
        return result;
    }

    /**
     * 收藏歌曲列表
     * @param request
     * @return
     */
    @RequestMapping("/getCollectList")
    public RetResult getCollectList(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID)) || StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN))
                || StringUtils.isEmpty(request.getParameter("page")) || StringUtils.isEmpty(request.getParameter("pagesize"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getCollectList(map);
        return result;
    }

    /**
     * 批量查询歌曲权限
     * @param json
     * @return
     */
    @PostMapping("/getResPrivilege")
    public RetResult getResPrivilege(@RequestBody JSONObject json,HttpServletRequest request){
        if (StringUtils.isEmpty(json.getString(StringUtil.USER_ID))|| StringUtils.isEmpty(json.getString(StringUtil.TOKEN))
                || StringUtils.isEmpty(json.getString(StringUtil.SN)) || StringUtils.isEmpty(json.getString("resource"))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.JsonToMap(json);
        map.put("channelId",request.getHeader("channelId"));
        RetResult result = resourceService.getResPrivilege(map);
        return result;
    }


    /**
     * 修改license绑定的passport
     * @param request
     * @return
     */
    @RequestMapping("/updatePassport")
    public RetResult updatePassport(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.LICENSE)) || StringUtils.isEmpty(request.getParameter(StringUtil.PASSPORT))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.updatePassport(map);
        return result;
    }


    /**
     * 激活酷码
     * @param request
     * @return
     */
    @RequestMapping("/activateCode")
    public RetResult activateCode(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID)) || StringUtils.isEmpty(request.getParameter(StringUtil.PASSPORT))
                || StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN)) || StringUtils.isEmpty(request.getParameter(StringUtil.SN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.COOLCODE))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.activateCode(map);
        return result;
    }

    /**
     * 获取酷狗用户信息
     * @param request
     * @return
     */
    @RequestMapping("/getUserInfo")
    public RetResult getUserInfo(HttpServletRequest request){
        if (StringUtils.isEmpty(request.getParameter(StringUtil.USER_ID)) || StringUtils.isEmpty(request.getParameter(StringUtil.TOKEN))
                || StringUtils.isEmpty(request.getParameter(StringUtil.SN))) {
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        Map<String, Object> map = StringUtil.getParam(request);
        RetResult result = resourceService.getUserInfo(map);
        return result;
    }

}
