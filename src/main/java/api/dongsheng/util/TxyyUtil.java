package api.dongsheng.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;
import java.io.*;

/**
 * @Author rx
 * @Description 腾讯音乐工具类
 * @Date 2019/10/31 14:54
 **/
@Slf4j
public class TxyyUtil {


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    /**
     * 测试appid
     */
    private static final Integer APPID = 2906;
    /**
     * 测试appkey
     */
    private static final String APPKEY = "UqRDzSCIZqOkgXIEjXsnsZ3EmPuXXmYP";

    /**
     * 正式--音响appid
     */
    private static final Integer YX_APPID = 3032;
    /**
     * 正式--音响appkey
     */
    private static final String YX_APPKEY = "eFlRXRRKzF7DlCu1puzWUQl36mFjIqvU";
    /**
     * 正式--LOT其他appid
     */
    private static final Integer LOT_APPID = 3016;
    /**
     * 正式--LOT其他appkey
     */
    private static final String LOT_APPKEY = "glX6I35enE6ysf46VfDG7ELi7A5Y1ixU";

    /**
     * 登录酷狗音乐返回的userid
     */
    private static final Integer USERID = 1231004820;
    /**
     * 登录酷狗音乐返回的token
     */
    private static final String TOKEN = "a5c7084566b4cef4b6b4235d3972540254097851879a9f07bf48b48ad88b9bc83c7364e6c2b0d2ba887e160b99f7d93b";




