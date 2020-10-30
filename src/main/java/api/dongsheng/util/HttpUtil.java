package api.dongsheng.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author rx
 * @Date 2019/9/23 14:35
 **/
public class HttpUtil {

    /**
     * post请求
     * @param url   地址
     * @param heads json
     * @return byte
     */
    public static String doHttpPost(String url, JSONObject heads) {
        String result = HttpRequest.post(url)
                .timeout(20000)//超时，毫秒
                .body(heads.toJSONString())
                .execute().body();
        return result;
    }


    /**
     * post请求
     * @param url   地址
     * @param body json
     * @param sign String 签名
     * @return byte
     */
    public static String doPost(String url, JSONObject body, String sign) {
        String result = HttpRequest.post(url)
                .header("signature", sign)
                .timeout(20000)//超时，毫秒
                .body(body.toJSONString())
                .execute().body();
        return result;
    }


    /**
     * post请求返回的参数转成String
     * @param inputStream
     * @return
     */
    public static String getString(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String str= "";
        while(true){
            try {
                if (!((str = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuffer.append(str );
        }
        return stringBuffer.toString();
    }
}
