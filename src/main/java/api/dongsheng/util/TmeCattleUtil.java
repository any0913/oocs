package api.dongsheng.util;

import api.dongsheng.common.TmeCattleConstant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 牛方案2.0工具类
 */
@Log4j
@Component
public class TmeCattleUtil {

    public static String APPID = "200021";
    public static String APPKEY = "55c801fac0b14dbb8c7eed72eb318f5b";
    public static String HOST = "https://thirdsso.kugou.com/v2";

    /**
     * 获取用户登录二维码请求地址
     */
    private static final String USER_LOGON_QRCODE_URL = new StringBuffer(HOST).append("/user/qrcode/get").toString();
    /**
     * 获取登录二维码授权信息请求地址
     */
    private static final String USER_QRCODE_AUTH_URL = new StringBuffer(HOST).append("/user/qrcode/auth").toString();
    /**
     * 获取用户信息
     */
    private static final String USER_INFO_URL = new StringBuffer(HOST).append("/user/info").toString();
    /**
     * 用户登出
     */
    private static final String USER_LOGOUT_URL = new StringBuffer(HOST).append("/user/logout").toString();
    /**
     * 设备激活
     */
    private static final String DEVICE_ACTIVATION_URL = new StringBuffer(HOST).append("/device/activation").toString();
    /**
     * 获取专辑信息请求地址
     */
    private static final String ALBUM_INFO_URL = new StringBuffer(HOST).append("/album/info").toString();
    /**
     * 获取每日推荐
     */
    private static final String EVERY_DAY_URL = new StringBuffer(HOST).append("/awesome/everyday").toString();
    /**
     * 获取推荐歌单
     */
    private static final String PLAY_LIST_URL = new StringBuffer(HOST).append("/playlist/awesome").toString();
    /**
     * 获取歌单内歌曲
     */
    private static final String PLAY_LIST_SONG_URL = new StringBuffer(HOST).append("/playlist/song").toString();
    /**
     * 获取电台列表
     */
    private static final String RADIO_LIST_URL = new StringBuffer(HOST).append("/radio/list").toString();
    /**
     * 获取电台内歌曲
     */
    private static final String RADIO_SONG_URL = new StringBuffer(HOST).append("/radio/song").toString();
    /**
     * 获取歌手列表
     */
    private static final String SINGER_LIST_URL = new StringBuffer(HOST).append("/singer/list").toString();
    /**
     * 获取歌手信息
     */
    private static final String SINGER_INFO_URL = new StringBuffer(HOST).append("/singer/info").toString();
    /**
     * 获取歌手歌曲
     */
    private static final String SINGER_SONG_URL = new StringBuffer(HOST).append("/singer/song").toString();
    /**
     * 获取歌手专辑
     */
    private static final String SINGER_ALBUM_URL = new StringBuffer(HOST).append("/singer/album").toString();
    /**
     * 批量获取歌曲信息
     */
    private static final String SONG_INFOS_URL = new StringBuffer(HOST).append("/song/infos").toString();
    /**
     * 获取歌曲播放连接
     */
    private static final String SONG_URL = new StringBuffer(HOST).append("/song/url").toString();
    /**
     * 获取歌曲歌词
     */
    private static final String SONG_LYRIC_URL = new StringBuffer(HOST).append("/song/lyric").toString();
    /**
     * 获取榜单列表
     */
    private static final String TOP_LIST_URL = new StringBuffer(HOST).append("/top/list").toString();
    /**
     * 获取榜单列表
     */
    private static final String TOP_SONG_URL = new StringBuffer(HOST).append("/top/song").toString();
    /**
     * 获取自建歌单列表
     */
    private static final String FAVORITE_PLAYLIST_URL = new StringBuffer(HOST).append("/favorite/self/list").toString();
    /**
     * 获取收藏歌单列表
     */
    private static final String COLLECT_LIST_URL = new StringBuffer(HOST).append("/favorite/other/list").toString();
    /**
     * 获取收藏歌单歌曲列表
     */
    private static final String PLAYLIST_SONG_URL = new StringBuffer(HOST).append("/favorite/song").toString();
    /**
     * 收藏/取消收藏歌单
     */
    private static final String FAVORITE_OPER_URL = new StringBuffer(HOST).append("/favorite/oper/playlist").toString();
    /**
     * 收藏/取消收藏歌曲
     */
    private static final String FAVORITE_OPER_SONG_URL = new StringBuffer(HOST).append("/favorite/oper/song").toString();
    /**
     * 搜索歌曲
     */
    private static final String SEARCH_SONG_URL = new StringBuffer(HOST).append("/search/song").toString();
    /**
     * 语音搜索
     */
    private static final String SEARCH_ALBUM_URL = new StringBuffer(HOST).append("/search/voice").toString();
    /**
     * TME会员直充
     */
    private static final String GIVE_VIP_URL = new StringBuffer(HOST).append("/vip/tme/partner/give").toString();

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("sp","KG");
        json.put("device_id","111111");
        json.put("client_ip","10.1.1.1");
        // 获取登录二维码
//        getUserLogOnQrcode(json);
        // 登录二维码授权信息
//        json.put("ticket","191ACABC92D04B459C36CFFFB45B927B");
//        getUserQrcodeAuth(json);
        // 用户信息
        json.put("userid","4E50741C2533759AD4032EC65E423613");
        json.put("token","1430d88c4123696cf463da1205261d10525b736c58b7b748360179ec6f601f155f994fe81aace112d0bf9fb0598e09e9");
//        System.out.println(getUserInfo(json));
        // 用户登出
//        System.out.println(getUserLogout(json));
        // 激活设备
//        System.out.println(deviceActivation(json));
//        json.put("album_id","212121");
        // 获取专辑信息
//        System.out.println(getAlbumInfo(json));
        // 获取每日推荐
//        System.out.println(getEveryDayAwesome(json));
        // 获取推荐歌单
        json.put("page",2);
        json.put("size",10);
//        System.out.println(getPlayList(json));
//        // 获取歌单内歌曲
//        json.put("playlist_id","7427003626");
//        System.out.println(getPlayListSong(json));
//        // 获取电台列表
//        System.out.println(getRadioList(json));
//        // 获取电台内歌曲
        json.put("radio_id","30");
//        System.out.println(getRadioSong(json));
//        // 获取歌手列表
        json.put("area",0);
        json.put("type",0);
//        System.out.println(getSingerList(json));
//        // 获取歌手信息
        json.put("singer_id","3539");
//        System.out.println(getSingerInfo(json));
        // 获取歌手歌曲
//        System.out.println(getSingerSong(json));
//        // 获取歌手专辑
//        json.put("sort",1);
//        System.out.println(getSingerAlbum(json));
//        // 批量查询歌曲信息
        List<String> list = new ArrayList<>();
        list.add("124781004");
        json.put("songs_id",list);
//        System.out.println(getSongInfos(json));
//        // 获取歌曲播放地址
        json.put("song_id","233125061");
//        System.out.println(getSongUrl(json));
//        // 获取歌曲歌词
//        System.out.println(getSongLyric(json));
//        // 获取榜单列表
//        System.out.println(getTopList(json));
//        // 获取榜单歌曲
//        json.put("top_id","62");
//        System.out.println(getTopSong(json));
//        // 获取自建歌单列表
//        System.out.println(getFavoritePlayList(json));
//        // 获取收藏歌单列表
//        System.out.println(getCollectPlayList(json));
//        // 获取收藏歌单内歌曲
//        json.put("playlist_id","3253063442");
//        json.put("type","self");
//        System.out.println(getCollectPlayListSong(json));
//        // 收藏或取消收藏歌单
//        json.put("cmd", 1);
//        json.put("playlist_extra_id", "3253063442");
//        System.out.println(getFavoriteOperPlayList(json));
//        // 获取或取消收藏歌曲
//        json.put("song_id","001QJyJ32zybEe");
//        json.put("song_extra_id","001QJyJ32zybEe");
//        System.out.println(getFavoriteOperSong(json));
//        // 会员直充
//        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK7KYDuAZP0E+9gVXjUlE/WUeAIjStlA4ZND8wHTzi+R50jLj8/TxSfNg8QWT70xhPpmOQuQNdWq83IeC5TOMGDcWxyECrClDWiwYDrCGU05fD7V///6hdcGCUZQEvPpbpgJuKpR1ICcriz098z4w6556L63wunxI1ChgcL0Q91JAgMBAAECgYAz8YhvplRcrOJ68L6yZHZuANU5LWvM7G8c6JCK9nrLwDxPcc8nH/PzRCvoVgkUXGb8UuyY9x5IhSkbvLoMWsodQOTIGgwst1JwD11KM/OWNoBVZcR5PH1d357lfkc4Qi7qWaC/dru2OCz7DANocLakWif3ttZ0/xsQIXdXqmfpkQJBAOFPybdbEt+4z3jPsnhBH2D1/kS8yULv7uhuDcqnKFJLUL3rc75R/YJL6hYvDZzVPvpWXL6o8wLD698kQmmGkmcCQQDGmQG9ij/6p7tQ8RjBVGQu+yImZoGgoKduq7cqPgT8khrcZtSUGVgzhsKe3sWCUzZw83gEzDRfrSpxnPLFGyTPAkEA23wAQo+DqVC9OzEI6EB4MtovogJOBNj6YI2k/b8sdk5MD1aoX8Q7MoyFMqsPlbHeMbujiU/Huu/kfHE0CNxBDQJBAKTjctN5DqfBTb/lfWU++TMcpXz2jNfdq0Yy4g/7kE5XzF+ELSmNZXLah4o6cghDqKbdLp/lH/L0JpJP+iz23XcCQD2+UAn1iEMhec6+IvgUbEzI9WekUJCbctL84ldwIJ4tGmstAIqr6+xG1YaQC8wRsUloIgkNukqyaWSzUAOSVmY=";
//        json.put("partner_no","131232131231231");
//        json.put("month",1);
//        json.put("day",0);
//        System.out.println(giveVip(json,privateKey));
        // 搜索歌曲
        json.put("keyword","少年");
//        System.out.println("搜索歌曲："+searchSong(json));
        // 语音搜索
//        json.put("query","七里香");
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name","singer");
//        jsonObject.put("value","周杰伦");
//        jsonArray.add(jsonObject);
//        json.put("slots",jsonArray);
//        System.out.println("语音搜索："+searchAlbum(json));
        // 上报播放数据
        String value = getSongUrl(json);
        JSONObject jsonObject = JSONObject.parseObject(value).getJSONObject("data");
        json.put("duration",jsonObject.getInteger("duration"));
        json.put("play_time",jsonObject.getInteger("duration"));
        json.put("source_id","");
        json.put("lvt",new Timestamp(System.currentTimeMillis()));
        json.put("play_type",1);
        monitorListen(json);
    }


    /**
     * 获取用户登录二维码
     */
    public static String getUserLogOnQrcode(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        return getResource(USER_LOGON_QRCODE_URL,jsonObject,json);
    }

    /**
     * 用户信息
     */
    public static String getUserInfo(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        return getResource(USER_INFO_URL,jsonObject,json);
    }


    /**
     * 用户登出
     */
    public static String getUserLogout(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        return getResource(USER_LOGOUT_URL,jsonObject,json);
    }


    /**
     * 登录二维码授权信息
     * @param json
     */
    public static String getUserQrcodeAuth(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("ticket",json.getString("ticket"));
        return getResource(USER_QRCODE_AUTH_URL,jsonObject,json);
    }

    /**
     * 设备激活
     * @param json
     */
    public static String deviceActivation(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        return getResource(DEVICE_ACTIVATION_URL,jsonObject,json);
    }

    /**
     * 获取专辑信息
     * @param json
     */
    public static String getAlbumInfo(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("page", json.getInteger("page"));
        jsonObject.put("size", json.getInteger("size"));
        jsonObject.put("album_id",json.getString("album_id"));
        return getResource(ALBUM_INFO_URL,jsonObject,json);
    }

    /**
     * 获取每日推荐
     * @param json
     */
    public static String getEveryDayAwesome(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        return getResource(EVERY_DAY_URL,jsonObject,json);
    }


    /**
     * 获取推荐歌单
     * @param json
     * @return
     */
    public static String getPlayList(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("page", json.get("page"));
        jsonObject.put("size", json.get("size"));
        return getResource(PLAY_LIST_URL,jsonObject,json);
    }


    /**
     * 获取歌单内歌曲
     * @param json
     * @return
     */
    public static String getPlayListSong(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("playlist_id", json.getString("playlist_id"));
        jsonObject.put("page", json.get("page"));
        jsonObject.put("size", json.get("size"));
        return getResource(PLAY_LIST_SONG_URL,jsonObject,json);
    }


    /**
     * 获取电台列表
     * @param json
     * @return
     */
    public static String getRadioList(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        return getResource(RADIO_LIST_URL,jsonObject,json);
    }


    /**
     * 获取电台内歌曲
     * @param json
     * @return
     */
    public static String getRadioSong(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("radio_id", json.getString("radio_id"));
        return getResource(RADIO_SONG_URL,jsonObject,json);
    }

    /**
     * 获取歌手列表
     * @param json
     * @return
     */
    public static String getSingerList(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("page", json.get("page"));
        jsonObject.put("size", json.get("size"));
        jsonObject.put("area", json.get("area"));
        jsonObject.put("type", json.get("type"));
        return getResource(SINGER_LIST_URL,jsonObject,json);
    }

    /**
     * 获取歌手信息
     * @param json
     * @return
     */
    public static String getSingerInfo(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("singer_id", json.getString("singer_id"));
        return getResource(SINGER_INFO_URL,jsonObject,json);
    }


    /**
     * 获取歌手歌曲
     * @param json
     * @return
     */
    public static String getSingerSong(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("page", json.get("page"));
        jsonObject.put("size", json.get("size"));
        jsonObject.put("singer_id", json.getString("singer_id"));
        return getResource(SINGER_SONG_URL,jsonObject,json);
    }

    /**
     * 获取歌手专辑
     * @param json
     * @return
     */
    public static String getSingerAlbum(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("page", json.get("page"));
        jsonObject.put("size", json.get("size"));
        jsonObject.put("singer_id", json.getString("singer_id"));
        jsonObject.put("sort",json.get("sort"));
        return getResource(SINGER_ALBUM_URL,jsonObject,json);
    }

    /**
     * 批量获取歌曲信息
     * @param json
     * @return
     */
    public static String getSongInfos(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("songs_id",json.get("songs_id"));
        return getResource(SONG_INFOS_URL,jsonObject,json);
    }

    /**
     * 获取歌曲播放连接
     * @param json
     * @return
     */
    public static String getSongUrl(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("song_id",json.getString("song_id"));
        return getResource(SONG_URL,jsonObject,json);
    }

    /**
     * 获取歌曲歌词
     * @param json
     * @return
     */
    public static String getSongLyric(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("song_id",json.getString("song_id"));
        return getResource(SONG_LYRIC_URL,jsonObject,json);
    }


    /**
     * 获取榜单列表
     * @param json
     * @return
     */
    public static String getTopList(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        return getResource(TOP_LIST_URL,jsonObject,json);
    }


    /**
     * 获取榜单歌曲
     * @param json
     * @return
     */
    public static String getTopSong(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("page", json.get("page"));
        jsonObject.put("size", json.get("size"));
        jsonObject.put("top_id", json.getString("top_id"));
        return getResource(TOP_SONG_URL,jsonObject,json);
    }

    /**
     * 获取自建歌单列表
     * @param json
     * @return
     */
    public static String getFavoritePlayList(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        return getResource(FAVORITE_PLAYLIST_URL,jsonObject,json);
    }

    /**
     * 获取收藏歌单列表
     * @param json
     * @return
     */
    public static String getCollectPlayList(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        return getResource(COLLECT_LIST_URL,jsonObject,json);
    }

    /**
     * 获取收藏歌单歌曲列表
     * @param json
     * @return
     */
    public static String getCollectPlayListSong(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("playlist_id", json.getString("playlist_id"));
        jsonObject.put("page", json.get("page"));
        jsonObject.put("size", json.get("size"));
        jsonObject.put("type", json.get("type"));
        return getResource(PLAYLIST_SONG_URL,jsonObject,json);
    }

    /**
     * 收藏/取消收藏歌单
     * @param json
     * @return
     */
    public static String getFavoriteOperPlayList(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("playlist_id", json.getString("playlist_id"));
        jsonObject.put("cmd", json.get("cmd"));
        jsonObject.put("playlist_extra_id", json.get("playlist_extra_id"));
        return getResource(FAVORITE_OPER_URL,jsonObject,json);
    }

    /**
     * 收藏/取消收藏歌曲
     * @param json
     * @return
     */
    public static String getFavoriteOperSong(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("cmd", json.get("cmd"));
        jsonObject.put("playlist_id", json.getString("playlist_id"));
        jsonObject.put("song_id", json.get("song_id"));
        jsonObject.put("song_extra_id", json.get("song_extra_id"));
        return getResource(FAVORITE_OPER_SONG_URL,jsonObject,json);
    }

    /**
     * 搜索歌曲
     * @param json
     * @return
     */
    public static String searchSong(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("page", json.get("page"));
        jsonObject.put("size", json.get("size"));
        jsonObject.put("keyword", json.get("keyword"));
        return getResource(SEARCH_SONG_URL,jsonObject,json);
    }

    /**
     * 搜索歌曲
     * @param json
     * @return
     */
    public static String searchAlbum(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("query", json.get("query"));
        jsonObject.put("slots", json.get("slots"));
        return getResource(SEARCH_ALBUM_URL,jsonObject,json);
    }


    /**
     * 上报接口访问
     * @param json
     * @return
     */
    public static String monitorDelay(JSONObject json,JSONObject param){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pid", param.getString(TmeCattleConstant.TME_PID));
        jsonObject.put("timestamp", System.currentTimeMillis()/1000);
        jsonObject.put("nonce", UUID.randomUUID().toString().replace("-",""));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ver","1.0");
        jsonObject.put("platform", StringUtils.isEmpty(param.getString(StringUtil.PLATFORM))?"Web":param.getString(StringUtil.PLATFORM));
        // array数据
        JSONArray data = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("sp",json.getString("sp"));
        jsonObject1.put("client_ip",json.getString("client_ip"));
        jsonObject1.put("server_ip",json.getString("client_ip"));
        jsonObject1.put("delay",json.getInteger("delay"));
        jsonObject1.put("apn",json.getString("apn"));
        jsonObject1.put("etype",json.getInteger("etype"));
        jsonObject1.put("ecode",json.getInteger("ecode"));
        jsonObject1.put("reqtime",json.getInteger("reqtime"));
        jsonObject1.put("reqsize",json.getInteger("reqsize"));
        jsonObject1.put("rspsize",json.getInteger("rspsize"));
        jsonObject1.put("retry",json.getInteger("retry"));
        jsonObject1.put("api",json.getString("api"));
        jsonObject1.put("userid",json.getString("userid"));
        data.add(jsonObject1);
        jsonObject.put("data",data);
        String url = "https://thirdssomdelay.kugou.com/v2/monitor/delay";
        String sign = SignUtil.getSign(jsonObject.toJSONString(),param.getString(TmeCattleConstant.TME_APPKEY));
        return HttpUtil.doPost(url, jsonObject, sign);
    }


    /**
     * 上报播放数据
     * @param json
     * @return
     */
    public static String monitorListen(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pid",json.getString(TmeCattleConstant.TME_PID));
        jsonObject.put("timestamp", System.currentTimeMillis()/1000);
        jsonObject.put("nonce", UUID.randomUUID().toString().replace("-",""));
        jsonObject.put("device_id",json.getString("device_id"));
        // array数据
        JSONArray data = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("sp",json.getString("sp"));
        jsonObject1.put("song_id",json.getString("song_id"));
        jsonObject1.put("duration",json.getInteger("duration"));
        jsonObject1.put("play_time",json.getInteger("play_time"));
        jsonObject1.put("source_id",json.getString("source_id"));
        jsonObject1.put("lvt",json.getString("lvt"));
        jsonObject1.put("client_ip",json.getString("client_ip"));
        jsonObject1.put("api",json.getString("source_api").substring(json.getString("source_api").indexOf("/ocs")).replaceAll("/ocs","/v2"));
        jsonObject1.put("userid",json.getString("userid"));
        jsonObject1.put("play_type",json.getInteger("play_type"));
        data.add(jsonObject1);
        jsonObject.put("data",data);
        String url = "https://thirdssomlisten.kugou.com/v2/monitor/listen";
        String sign = SignUtil.getSign(jsonObject.toJSONString(),json.getString(TmeCattleConstant.TME_APPKEY));
        String result = HttpUtil.doPost(url, jsonObject, sign);
        System.out.println("上报播放数据结果："+result);
        return result;
    }


    /**
     * 发送请求
     * @param url
     * @param json
     * @return
     */
    private synchronized static String getResource(String url, JSONObject json,JSONObject param){
        json.put("pid", param.getString(TmeCattleConstant.TME_PID));
        json.put("timestamp", System.currentTimeMillis()/1000);
        json.put("nonce", UUID.randomUUID().toString().replace("-",""));
        String sign = SignUtil.getSign(json.toJSONString(),param.getString(TmeCattleConstant.TME_APPKEY));
        Long start = System.currentTimeMillis();
        String result = HttpUtil.doPost(url, json, sign);
        long end = System.currentTimeMillis();
        json.put("delay",end-start);
        // 上报接口访问
        reporting(json,url,result,param);
        return result;
    }

    /**
     * 上报接口访问
     * @param json 请求包体
     * @param url 接口url地址
     * @param value 返回结果
     */
    private static void reporting(JSONObject json,String url,String value,JSONObject param){
        Integer rspsize = json.toString().getBytes().length;
        JSONObject jsonObject = JSONObject.parseObject(value);
        json.put("apn","wifi");
        json.put("etype",jsonObject.getInteger("error_code"));
        json.put("ecode",jsonObject.getInteger("error_code"));
        json.put("reqtime",System.currentTimeMillis());
        json.put("reqsize",value.getBytes().length);
        json.put("rspsize",rspsize);
        json.put("retry",0);
        json.put("api",url.substring(url.indexOf("/v2")));
        String result = monitorDelay(json,param);
//        System.out.println(">>>>>>>>>>>>>上报接口访问结果："+result);
    }

    /**
     * TME会员直充
     * @param json
     * @return
     */
    public static String giveVip(JSONObject json){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pid", json.getString(TmeCattleConstant.TME_PID));
        jsonObject.put("timestamp", System.currentTimeMillis()/1000);
        jsonObject.put("nonce", UUID.randomUUID().toString().replace("-",""));
        jsonObject.put("sp",json.getString("sp"));
        jsonObject.put("device_id",json.getString("device_id"));
        jsonObject.put("client_ip",json.getString("client_ip"));
        jsonObject.put("userid", json.get("userid"));
        jsonObject.put("token", json.getString("token"));
        jsonObject.put("partner_no", json.getString("partner_no"));
        jsonObject.put("month", json.containsKey("month")?json.getInteger("month"):0);
        jsonObject.put("day", json.containsKey("day")?json.getInteger("day"):0);
        String sign = SignUtil.getRsaSign(jsonObject.toJSONString(), json.getString(TmeCattleConstant.TME_MEMBER_KEY));
        return HttpUtil.doPost(GIVE_VIP_URL, jsonObject, sign);
    }
}
