package api.dongsheng.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: aioh_sw_payment
 * @description: 微信支付配置
 * @author: urbane
 * @create: 2019-12-07 16:50
 **/
@Getter
@Component
public class WeChatConfig {
    /**
     * 微信分配的公众账号ID（企业号corpid即为此appId）
     */
    @Value("${wechat.appid}")
    private String appId;
    /**
     * 商户号，微信支付分配的商户号
     */
    @Value("${wechat.mchid}")
    private String mchId;
    /**
     * API秘钥，用来加密的key
     */
    @Value("${wechat.key}")
    private String key;
    /**
     * 接收微信支付結果异步通知回调地址
     */
    @Value("${wechat.notify.url}")
    private String notifyUrl;
    /**
     * 商品描述
     */
    @Value("${wechat.body}")
    private String body;



}