    public static void main(String[] args) {
//        System.out.println(getYric("8129AFF61BA5DC03DCCF97D44F3F7596",242,"赵薇 - 天使旅行箱"));
//        System.out.println(getYricDownload(14236520,"FB11817DA2D754CFD6EC831B048B3A6B"));
        HashMap<String, Object> map = new HashMap<>(6);
//        map.put("userId","1231004820");
//        map.put("token","a5c7084566b4cef4b6b4235d3972540254097851879a9f07bf48b48ad88b9bc83c7364e6c2b0d2ba887e160b99f7d93b");
//        map.put("sn","13css2112dew1");
        map.put("userId","391562067");
        map.put("token","9dff70ee8cbd35fd22b7eca62b4a27263a38768a834dea8b494a62eb6a27bb121cf5ab58a1a1b3054e4ea50e45519ebf");
        map.put("sn","47a3183cdb4d8f4af35ff96b222768da");
        map.put("channelType",5);
        //获取歌曲权限
        String s = "[{\"name\":\"毛不易\",\"album_id\":\"34631159\",\"id\":0,\"type\":\"audio\",\"hash\":\"6BA651668FC185DCC5AB23DDA1EED637\"},{\"name\":\"毛不易\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"A3DBFD1CD5E6F6D352563E14A1D9ADB3\"},{\"name\":\"毛不易\",\"album_id\":\"36309181\",\"id\":0,\"type\":\"audio\",\"hash\":\"081CCD123A90F68C0D618C13537A23EE\"},{\"name\":\"毛不易\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"EF484A20AD395E8707C1ABF03F4883AC\"},{\"name\":\"毛不易\",\"album_id\":\"36230318\",\"id\":0,\"type\":\"audio\",\"hash\":\"842FACF3039E51ECF396679AD0C74BDE\"},{\"name\":\"毛不易\",\"album_id\":\"35945159\",\"id\":0,\"type\":\"audio\",\"hash\":\"8542D4F03DA9D55BDDCD0A5E3B9CA352\"},{\"name\":\"毛不易\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"14906ED7015A07E079F0EED6466C4D0C\"},{\"name\":\"毛不易\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"EC5B58ECDC8D6F8AE843DB2872D13209\"},{\"name\":\"毛不易\",\"album_id\":\"34631159\",\"id\":0,\"type\":\"audio\",\"hash\":\"5DD8F06429B96BF7A0116CB359B952EC\"},{\"name\":\"毛不易\",\"album_id\":\"34631159\",\"id\":0,\"type\":\"audio\",\"hash\":\"F9B009E30E0DF99F7AF3F0CCCE78859F\"},{\"name\":\"毛不易\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"BC0E6A70A85B878D8E7D1D72A735534C\"},{\"name\":\"毛不易、何炅\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"DB32F39E5DB48127E785F88D1F3D8A8E\"},{\"name\":\"毛不易、刘涛\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"8A7AAB6148C11F2D76B0895841CA3B98\"},{\"name\":\"毛不易\",\"album_id\":\"28470328\",\"id\":0,\"type\":\"audio\",\"hash\":\"26CAF08F7D8096A4057F00D101072317\"},{\"name\":\"毛不易\",\"album_id\":\"28077367\",\"id\":0,\"type\":\"audio\",\"hash\":\"81B3F8507860259DAE810F2D5A82AE2C\"},{\"name\":\"毛不易、罗志祥\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"ED7B2C7A62CD5E07465A3FBCF51A488B\"},{\"name\":\"毛不易\",\"album_id\":\"27649820\",\"id\":0,\"type\":\"audio\",\"hash\":\"0D05E66863344C1AE4BEB3C59C9A4317\"},{\"name\":\"毛不易、冯希瑶\",\"album_id\":\"27653944\",\"id\":0,\"type\":\"audio\",\"hash\":\"9709AE4C3F88DD04473AB4A5173015E1\"},{\"name\":\"毛不易\",\"album_id\":\"0\",\"id\":0,\"type\":\"audio\",\"hash\":\"3E04CBA140F1C8A2AF52437DB3FFAB8C\"},{\"name\":\"毛不易\",\"album_id\":\"26183679\",\"id\":0,\"type\":\"audio\",\"hash\":\"3F4C1F41A3D940AD131F238104282AF6\"}]";
        JSONArray jsonArray = JSONArray.parseArray(s);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("type","audio");
//        jsonObject.put("id",0);
//        jsonObject.put("hash","3BD5C05B9F8D082BA3C9425A1A712394");
//        jsonObject.put("name","周杰伦 - 晴天");
//        jsonObject.put("album_id","966846");
//        jsonArray.add(jsonObject);
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("type","audio");
//        jsonObject1.put("id",0);
//        jsonObject1.put("hash","3BD5C05B9F8D082BA3C9425A1A712394");
//        jsonObject1.put("name","周杰伦 - 晴天");
//        jsonObject1.put("album_id","966846");
//        jsonArray.add(jsonObject1);
//        System.out.println(jsonArray.toJSONString());
//        System.out.printf(getResPrivilege(map,jsonArray));
//        System.out.println(getLicenseActivate(map,"1KGDh/QBbQpnvbL/EErabOP2YTVLOqZNgBJjGSvuMuM="));
//        getActivate(map);
//        System.out.println(getPlayUrl("057827259F391CE4DA5FB2B2EB851484",966846,map));
//        System.out.println(getAlbumSearch(map));
        //获取用户信息
//        System.out.println(getUserInfo(map));
//        System.out.printf(getPrivilege(map));
//        System.out.println(getRefreshToken(map));
//        System.out.println(getActivate(map));
//        System.out.println(getSongSearch(map));
        System.out.println(getWordRecommend(map,"周杰伦"));
//        System.out.println(getFmList(map));
//        map.put("type", 0);
//        map.put("sort", 1);
//        map.put("sextype", 0);
//        map.put("showtype", 1);
//        map.put("musician", 0);
        map.put("page", 1);
        map.put("pagesize", 200);
//        System.out.println(getSinger(map));
        // 获取歌手单曲
//        map.put("singerid", 3520);
//        map.put("sorttype", 2);
//        System.out.println(getSingerSong(map));
        // 获取歌手专辑
//        System.out.println(getSingerAlbum(map));
        // 专辑下歌曲
//        map.put("albumid",28318475);
//        System.out.println(getAlbumMusic(map));
        // 获取排行榜榜单
//        System.out.println(getRankList(map));
        // 获取榜单歌曲
//        map.put("rankid",6666);
//        map.put("ranktype",2);
//        System.out.println(getRankSong(map));
        // 获取推荐电台
//        System.out.println(getFmList(map));
        // 获取电台歌曲
//        map.put("fmid",73);
//        map.put("fmtype",2);
//        map.put("offset",-1);
//        map.put("size",20);
//        System.out.println(getFmSong(map));
        //收藏歌曲到我喜欢
//        map.put("name","周杰伦 - 晴天");
//        map.put("hash","41D1995B8CE9F7B2259776D88BF5DA90");
//        map.put("size",4318896);
//        map.put("timelen",269);
//        map.put("bitrate",128);
////        map.put("album_id",966846);
//        System.out.println(collectSong(map));
        // 获取我喜欢列表
//        System.out.println(getCollectList(map));

//        System.out.printf(getCategory(map));
        // 获取分类歌单
//        map.put("categoryid",1084);
//        map.put("page",1);
//        map.put("pagesize",200);
//        map.put("sort",1);
//        System.out.printf(getCategorySpercial(map));

//        map.put("specialid",2020252);
//        map.put("page",1);
//        map.put("pagesize",200);
//        System.out.printf(getSpercialSong(map));
        // 获取每日推荐歌曲
//        System.out.println(everyDaySong(map));
//        System.out.println(getAlbumSearch(map));
    }


