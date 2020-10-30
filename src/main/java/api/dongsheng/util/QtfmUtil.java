package api.dongsheng.util;

import api.dongsheng.model.entity.Music;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author rx
 * @Description TODO
 * @Date 2019/7/26 16:17
 **/
public class QtfmUtil {


    /**
     * ID
     */
    public static final String QTET_CLIENT_ID = "NjllODhmNmEtOTcyNy0xMWU5LWE1ZDAtMDAxNjNlMGVjZTBl";
    /**
     * 密匙
     */
    public static final String QTET_CLIENT_SECRET = "MThkYjdmNTItYTBiNC0zMzgxLWI1NmYtNmYyZDYyYzhlZTU2";
    /**
     * ID
     */
    public static final String QTCR_CLIENT_ID = "NjFlZDI1ZTItYTNiYS0xMWU5LWE1ZDAtMDAxNjNlMGVjZTBl";
    /**
     * 密匙
     */
    public static final String QTCR_CLIENT_SECRET = "ZTAzY2NmNTAtOWRjOS0zOGM2LTk2YzEtODczN2FmZmFiMWFk";
    /**
     * 正式域名
     */
    public static final String prod = "https://api.open.qingting.fm";
    /**
     * 测试域名
     *
     * @param args
     */
    public static final String test = "https://open.staging.qingting.fm";

    /**
     * 获取匿名凭证
     * @param args
     */
    public static final String ACCESS = prod + "/auth/v7/access";
    /**
     * 注册新用户
     * @param args
     */
    public static final String REG_ACCESS = prod + "/auth/v7/reg_access";
    /**
     * 使用兑换码
     * @param args
     */
    public static final String USE_CODE = prod + "/pay/v7/use_code";
    /**
     * 获取音频播放地址
     * @param args
     */
    public static final String AUDIO_URL = prod + "/media/v7/audiostream/channelondemands/";
    /**
     * 获取电台播放地址
     * @param args
     */
    public static final String RADIO_URL = prod + "/media/v7/audiostream/channellives/";



    public static void main(String[] args) {
//        String url = "/media/v7/audiostream/channelondemands/187796/programs/5523396";
////        String url = "/pay/v7/subscriptions";
////        Map<String, Object> params = new HashMap<>(1);
//        JSONObject returnString = regAccess();
//        System.out.println(returnString.toString());
        Music music = new Music();
        music.setAlbumId(275112L);
        music.setThirdId(10337591L);
        music.setSourceId(3);
        music.setIsFree(1);
        music.setType(1);
        System.out.println(getPlayURL(music,""));
//        System.out.println(getRegAccess("1",3));
//        getUseCode(2,"100000000","FY6QJ24AMBR67ZU8");
    }


    /**
     * 使用兑换码
     * @param rspId 类型2儿童，3成人
     * @param cid   用户唯一标识
     * @param license  兑换码
     * @return
     */
    public static String getUseCode(Integer rspId,String cid,String license){
        JSONObject json = getRegAccess(cid,rspId);
        JSONObject body = new JSONObject();
        body.put("user_id",json.getString("user_id"));
        body.put("code",license);

        Header header = new Header();
        header.setName("QT-Access-Token");
        header.setValue(json.getString("access_token"));
        String returnString = doHttpPost(USE_CODE, body,header);
        return returnString;
    }


    /**
     * 注册蜻蜓新用户
     * @param cid
     * @return
     */
    public static JSONObject getRegAccess(String cid,Integer rspId) {
        JSONObject heads = new JSONObject();
        heads.put("coop_user_id", cid);
        if(rspId == 2){
            heads.put("client_id", QTET_CLIENT_ID);
            heads.put("client_secret", QTET_CLIENT_SECRET);
        }else{
            heads.put("client_id", QTCR_CLIENT_ID);
            heads.put("client_secret", QTCR_CLIENT_SECRET);
        }
        heads.put("nick_name", "test");
        heads.put("avatar", "avatar");
        String bodyString = HttpUtil.doHttpPost(REG_ACCESS, heads);
        JSONObject json = null;
        if (bodyString.contains("Success")) {
            json = JSONObject.parseObject(bodyString).getJSONObject("data");
        }else{
            System.out.println(bodyString);
        }
        return json;
    }


    /**
     * 获取token令牌
     * @param rspId 类型2儿童，3成人
     * @return
     */
    public static JSONObject getAccess(Integer rspId) {
        JSONObject heads = new JSONObject();
        heads.put("grant_type", "client_credentials");
        if(rspId == 2){
            heads.put("client_id", QTET_CLIENT_ID);
            heads.put("client_secret", QTET_CLIENT_SECRET);
        }else{
            heads.put("client_id", QTCR_CLIENT_ID);
            heads.put("client_secret", QTCR_CLIENT_SECRET);
        }
        String bodyString = doHttpPost(ACCESS, heads,null);
        JSONObject json = null;
        if (bodyString.contains("Success")) {
            json = JSONObject.parseObject(bodyString).getJSONObject("data");
        }
        return json;
    }


    /**
     * 获取播放地址
     * @param music 音频信息
     * @param cid 用户唯一标识
     * @return
     */
    public static String getPlayURL(Music music,String cid){
        String url;
        if(music.getType() == 3){
            url = RADIO_URL+music.getAlbumId();
        }else{
            url = AUDIO_URL+music.getAlbumId()+"/programs/"+music.getThirdId();
        }
        Map<String, Object> params = new HashMap<>(1);
        params.put("format","mp3");
        String returnString = httpGet(music,cid,url, params);
        return returnString;
    }

    /**
     * get请求
     * @param music 音频信息
     * @param url    地址
     * @param params 参数
     * @return
     */
    public static String httpGet(Music music,String cid, String url, Map<String, Object> params) {
        JSONObject json = null;
        if(music.getIsFree() == 3){
            json = getRegAccess(cid,music.getSourceId());
        }else{
            json = getAccess(music.getSourceId());
        }
        params.put("access_token", json.getString("access_token"));
        params.put("device_id", "0");
        NameValuePair[] nvps = LrtsUtil.transBean2NameValuePairsParams(params);
        return LrtsUtil.doHttpGet(url, nvps);
    }

    /**
     * post请求
     *
     * @param url   地址
     * @param bodys json
     * @return byte
     */
    public static String doHttpPost(String url, JSONObject bodys, Header header) {
        PostMethod post = new PostMethod(url);
        post.setRequestBody(bodys.toString());
        if(header != null){
            post.setRequestHeader(header);
        }
        String bodyString = null;
        try {
            // 得到返回的response.
            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(post);
            if (statusCode == HttpStatus.SC_OK) {
                bodyString = HttpUtil.getString(post.getResponseBodyAsStream());
            } else {
                bodyString = HttpUtil.getString(post.getResponseBodyAsStream());
                throw new RuntimeException("is error status =" + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return bodyString;
    }
}
