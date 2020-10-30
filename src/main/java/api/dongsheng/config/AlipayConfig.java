package api.dongsheng.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: aioh_sw_payment
 * @description: 支付宝支付配置类
 * @author: urbane
 * @create: 2019-12-06 17:09
 **/
@Getter
@Component
public class AlipayConfig {
    /**
     * 支付宝请求网关
     */
    @Value("${alipay.url}")
    private String alipayUrl;
    /**
     * 支付宝APPID
     */
    @Value("${alipay.appid}")
    private String appId;
    /**
     * 支付宝公钥
     */
    @Value("${alipay.alipaypublickey}")
    private String alipayPublicKey;
    /**
     * 支付宝应用私钥
     */
    @Value("${alipay.alipayprivatekey}")
    private String alipayPrivateKey;
    /**
     * 异步地址通知地址
     */
    @Value("${alipay.notify.url}")
    private String notifyUrl;
    /**
     * 商品的标题/交易标题/订单标题/订单关键字等。
     */
    @Value("${alipay.subject}")
    private String subject;
    /**
     * 订单超时时间
     */
    @Value("${alipay.timeoutexpress}")
    private String timeoutExpress;
    /**
     * 销售产品码，商家和支付宝签约的产品码，为固定值 QUICK_MSECURITY_PAY
     */
    @Value("${alipay.productcode}")
    private String productCode;
    /**
     * 编码
     */
    private final static String CHARSET = "UTF-8";
    /**
     * 签名类型
     */
    private final static String SIGN_TYPE = "RSA2";
    /**
     * 格式
     */
    private final static String FORMATE = "json";
    /**
     * 同步地址
     */
    private String returnUrl;


    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(this.getAlipayUrl(),
                this.getAppId(),
                this.getAlipayPrivateKey(),
                FORMATE,
                CHARSET,
                this.getAlipayPublicKey(),
                SIGN_TYPE);
    }
}
