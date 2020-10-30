package api.dongsheng.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * rsa工具类
 */
public class RsaUtil {

    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 公匙
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 私匙
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * 测试环境私匙
     */
    private static String TEST_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIh3R6b2NJNQnvg9pr7SctpmI9aNyQTXt5koiuhj8o0lRq2fEEKT9CiTNOJz4R7PxSzGK/HJcinSTOtLGDco2TS30viLh4mE6fxbVI9wqPwuQU0uEbDOb0QQ574l2cjYx5zoLp5SbC2e+C8YWK9BLfLgQHTmGu4V98QKWvhQa5yLAgMBAAECgYEAh25ZHcmZw9lu6s3Effw9H6HeTz/HD5dGDim+MYN/Y7RqwEPrzycmWF4/Tb3S3NE1zYmecwaI1hhvXdPY5GUVqVdkRI0B0ODphFaFCSvcJfV6h4lz+PRPio8kvl1rzLeUqg3C0J2NoUSC1RnWJkwkEbladULiNool71U5dZUTnQECQQDNU2pze/BsQ0KHkCuweNLfcfy3x/ddDi8uB5/BtkM3/ginVyhZOzPIsT2GPa32HYqCpAcefa3KR82tXP4nX8wTAkEAqiVDNa6nIRy5MoKUjdlPNp/3tlpXOs7as3TxOnigYT0SYsxiImKLDX3SSNHDQJG1BLP8z2TZmFgCeUwGko8MqQJARvFvcn98TqverOBPeCYHFlVYMFfTTD++ZXUU6eWDzDDl5Ivk0Q/3kHxM+bkOBl+moAT0wMBD8ARnOGNNHWWoKQJAc5ZA5HU0IPCMd93x4bOhdrCR7hXNcKwaMxPc1SYhfi2pEelbOZTMdmvlsYafKHxUB0/nDVWr5SzU8WZOiL4ZkQJAHQLzWGQpMrQDrLHSE4hdITazfG0FGQaRXv1Ts94bzYkWnpfGhb8vb1RSF+A3VQNUxnWukAkNFqvhQCfFJZziqg==";
    /**
     * 生产环境私匙
     */
    private static String PROD_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI3nXmQCBRNtwaJIuJlb/g1x/AesttvnhBaPLXzrDWupBP5glTZ6kYPIPNEoz31AjW/C2FYTZy10T+S8074Mdp1JHGsnjay0xo0Cw1wh43TTmEJKUwH7k6YsGmzABO+Silc28nHd9L4glU09cGtTQlKMEkgLT4yAtYo/6DtHN6LHAgMBAAECgYA1RPegH0n8gyU/YIE7wNbcAUAVOXbjkkN5nIH89Nfy5F4lh+g7rUNW7yI1elZ5tHPj0ocXuOzwmUssmN375/3WenO4948NaED7wfwQPsW+pM9pwSHOANOK/+ueDWvLlfTRXzeJtc9tAfSvygQhWygXcYTMe9f/bDBLNDh+jCDFsQJBAL+PkUUU9fQjlVQqUYRrST0kjqp8R/reiY5jGfA+FaJHA7zOXjCyD6ljHqKAl7lSY+qLD0Ecbw7NenSrcCphb70CQQC9o4qfMDfPiI7NwRR95hS11beSpiQiR+VTdHT+aqA7OJ2AqozI3YJaIDQnj0oWalvfpcCDukAirkZlvmtKdlLTAkEAorJfIZtR4k8uj1N59MviYmngOfBgejP0BNkquJ8iZLeH9x4XZah2INmstTweoSnW7ue4xwZNVPc0IE9TbsteyQJAVoBwzOgrrQi3SvdVp4/5xm+0CrqqJNDytDyc2Sg96oWKopQpH76apQ2nfJwJyWu+eKecxvmAI9Gm3uYa/p42bQJAfz12h7Ksu5YP8iDMfhQQxlhs3vOTLjo3Bm4pHL3esiLkdXPJVCMZiRbxe1DD5+XO4QFya6q8x8VuZWF46Qvepw==";
    private static String CHARSET = "UTF-8";

    public static void main(String[] args) {
        HashMap<String, Object> headArg = new HashMap<>(6);
        headArg.put("appid", 3032);
        headArg.put("timestamp", System.currentTimeMillis()/1000);
        headArg.put("uuid", "tiinlab");
        headArg.put("nonce", UUID.randomUUID().toString().replace("-",""));
        headArg.put("userid", 1067065160);
        headArg.put("token", "188e15fa59283a2130d0c82d5c7c53549dc38aca5bc2393f9b3b060acc2172cbb3f4c12dcf816dbdda810facaaf2b8e0");
        headArg.put("code", "VZCAAZ4E5YVWUJN9");
        JSONObject json = StringUtil.getJsonToMap(headArg);
        String sign = MakeSign(json.toJSONString(),TEST_PRIVATE_KEY);
        String url = "https://thirdssojoin.kugou.com/v2/coolcode/activation";
        String result = HttpUtil.doPost(url,json,sign);
        System.out.println("请求结果："+result);
    }

    /**
     * 生成对应的公私钥
     * map对象中存放公私钥
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    /**
     * 签名
     * @param content  签名内容
     * @param privateKey  私匙
     * @return 返回的签名值
     */
        public static String sign(String content, String privateKey) {
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


    /**
     * sha256签名
     * @param Data
     * @param PrivateKey
     * @return
     */
    public static String MakeSign(String Data,String PrivateKey) {
        try {
            byte[] data = Data.getBytes();
            byte[] keyBytes = Base64.decode(PrivateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance("SHA256withRSA");//这个根据需求填充SHA1WithRSA或SHA256WithRSA
            signature.initSign(priKey);
            signature.update(data);
            return org.apache.commons.codec.binary.Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            return "";
        }
    }
}