    /**
     * 获取歌曲播放地址
     * @param hash
     * @return
     */
    public static String getPlayUrl(String hash,Integer albumId,Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/tracker/mp3_simple";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("hash", hash);
        bodyArg.put("vipType", 1);
        bodyArg.put("album_id", albumId);
        String response = getResponse(url,map,bodyArg);
        return response;
    }

    /**
     * 获取用户信息
     * @param map
     * @return
     */
    public static String getUserInfo(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/get_user_info";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("mid", map.get(StringUtil.SN));
        String response = getResponse(url, map, bodyArg);
        return response;
    }


    /**
     * 关键字搜索
     * @param map
     * @param keyword
     * @return
     */
    public static String getWordRecommend(Map<String,Object> map,String keyword){
        String url = "https://thirdsso.kugou.com/v1/tip/word_recommend";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("keyword", keyword);
        bodyArg.put("searchcount", 1);
        String response = getResponse(url, map, bodyArg);
        return response;
    }


    /**
     * 专辑搜索
     * @param map
     * @return
     */
    public static String getAlbumSearch(Map<String,Object> map) {
        String url = "https://thirdsso.kugou.com/v1/search/album_search";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("keyword", "太空人");
        bodyArg.put("page", 1);
        bodyArg.put("pagesize", 30);
        bodyArg.put("platform", "WebFilter");
        String response = getResponse(url, map, bodyArg);
        return response;
    }


    /**
     * 语音搜索
     * @param map
     * @return
     */
    public static String getSongSearch(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/search/voice_order";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("clientver", 9000);
        bodyArg.put("clienttime", System.currentTimeMillis());
        bodyArg.put("type_client", "phone");



        JSONArray slots = new JSONArray();
        JSONObject slot = new JSONObject();
        slot.put("name","song");
        slot.put("slot_struct",1);
        slot.put("type","sys.music.song");
        JSONArray values = new JSONArray();
        JSONObject value = new JSONObject();
        value.put("original_text","冰雨");
        value.put("text","冰雨");
        values.add(value);
        slot.put("values",values);
        slots.add(slot);

        JSONObject semantic = new JSONObject();
        semantic.put("domain","music");
        semantic.put("intent","play");
        semantic.put("query","冰雨");
        semantic.put("slots",slots);

        JSONObject semantic_json = new JSONObject();
        semantic_json.put("semantic",semantic);

        JSONObject data = new JSONObject();
        data.put("semantic_json",semantic_json);
        bodyArg.put("data",data);
        System.out.println(JSONObject.toJSONString(bodyArg));
        String response = getResponse(url, map, bodyArg);
        return response;
    }


    /**
     * 激活牛方案会员
     * @param map
     */
    public static String getActivate(Map<String,Object> map) {
        String url = "https://thirdsso.kugou.com/v1/activate";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("devtype", 1);
        String response = getResponse(url, map, bodyArg);
        return response;
    }

    /**
     * 获取牛方案会员有效期
     * @param map
     */
    public static String getPrivilege(Map<String,Object> map) {
        String url = "https://thirdsso.kugou.com/v1/get_privilege";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        String response = getResponse(url, map, bodyArg);
        return response;
    }

