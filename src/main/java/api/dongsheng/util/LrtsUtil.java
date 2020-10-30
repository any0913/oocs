package api.dongsheng.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.*;

/**
 * @Author rx
 * @Description TODO
 * @Date 2019/7/26 16:17
 **/
public class LrtsUtil {

    /**
     * 合作方加密秘钥
     */
    public static String key = "@SHfs?z?QQ%VgFk<bqdsa(99";
    /**
     * 合作方账号
     */
    public static Long partnerId = 190322001152L;
    /**
     * 编码
     */
    public static final String CHARSET = "UTF-8";
    /**
     * =
     */
    public static final String STRING_EQUAL = "=";
    /**
     * &
     */
    public static final String STRING_AND = "&";
    /**
     * ?
     */
    public static final String STRING_SPLIT = "?";


    public static void main(String[] args) {
        String url = "https://t1.aidongsheng.com/v2/open/ocs/getRealPath";
        Map<String,Object> param = new HashMap<>();
        param.put("categoryId",521);
        param.put("sourceId",3);
        param.put("albumId",236333);
        param.put("musicId",300006041);
        param.put("passport","13051576502");
        param.put("passportType",2);
        param.put("sn","tttrrreeesss");
        param.put("deviceIp","14.12.23.56");
        param.put("platform","speaker");
        param.put("userId","1592068138");
        param.put("token","262688ec405de42edef04bdfab9e0b88b1fb605209c7f45534903b66fa5ab747f339540012ffc03cc05e1d6cfbf5a06a");
        String sign = getSign(url,"@Shfs?z?bb&*%fk<bsdjs(86",param);
        param.put("channelId",9001);
        param.put("sign",sign);
        NameValuePair[] nvps = transBean2NameValuePairsParams(param);
        System.out.println(doHttpGet(url, nvps));
//        System.out.println(entityPath(36984L,100191213L));
    }

    /**
     * 获取章节播放地址
     *
     * @param bookId
     * @param resId
     */
    public static String entityPath(Long bookId, Long resId) {
        String url = "http://open.lrts.me/open/resource/entityPath";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bookId", bookId);
        params.put("resId", resId);
        String returnString = httpGet(url, params);
        return returnString;
    }


    /**
     * 传参数集合
     */
    public static String httpGet(String url, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>(0);
        }
        String token = getToken(url, key, params);
        params.put("token", token);
        params.put("partnerId", partnerId);
        NameValuePair[] nvps = transBean2NameValuePairsParams(params);
        return doHttpGet(url, nvps);
    }

    /**
     * get请求
     *
     * @param url  地址
     * @param nvps 参数
     * @return
     */
    public static String doHttpGet(String url, NameValuePair[] nvps) {
        HttpMethod get = new GetMethod(url);
        String oldQueryString = get.getQueryString();
        get.setQueryString(nvps);
        String queryString = get.getQueryString();
        if (StringUtils.isNotBlank(oldQueryString)) {
            queryString = oldQueryString + STRING_AND + queryString;
        }
        get.setQueryString(queryString);
        String bodyString = null;
        try {
            // 得到返回的response.
            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(get);
            if (statusCode == HttpStatus.SC_OK) {
                bodyString = get.getResponseBodyAsString();
            } else {
                bodyString = get.getResponseBodyAsString();
//                throw new RuntimeException("is error status ="+statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }
        return bodyString;
    }


    /**
     * 获取token 默认md5
     *
     * @param uri
     * @param userKey
     * @param params
     * @return
     */
    public static String getSign(String uri, String userKey, Map<String, Object> params) {
        if (uri.startsWith("http://") && uri.indexOf("/v2/") > -1) {
            uri = uri.substring(uri.indexOf("/v2/"));
        } else if (uri.startsWith("https://") && uri.indexOf("/v2/") > -1) {
            uri = uri.substring(uri.indexOf("/v2/"));
        }

        List<String> pKeyList = sortByASCIIAsc(params);
        StringBuilder url = new StringBuilder(uri);
        if (null != pKeyList && pKeyList.size() > 0) {
            boolean isFirst = true;
            for (String key : pKeyList) {
                if (isFirst) {
                    isFirst = false;
                    url.append(STRING_SPLIT);
                } else {
                    url.append(STRING_AND);
                }
                Object value = params.get(key);
                url.append(key).append(STRING_EQUAL).append(value == null ? "" : String.valueOf(value));
            }
        }
        return DigestUtils.md5Hex(getContentBytes(url.toString() + userKey, CHARSET));
    }

    /**
     * 获取token 默认md5
     *
     * @param uri
     * @param userKey
     * @param params
     * @return
     */
    public static String getToken(String uri, String userKey, Map<String, Object> params) {
        if (uri.startsWith("http://") && uri.indexOf("/open/") > -1) {
            uri = uri.substring(uri.indexOf("/open/"));
        } else if (uri.startsWith("http://") && uri.indexOf("/order/") > -1) {
            uri = uri.substring(uri.indexOf("/order/"));
        }
        List<String> pKeyList = sortByASCIIAsc(params);
        StringBuilder url = new StringBuilder(uri);
        if (null != pKeyList && pKeyList.size() > 0) {
            boolean isFirst = true;
            for (String key : pKeyList) {
                if (isFirst) {
                    isFirst = false;
                    url.append(STRING_SPLIT);
                } else {
                    url.append(STRING_AND);
                }
                Object value = params.get(key);
                url.append(key).append(STRING_EQUAL).append(value == null ? "" : String.valueOf(value));
            }
        }
        return DigestUtils.md5Hex(getContentBytes(url.toString() + userKey, CHARSET));
    }


    /**
     * 根据key升序排序
     *
     * @param params
     * @return
     */
    public static List<String> sortByASCIIAsc(Map<String, Object> params) {
        List<String> pKeyList = new ArrayList<>(params.keySet());
        Collections.sort(pKeyList);
        return pKeyList;
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || charset.isEmpty()) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    public static NameValuePair[] transBean2NameValuePairsParams(Map<String, Object> params) {
        NameValuePair[] result = null;
        if (params == null) {
            return result;
        }
        List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                if (e.getValue() == null) {
                    nvpList.add(new NameValuePair(e.getKey(), null));
                    continue;
                }
                if (e.getValue() instanceof String) {
                    nvpList.add(new NameValuePair(e.getKey(), (String) e.getValue()));
                } else if (e.getValue() instanceof Integer) {
                    nvpList.add(new NameValuePair(e.getKey(), String.valueOf(e.getValue())));
                } else if (e.getValue() instanceof Long) {
                    nvpList.add(new NameValuePair(e.getKey(), String.valueOf(e.getValue())));
                } else if (e.getValue() instanceof Date) {
                    nvpList.add(new NameValuePair(e.getKey(), String.valueOf(e.getValue())));
                } else {
                    nvpList.add(new NameValuePair(e.getKey(), String.valueOf(e.getValue())));
                }

            }
        }
        result = new NameValuePair[nvpList.size()];
        for (int i = 0; i < nvpList.size(); i++) {
            result[i] = nvpList.get(i);
        }
        return result;
    }
}
