package api.dongsheng.interceptor;

import api.dongsheng.common.RequestWrapper;
import api.dongsheng.common.RetCode;
import api.dongsheng.exception.BaseException;
import api.dongsheng.model.entity.ChannelAuth;
import api.dongsheng.service.ChannelService;
import api.dongsheng.util.LrtsUtil;
import api.dongsheng.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author rx
 * @Description 授权
 * @Date 2019/8/7 10:15
 **/
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {


    private ChannelService channelService;

    public AuthInterceptor(ChannelService channelService) {
        this.channelService = channelService;
    }

    /**
     * 授权鉴定
     *
     * @param request
     * @param response
     * @param handler
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(request.getMethod().equals("GET")){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
        return true;
    }


    /**
     * post请求时验签
     * @param request
     * @param response
     */
    private void doPost(HttpServletRequest request, HttpServletResponse response){
        //获取url地址
        StringBuffer url = request.getRequestURL();
        Map<String, Object> map = new HashMap<>();
        // 获取body的值
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        Map<String,Object> bodyArg = StringUtil.getJsonToMap(JSONObject.parseObject(body));
        for(Map.Entry entry : bodyArg.entrySet()){
            if(entry.getValue().getClass().getSimpleName().equals("Integer") ||entry.getValue().getClass().getSimpleName().equals("Long")
                    ||entry.getValue().getClass().getSimpleName().equals("String")){
                map.put(entry.getKey().toString(),entry.getValue());
            }
        }
        String channelId = request.getHeader("channelId");
        String sign = request.getHeader("sign");
        if (StringUtils.isBlank(channelId) || StringUtils.isBlank(sign)) {
            log.info("请求参数         : channelId={},sign={}",channelId,sign);
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        }
        // 获取渠道key
        ChannelAuth auth = channelService.getChannelAuth(Long.valueOf(channelId));
        if (auth != null) {
            // 获取加密key
            String key = auth.getChannel_key();
            // 验证签名
            String sysSign = LrtsUtil.getSign(url.toString(), key, map);
            if (!sign.equals(sysSign)) {
                log.info("sign           : {}",sysSign);
                throw new BaseException(RetCode.TOKEN_ERROR.getCode(), RetCode.TOKEN_ERROR.getMsg());
            }
        } else {
            throw new BaseException(RetCode.CHANNEL_ID_ERROR.getCode(), RetCode.CHANNEL_ID_ERROR.getMsg());
        }
    }



    /**
     * get请求时验签
     * @param request
     * @param response
     */
    private void doGet(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = StringUtil.getParam(request);
        //获取url地址
        StringBuffer url = request.getRequestURL();
        // 当token和合作方id参数空时,参数异常
        if (!map.containsKey(StringUtil.SIGN) || !map.containsKey(StringUtil.CHANNELID)) {
            // 请求参数异常
            log.error("请求参数:" + map.toString());
            throw new BaseException(RetCode.PARAM_ERROR.getCode(), RetCode.PARAM_ERROR.getMsg());
        } else {
            // 获取请求的token
            String sign = map.get(StringUtil.SIGN).toString();
            String channelId = map.get(StringUtil.CHANNELID).toString();
            // 获取合作方信息
            ChannelAuth auth = channelService.getChannelAuth(Long.valueOf(channelId));
            if (auth != null) {
                map.remove(StringUtil.SIGN);
                map.remove(StringUtil.CHANNELID);
                map.remove(StringUtil.IP);
                map.remove(StringUtil.URL_PATH);
                // 获取加密key
                String key = auth.getChannel_key();
                //验证签名
                String sysSign = LrtsUtil.getSign(url.toString(), key, map);
                if (!sign.equals(sysSign)) {
                    log.info("sign           : {}",sysSign);
                    throw new BaseException(RetCode.TOKEN_ERROR.getCode(), RetCode.TOKEN_ERROR.getMsg());
                }
            } else {
                throw new BaseException(RetCode.CHANNEL_ID_ERROR.getCode(), RetCode.CHANNEL_ID_ERROR.getMsg());
            }
        }
    }
}
