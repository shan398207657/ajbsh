package com.work.ssj.thirdtool.qywechat;

import cn.hutool.core.util.StrUtil;
import com.work.ssj.redis.util.RedisUtils;
import lombok.AllArgsConstructor;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class SendWxInfo {

    private RedisUtils redisUtils;
    // 凭证获取（GET）
    public final static String token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
    /**
     * 常量说明：
     * 在多企业号中，最好以每个应用来定义。
     */
    public static final int AGENTID = 1000011;
    public static final String CORPID = "ww4456f63d1694d46d";
    public static final String SECRET = "DXznHC7eh2G7lM2alqM8jV1ciqvXBHJpsCaIXR2dTUQ";
    public static final String SEND_MSG_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

    /**
     * @param touser  成员ID列表
     * @param content 消息内容，最长不超过2048个字节，注意：主页型应用推送的文本消息在微信端最多只显示20个字（包含中英文）
     */
    public void sendMsg(String touser, String content) {
        //拼接请求地址
        String requestUrl = SEND_MSG_URL.replace("ACCESS_TOKEN", getAccessToken(CORPID, SECRET));
        //需要提交的数据
        String postJson = "{\"agentid\":%s,\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\"," +
                "\"msgtype\":\"%s\",\"text\": {\"content\": \"%s\"},\"safe\":0}";

        String outputStr = String.format(postJson, AGENTID, touser, "@all", "", "text", content);
        //创建成员
       httpsRequest(requestUrl, "POST", outputStr);
    }

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuilder buffer = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (Exception ignored) {
        }
        return jsonObject;
    }

    /**
     * 获取接口访问凭证
     *
     * @param corpid     凭证
     * @param corpsecret 密钥
     */
    public String getAccessToken(String corpid, String corpsecret) {
        String access_token = redisUtils.get("qywechat_access_token");
        if (StrUtil.isEmpty(access_token)) {
            String requestUrl = token_url.replace("CORPID", corpid).replace("CORPSECRET", corpsecret);
            // 发起GET请求获取凭证
            JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
            if (null != jsonObject) {
                try {
                    access_token = jsonObject.getString("access_token");
                    redisUtils.set("qywechat_access_token", access_token, 7200);
                } catch (JSONException e) {
                    redisUtils.delete("qywechat_access_token");
                    access_token = null;
                }
            }
        }
        return access_token;
    }
}
