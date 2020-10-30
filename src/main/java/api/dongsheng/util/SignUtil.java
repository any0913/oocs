package api.dongsheng.util;

import cn.hutool.crypto.digest.DigestUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * @program: aioh_sw_content
 * @description: 加密
 * @author: urbane
 * @create: 2020-03-09 15:04
 **/
public final class SignUtil {

    private static String CHARSET = "UTF-8";

    /**
     * 牛方案2.0签名
     * @param body
     * @param appkey
     * @return
     */
    public static String getSign(String body,String appkey) {
        String data = new StringBuffer().append(body).append(appkey).toString();
        // LOGGER.info("加密串组装：[{}]",data);
        return DigestUtil.md5Hex(data);
    }


    /**
     * 签名
     * @param content  签名内容
     * @param privateKey  私匙
     * @return 返回的签名值
     */
    public static String getRsaSign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("rsa");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes(CHARSET));
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