    /**
     * 使用license激活牛方案会员
     * @param map
     * @param license
     * @return
     */
    public static String getLicenseActivate(Map<String,Object> map,String license){
        String url = "https://thirdsso.kugou.com/v1/activate/withlicense";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("license", license);
        String response = getResponse(url, map, bodyArg);
        return response;
    }


    /**
     * 批量查询歌曲权限
     * @param map
     * @return
     */
    public static String getResPrivilege(Map<String,Object> map,JSONArray jsonArray){
        String url = "https://thirdsso.kugou.com/v1/media/get_res_privilege";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("relate", 0);
        bodyArg.put("resource",jsonArray);
        String response = getResponse(url, map, bodyArg);
        return response;
    }

    /**
     * 刷新token
     * @param map
     * @return
     */
    public static String getRefreshToken(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/login_by_token";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("clientver", 900);
        bodyArg.put("mid", map.get("sn").toString());
        bodyArg.put("userid", Long.valueOf(map.get("userId").toString()));
        bodyArg.put("token", map.get("token").toString());
        String response = getResponse(url, map, bodyArg);
        return response;
    }

    /**
     * 获取每日推荐歌曲
     * @param map
     * @return
     */
    public static String everyDaySong(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/recommend/everyday_song";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("mid", map.get("sn"));
        String response = getResponse(url,map,bodyArg);
        return response;
    }


