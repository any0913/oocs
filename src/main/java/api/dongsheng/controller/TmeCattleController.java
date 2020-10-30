package api.dongsheng.controller;


import api.dongsheng.common.RetCode;
import api.dongsheng.common.RetResult;
import api.dongsheng.common.TmeCattleConstant;
import api.dongsheng.exception.BaseException;
import api.dongsheng.service.TmeCattleService;
import api.dongsheng.util.StringUtil;
import api.dongsheng.util.TmeCattleUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author rx
 * @Description 牛方案2.0接口
 * @Date 2020/4/11 11:38
 **/
@RestController
@RequestMapping("/v2/open/ocs")
public class TmeCattleController {

    @Autowired
    private TmeCattleService tmeCattleService;

    /**
     * 获取用户登录二维码
     * @param request
     * @return
     */
    @PostMapping("/user/qrcode/get")
    public RetResult getQrCode(@RequestBody JSONObject json,HttpServletRequest request) {
        if(!json.containsKey(TmeCattleConstant.PASSPORT) || !json.containsKey(TmeCattleConstant.SP)
                || !json.containsKey(TmeCattleConstant.DEVICEID)){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.getUserLogOnQrcode(json);
    }

    /**
     * 登录二维码授权信息
     * @param request
     * @return
     */
    @PostMapping("/user/qrcode/auth")
    public RetResult getAuth(@RequestBody JSONObject json,HttpServletRequest request) {
        if(!json.containsKey(TmeCattleConstant.PASSPORT) || !json.containsKey(TmeCattleConstant.SP)
                || !json.containsKey(TmeCattleConstant.DEVICEID) || !json.containsKey(TmeCattleConstant.TICKET)){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.qrCodeAuth(json);
    }

    /**
     * 用户信息
     * @param request
     * @return
     */
    @PostMapping("/user/info")
    public RetResult getUserInfo(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.getUserInfo(json);
    }

    /**
     * 用户登出
     * @param request
     * @return
     */
    @PostMapping("/user/logout")
    public RetResult getUserLogout(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.logout(json);
    }


    /**
     * 设备激活
     * @param request
     * @return
     */
    @PostMapping("/device/activation")
    public RetResult deviceActivation(@RequestBody JSONObject json,HttpServletRequest request) {
        if(!json.containsKey(TmeCattleConstant.PASSPORT) || !json.containsKey(TmeCattleConstant.SP)
                || !json.containsKey(TmeCattleConstant.DEVICEID)){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        String channelId = request.getHeader(StringUtil.CHANNELID);
        json.put("channelId", channelId);
        return tmeCattleService.deviceActivation(json);
    }

    /**
     * 激活赠送会员
     * @param request
     * @return
     */
    @PostMapping("/member/activation")
    public RetResult activationVip(@RequestBody JSONObject json,HttpServletRequest request) {
        if(!json.containsKey(TmeCattleConstant.PASSPORT)){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        tmeVerification(json);
        getClientIp(request,json);
        String channelId = request.getHeader(StringUtil.CHANNELID);
        json.put("channelId", channelId);
        return tmeCattleService.activationVip(json);
    }


    /**
     * 使用会员码
     * @param request
     * @return
     */
    @PostMapping("/member/code")
    public RetResult useQuota(@RequestBody JSONObject json,HttpServletRequest request) {
        if(!json.containsKey(TmeCattleConstant.PASSPORT) || !json.containsKey("memberCode")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        tmeVerification(json);
        getClientIp(request,json);

        return tmeCattleService.useQuota(json);
    }

    /**
     * 专辑信息
     * @param request
     * @return
     */
    @PostMapping("/album/info")
    public RetResult albumInfo(@RequestBody JSONObject json,HttpServletRequest request) {
        if(!json.containsKey("page") || !json.containsKey("size") || !json.containsKey("album_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.albumInfo(json);
    }


    /**
     * 每日推荐
     * @param request
     * @return
     */
    @PostMapping("/awesome/everyday")
    public RetResult everydayAwesome(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.everydayAwesome(json);
    }

    /**
     * 推荐歌单
     * @param request
     * @return
     */
    @PostMapping("/playlist/awesome")
    public RetResult playlistAwesome(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey(TmeCattleConstant.PAGE) || !json.containsKey(TmeCattleConstant.SIZE)){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.playlistAwesome(json);
    }

    /**
     * 歌单内歌曲列表
     * @param request
     * @return
     */
    @PostMapping("/playlist/song")
    public RetResult playlistSong(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey(TmeCattleConstant.PAGE) || !json.containsKey(TmeCattleConstant.SIZE)
                || !json.containsKey("playlist_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.playlistSong(json);
    }

    /**
     * 电台列表
     * @param request
     * @return
     */
    @PostMapping("/radio/list")
    public RetResult radioList(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.radioList(json);
    }

    /**
     * 电台内歌曲列表
     * @param request
     * @return
     */
    @PostMapping("/radio/song")
    public RetResult radioSong(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey("radio_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.radioSong(json);
    }

    /**
     * 歌手列表
     * @param request
     * @return
     */
    @PostMapping("/singer/list")
    public RetResult singerList(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.singerList(json);
    }

    /**
     * 歌手列表
     * @param request
     * @return
     */
    @PostMapping("/singer/info")
    public RetResult singerInfo(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.singerInfo(json);
    }

    /**
     * 歌手歌曲列表
     * @param request
     * @return
     */
    @PostMapping("/singer/song")
    public RetResult singerSong(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey(TmeCattleConstant.PAGE) || !json.containsKey(TmeCattleConstant.SIZE)
                || !json.containsKey("singer_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.singerSong(json);
    }


    /**
     * 歌手专辑
     * @param request
     * @return
     */
    @PostMapping("/singer/album")
    public RetResult singerAlbum(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey(TmeCattleConstant.PAGE) || !json.containsKey(TmeCattleConstant.SIZE)
                || !json.containsKey("singer_id") || !json.containsKey("sort")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.singerAlbum(json);
    }


    /**
     * 批量查询歌曲信息
     * @param request
     * @return
     */
    @PostMapping("/song/infos")
    public RetResult songInfos(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey("songs_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.songInfos(json);
    }

    /**
     * 获取歌曲播放链接
     * @param request
     * @return
     */
    @PostMapping("/song/url")
    public RetResult songUrl(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey("song_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.songUrl(json);
    }

    /**
     * 获取歌曲歌词
     * @param request
     * @return
     */
    @PostMapping("/song/lyric")
    public RetResult songLyric(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey("song_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.songLyric(json);
    }


    /**
     * 榜单列表
     * @param request
     * @return
     */
    @PostMapping("/top/list")
    public RetResult topList(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.topList(json);
    }

    /**
     * 榜单歌曲
     * @param request
     * @return
     */
    @PostMapping("/top/song")
    public RetResult topSong(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey(TmeCattleConstant.PAGE) || !json.containsKey(TmeCattleConstant.SIZE)
                || !json.containsKey("top_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.topSong(json);
    }


    /**
     * 搜索歌曲
     * @param request
     * @return
     */
    @PostMapping("/search/song")
    public RetResult searchSong(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey(TmeCattleConstant.PAGE) || !json.containsKey(TmeCattleConstant.SIZE)
                || !json.containsKey("keyword")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.searchSong(json);
    }



    /**
     * 获取自建歌单列表
     * @param request
     * @return
     */
    @PostMapping("/favorite/self/list")
    public RetResult seftPlayList(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.seftPlayList(json);
    }

    /**
     * 获取收藏歌单列表
     * @param request
     * @return
     */
    @PostMapping("/favorite/other/list")
    public RetResult otherPlayList(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        getClientIp(request,json);
        return tmeCattleService.otherPlayList(json);
    }


    /**
     * 获取收藏歌单内歌曲列表
     * @param request
     * @return
     */
    @PostMapping("/favorite/song")
    public RetResult favoriteSong(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey(TmeCattleConstant.PAGE) || !json.containsKey(TmeCattleConstant.SIZE)
                || !json.containsKey("type") || !json.containsKey("playlist_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.favoriteSong(json);
    }

    /**
     * 收藏或取消收藏歌单
     * @param request
     * @return
     */
    @PostMapping("/favorite/oper/playlist")
    public RetResult favoriteOperPlayList(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey("playlist_id") || !json.containsKey("playlist_extra_id") || !json.containsKey("cmd")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.favoriteOperPlayList(json);
    }


    /**
     * 收藏或取消收藏歌曲
     * @param request
     * @return
     */
    @PostMapping("/favorite/oper/song")
    public RetResult favoriteOperSong(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey("playlist_id") || !json.containsKey("song_id")
                || !json.containsKey("cmd") || !json.containsKey("song_extra_id")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.favoriteOperSong(json);
    }

    /**
     * 上报播放数据
     * @param request
     * @return
     */
    @PostMapping("/monitor/listen")
    public RetResult monitorListen(@RequestBody JSONObject json,HttpServletRequest request) {
        tmeVerification(json);
        if(!json.containsKey("song_id") || !json.containsKey("duration") || !json.containsKey("source_id")
             || !json.containsKey("lvt") || !json.containsKey("play_type") || !json.containsKey("source_api")
                || !json.containsKey("play_time")){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        getClientIp(request,json);
        return tmeCattleService.monitorListen(json);
    }


    /**
     * tme公共字段验证
     * @param json
     */
    private void tmeVerification(JSONObject json){
        if(!json.containsKey(TmeCattleConstant.USERID) || !json.containsKey(TmeCattleConstant.TOKEN)
                || !json.containsKey(TmeCattleConstant.SP) || !json.containsKey(TmeCattleConstant.DEVICEID)){
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
    }

    /**
     * 获取请求头参数
     * @return
     */
    private void getClientIp(HttpServletRequest request,JSONObject json) {
        String client_ip = StringUtil.getIpAddress(request);
        json.put(TmeCattleConstant.CLIENTIP,client_ip);
        json.put("channelId",request.getHeader(StringUtil.CHANNELID));
        json.put("productId",request.getHeader(StringUtil.PRODUCTID));
        json.put("platform",request.getHeader(StringUtil.PLATFORM));
    }


}
