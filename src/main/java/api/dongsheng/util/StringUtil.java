package api.dongsheng.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @Author rx
 * @Description 工具类
 * @Date 2019/7/29 17:18
 **/
public class StringUtil {

    /**
     * 请求api接口的url地址
     */
    public static final String URL_PATH = "urlPath";
    /**
     * 请求的ip
     */
    public static final String IP = "ip";
    /**
     * token
     */
    public static final String SIGN = "sign";
    /**
     * 渠道id
     */
    public static final String CHANNELID = "channelId";
    /**
     * 渠道产品id
     */
    public static final String PRODUCTID = "productId";
    /**
     * 专辑id
     */
    public static final String ALBUMID = "albumId";
    /**
     * 电台id
     */
    public static final String RADIOID = "radioId";
    /**
     * 音频id
     */
    public static final String MUSICID = "musicId";
    /**
     * 歌曲hash值
     */
    public static final String HASH = "hash";
    /**
     * 用户手机号(第三方上传)
     */
    public static final String PASSPORT = "passport";
    /**
     * 用户唯一标识类型
     */
    public static final String PASSPORTTYPE = "passportType";
    /**
     * 用户imei
     */
    public static final String IMEI = "imei";
    /**
     * 兑换码
     */
    public static final String LICENSE = "license";
    /**
     * 分类id
     */
    public static final String CATEGORYID = "categoryId";
    /**
     * 分类id
     */
    public static final String SOURCEID = "sourceId";
    /**
     * 唯一标识
     */
    public static final String SN = "sn";
    /**
     * 设备ip
     */
    public static final String DEVICE_IP = "deviceIp";
    /**
     * 平台
     */
    public static final String PLATFORM  = "platform";
    /**
     * 腾讯音乐用户id
     */
    public static final String USER_ID = "userId";
    /**
     * 腾讯音乐用户token
     */
    public static final String TOKEN = "token";
    /**
     * 腾讯音乐酷码
     */
    public static final String COOLCODE = "coolCode";

    /**
     * 艺术家
     */
    public static final String ARTIST = "artist";

    /**
     * 查询渠道license是否使用
     */
    public static final String CHANNEL_QUERY_LICENSE = "/v2/channel/queryLicense";
    /**
     * 渠道使用license
     */
    public static final String CHANNEL_USE_LICENSE = "/v2/channel/useLicense";
    /**
     * 修改渠道license绑定的用户passport
     */
    public static final String UPDATE_LICENSE = "/v2/channel/updateLicense";
    /**
     * 获取第三方license
     */
    public static final String RSP_LICENSE = "/v2/license/getRspLicense";
    /**
     * 获取酷码
     */
    public static final String RSP_COOL_CODE = "/v2/cool/getCoolCode";
    /**
     * 修改酷码状态
     */
    public static final String UPDATE_COOL_CODE = "/v2/cool/updateCoolCode";
    /**
     * 使用会员码
     */
    public static final String CHANNEL_MEMBER_CODE = "/v2/channel/getMemberCode";

    /**
     * 获取request请求的所有参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getParam(HttpServletRequest request) {
        //获取url地址
        StringBuffer url = request.getRequestURL();
        String ip = getIpAddress(request);
        //获取参数
        Map<String, Object> map = new HashMap<>(5);
        Enumeration<String> paraNames = request.getParameterNames();
        for (Enumeration<String> e = paraNames; e.hasMoreElements(); ) {
            String thisName = e.nextElement().toString();
            String thisValue = request.getParameter(thisName);
            map.put(thisName, thisValue);
        }
        map.put(URL_PATH, url);
        map.put(IP, ip);
        return map;
    }


    /**
     * 将 Map对象转化为JavaBean
     *
     * @param map
     * @param T
     * @return
     * @throws Exception
     */
    public static <T> T convertMap2Bean(Map<String, Object> map, Class<T> T) throws Exception {
        if (map == null || map.size() == 0) {
            return null;
        }
        //获取map中所有的key值，全部更新成大写，添加到keys集合中,与mybatis中驼峰命名匹配
        Object mvalue = null;
        Map<String, Object> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            mvalue = map.get(key);
            newMap.put(key.toUpperCase(Locale.US), mvalue);
        }

        BeanInfo beanInfo = Introspector.getBeanInfo(T);
        T bean = T.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            String upperPropertyName = propertyName.toUpperCase();

            if (newMap.keySet().contains(upperPropertyName)) {
                Object value = newMap.get(upperPropertyName);
                //这个方法不会报参数类型不匹配的错误。
                BeanUtils.copyProperty(bean, propertyName, value);
            }
        }
        return bean;
    }

    /**
     * map转json
     * @param map
     * @return
     */
    public static JSONObject getJsonToMap(Map<String,Object> map){
        String data = JSONObject.toJSONString(map);
        return JSONObject.parseObject(data);
    }

    /**
     * 获取用户访问ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 验证手机号是否符合格式
     *
     * @param phone
     * @return
     */
    public static boolean phoneVerif(String phone) {
        boolean flag = false;
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() == 11) {
            if (phone.matches(regex)) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * @Author：wangh
     * @Description：json串解析存至map
     * @Date：
     */
    public static Map<String, Object> JsonToMap(JSONObject json){
        Map<String, Object> map = new HashMap<String, Object>();
        for(Object k : json.keySet()){
            Object v = json.get(k);
            if(v instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                Iterator<Object> it = ((JSONArray)v).iterator();
                while(it.hasNext()){
                    JSONObject json2 = (JSONObject) it.next();
                    list.add(JsonToMap(json2));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }
}
