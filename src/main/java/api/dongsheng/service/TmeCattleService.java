package api.dongsheng.service;


import api.dongsheng.common.RetResult;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/8/8 11:16
 **/
public interface TmeCattleService {

    /**
     * 获取用户登录二维码
     * @param json
     * @return
     */
    RetResult getUserLogOnQrcode(JSONObject json);

    /**
     * 登录二维码授权信息
     * @param json
     * @return
     */
    RetResult qrCodeAuth(JSONObject json);

    /**
     * 获取用户信息
     * @param json
     * @return
     */
    RetResult getUserInfo(JSONObject json);

    /**
     * 用户登出
     * @param json
     * @return
     */
    RetResult logout(JSONObject json);

    /**
     * 设备激活
     * @param json
     * @return
     */
    RetResult deviceActivation(JSONObject json);

    /**
     * 使用配额
     * @param json
     * @return
     */
    RetResult useQuota(JSONObject json);

    /**
     * 专辑信息
     * @param json
     * @return
     */
    RetResult albumInfo(JSONObject json);

    /**
     * 每日推荐
     * @param json
     * @return
     */
    RetResult everydayAwesome(JSONObject json);

    /**
     * 推荐歌单列表
     * @param json
     * @return
     */
    RetResult playlistAwesome(JSONObject json);

    /**
     * 歌单内歌曲列表
     * @param json
     * @return
     */
    RetResult playlistSong(JSONObject json);

    /**
     * 电台列表
     * @param json
     * @return
     */
    RetResult radioList(JSONObject json);

    /**
     * 电台内歌曲列表
     * @param json
     * @return
     */
    RetResult radioSong(JSONObject json);

    /**
     * 歌手列表
     * @param json
     * @return
     */
    RetResult singerList(JSONObject json);

    /**
     * 歌手信息
     * @param json
     * @return
     */
    RetResult singerInfo(JSONObject json);

    /**
     * 歌手歌曲
     * @param json
     * @return
     */
    RetResult singerSong(JSONObject json);

    /**
     * 歌手专辑
     * @param json
     * @return
     */
    RetResult singerAlbum(JSONObject json);

    /**
     * 批量查询歌曲信息
     * @param json
     * @return
     */
    RetResult songInfos(JSONObject json);

    /**
     * 获取歌曲播放链接
     * @param json
     * @return
     */
    RetResult songUrl(JSONObject json);

    /**
     * 获取歌曲歌词
     * @param json
     * @return
     */
    RetResult songLyric(JSONObject json);

    /**
     * 榜单列表
     * @param json
     * @return
     */
    RetResult topList(JSONObject json);

    /**
     * 榜单歌曲
     * @param json
     * @return
     */
    RetResult topSong(JSONObject json);

    /**
     * 搜索歌曲
     * @param json
     * @return
     */
    RetResult searchSong(JSONObject json);

    /**
     * 自建歌单列表
     * @param json
     * @return
     */
    RetResult seftPlayList(JSONObject json);

    /**
     * 收藏歌单列表
     * @param json
     * @return
     */
    RetResult otherPlayList(JSONObject json);

    /**
     * 歌单内歌曲列表
     * @param json
     * @return
     */
    RetResult favoriteSong(JSONObject json);

    /**
     * 收藏或取消收藏歌单
     * @param json
     * @return
     */
    RetResult favoriteOperPlayList(JSONObject json);

    /**
     * 收藏或取消收藏歌曲
     * @param json
     * @return
     */
    RetResult favoriteOperSong(JSONObject json);

    /**
     * 上报播放数据
     * @param json
     * @return
     */
    RetResult monitorListen(JSONObject json);

    /**
     * 激活赠送会员
     * @param json
     * @return
     */
    RetResult activationVip(JSONObject json);
}