    /**
     * 歌词搜索
     * @param hash
     * @return
     */
    public static String getYric(String hash,Integer duration,String keyword){
        String url = "https://thirdsso.kugou.com/v1/lyric/search";
        Map<String,Object> map = new HashMap<>(3);
        map.put("userId",USERID);
        map.put("token",TOKEN);
        map.put("deviceNo","1111111");
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("hash", hash);
        bodyArg.put("keyword", keyword);
        bodyArg.put("duration", duration);
        String response= null;
        try {
            response = getResponse(url,map,bodyArg);
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 歌词下载
     * @param id
     * @param accesskey
     * @return
     */
    public static String getYricDownload(Integer id,String accesskey){
        String url = "https://thirdsso.kugou.com/v1/lyric/download";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("id", id);
        bodyArg.put("accesskey", accesskey);
        bodyArg.put("fmt","lrc");
        bodyArg.put("charset", "utf8");
        Map<String,Object> map = new HashMap<>(3);
        map.put("userId",USERID);
        map.put("token",TOKEN);
        map.put("deviceNo","1111111");
        String response = getResponse(url,map,bodyArg);
        JSONObject body = JSONObject.parseObject(response);
        String content = null;
        if(body.getInteger("status") == 1){
            JSONObject jsonObject = JSONObject.parseObject(body.getString("data"));
            Base64.Decoder decoder = Base64.getDecoder();
            try {
                content = new String(decoder.decode(jsonObject.getString("content")), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(content);
        }else{
            log.info("获取歌词失败！");
        }
        return content;
    }

    /**
     * 获取分类列表
     * @param map
     * @return
     */
    public static String getCategory(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/category/alllist";
        HashMap<String, Object> bodyArg = new HashMap<>(0);
        String response = getResponse(url,map,bodyArg);
        return response;
    }

    /**
     * 获取分类歌单
     * @param map
     * @return
     */
    public static String getCategorySpercial(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/category/special";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("categoryid", map.get("categoryid"));
        bodyArg.put("sort", map.get("sort"));
        bodyArg.put("page", map.get("page"));
        bodyArg.put("pagesize", map.get("pagesize"));
        String response = getResponse(url,map,bodyArg);
        return response;
    }

    /**
     * 获取歌单歌曲
     * @param map
     * @return
     */
    public static String getSpercialSong(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/special/song";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("specialid", map.get("specialid"));
        bodyArg.put("page", map.get("page"));
        bodyArg.put("pagesize", map.get("pagesize"));
        String response = getResponse(url,map,bodyArg);
        return response;
    }


    /**
     * 获取歌手
     */
    public static String getSinger(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/singer/list";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("type", map.get("type"));
        bodyArg.put("sort", map.get("sort"));
        bodyArg.put("sextype", map.get("sextype"));
        bodyArg.put("showtype", map.get("showtype"));
        bodyArg.put("musician", map.get("musician"));
        bodyArg.put("page", map.get("page"));
        bodyArg.put("pagesize", map.get("pagesize"));
        String response = getResponse(url,map,bodyArg);
        return response;
    }

    /**
     * 歌手详情
     * @param map
     */
    public static String getSingerInfo(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/singer/info";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("singerid", map.get("singerId"));
        bodyArg.put("singername", map.get("singername"));
        String response = getResponse(url,map,bodyArg);
        return response;
    }

    /**
     * 获取歌手下的单曲
     * @param map
     */
    public static String getSingerSong(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/singer/song";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("singerid", map.get("singerid"));
        bodyArg.put("sorttype", map.get("sorttype"));
        bodyArg.put("page",map.get("page"));
        bodyArg.put("pagesize", map.get("pagesize"));
        return getResponse(url,map,bodyArg);

    }

    /**
     * 获取歌手下的专辑
     * @param map
     */
    public static String getSingerAlbum(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/singer/album";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("singerid", map.get("singerid"));
        bodyArg.put("page",map.get("page"));
        bodyArg.put("pagesize", map.get("pagesize"));
        return getResponse(url,map,bodyArg);

    }


    /**
     *  获取专辑下歌曲
     * @param map
     */
    public static String getAlbumMusic(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/album/song";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("albumid", map.get("albumid"));
        bodyArg.put("page",map.get("page"));
        bodyArg.put("pagesize", map.get("pagesize"));
        return getResponse(url,map,bodyArg);
    }


    /**
     *  获取排行榜列表
     * @param map
     */
    public static String getRankList(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/rank/list";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        return getResponse(url,map,bodyArg);
    }


    /**
     *  获取榜单歌曲
     * @param map
     */
    public static String getRankSong(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/rank/song";
        HashMap<String, Object> bodyArg = new HashMap<>(6);
        bodyArg.put("rankid", map.get("rankid"));
        bodyArg.put("ranktype", map.get("ranktype"));
        bodyArg.put("page",map.get("page"));
        bodyArg.put("pagesize", map.get("pagesize"));
        return getResponse(url,map,bodyArg);
    }


    /**
     * 获取电台
     * @param map
     * @return
     */
    public static String getFmList(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/recommend/fm_list";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        String response = getResponse(url, map, bodyArg);
        return response;
    }


    /**
     * 获取电台歌曲
     * @param map
     * @return
     */
    public static String getFmSong(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/fm/song_list";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        data.put("fmid",map.get("fmid"));
        data.put("fmtype",map.get("fmtype"));
        data.put("offset",map.get("offset"));
        data.put("size",20);
        jsonArray.add(data);
        bodyArg.put("data",jsonArray);
        String response = getResponse(url, map, bodyArg);
        return response;
    }

    /**
     * 收藏歌曲
     * @param map
     * @return
     */
    public static String collectSong(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/cloudlist_add_song";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        data.put("name",map.get("name"));
        data.put("hash",map.get("hash"));
        data.put("size",Integer.valueOf(map.get("size").toString()));
        data.put("timelen",Integer.valueOf(map.get("timelen").toString()));
        data.put("bitrate",Integer.valueOf(map.get("bitrate").toString()));
        if(map.containsKey(StringUtil.ALBUMID)){
            data.put("album_id",map.get(StringUtil.ALBUMID));
        }
        jsonArray.add(data);
        bodyArg.put("clientver",900);
        bodyArg.put("clienttime",System.currentTimeMillis());
        bodyArg.put("data",jsonArray);
        String response = getResponse(url, map, bodyArg);
        return response;
    }



    /**
     * 获取收藏列表
     * @param map
     * @return
     */
    public static String getCollectList(Map<String,Object> map){
        String url = "https://thirdsso.kugou.com/v1/cloudlist_get_list_like";
        HashMap<String, Object> bodyArg = new HashMap<>(1);
        bodyArg.put("page",1);
        // 最大数300
        bodyArg.put("pagesize",300);
        String response = getResponse(url, map, bodyArg);
        return response;
    }


    /**
     * 字节流转字符串
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 签名
     * @param appKey 密匙
     * @param headArg 加密的参数
     * @return
     */
    public static String calSignature(String appKey,Collection<Object> headArg){
        ArrayList<String> args = new ArrayList<>();
        for (Iterator<Object> iterator = headArg.iterator(); iterator.hasNext();) {
            args.add(iterator.next().toString());
        }
        args.add(appKey);
        Collections.sort(args);
        String signature = String.join("", args);
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] sign = md5.digest(signature.getBytes("UTF-8"));
            return bytesToHex(sign);
        }catch (NoSuchAlgorithmException e){
            return "";
        }catch (UnsupportedEncodingException e){
            return "";
        }
    }


    /**
     * 获取请求结果
     * @param url
     * @param bodyArg
     * @return
     */
    public static String getResponse(String url,Map<String, Object> map, Map<String, Object> bodyArg){
        Integer appid;
        String appKey;
        if(map.get("channelType").toString().equals("0")){
            appid = APPID;
            appKey = APPKEY;
        }
        else if(map.get("channelType").toString().equals("5")){
            appid = YX_APPID;
            appKey = YX_APPKEY;
        }else{
            appid = LOT_APPID;
            appKey = LOT_APPKEY;
        }
        // 公共参数
        HashMap<String, Object> headArg = new HashMap<>(6);
        headArg.put("appid", appid);
        headArg.put("timestamp", System.currentTimeMillis()/1000);
        headArg.put("uuid", map.get("sn").toString());
        headArg.put("nonce", UUID.randomUUID().toString().replace("-",""));
        headArg.put("userid", map.get("userId").toString());
        headArg.put("token", map.get("token").toString());

        Request.Builder reqBuild = new Request.Builder().url(url);
        for (Map.Entry<String, Object> entry : headArg.entrySet()){
            reqBuild.addHeader(entry.getKey(), entry.getValue().toString());
        }
        // 遍历body参数，只有Integer和String类型参与签名
        for(Map.Entry entry : bodyArg.entrySet()){
            if(entry.getValue().getClass().getSimpleName().equals("Integer") ||entry.getValue().getClass().getSimpleName().equals("Long")
                ||entry.getValue().getClass().getSimpleName().equals("String")){
                headArg.put(entry.getKey().toString(),entry.getValue());
            }
        }
        String sign = calSignature(appKey,headArg.values());
        reqBuild.addHeader("signature",sign);

        // 构建body内容
        JSONObject bodyJSON = new JSONObject();
        for (Map.Entry<String, Object> entry : bodyArg.entrySet()){
            bodyJSON.put(entry.getKey(), entry.getValue());
        }
        RequestBody reqBody = RequestBody.create(JSON, bodyJSON.toString());
        // 实际请求
        OkHttpClient client = new OkHttpClient();
        Request request = reqBuild.post(reqBody).build();
        String body = null;
        try {
            Response response = client.newCall(request).execute();
            body = response.body().string();
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    /**
     * 激活酷狗的酷码
     * @param map
     * @return
     */
    public static String activateCode(Map<String, Object> map,String rsaCipher) {
        HashMap<String, Object> headArg = new HashMap<>(7);
        headArg.put("appid", YX_APPID);
        headArg.put("timestamp", System.currentTimeMillis()/1000);
        headArg.put("uuid", map.get(StringUtil.SN));
        headArg.put("nonce", UUID.randomUUID().toString().replace("-",""));
        headArg.put("userid", Integer.valueOf(map.get(StringUtil.USER_ID).toString()));
        headArg.put("token", map.get(StringUtil.TOKEN));
        headArg.put("code", map.get("coolCode"));
        JSONObject json = StringUtil.getJsonToMap(headArg);
        String sign = RsaUtil.MakeSign(json.toJSONString(),rsaCipher);
        String url = "https://thirdsso.kugou.com/v2/coolcode/activation";
        String result = HttpUtil.doPost(url,json,sign);
        return result;
    }
}
