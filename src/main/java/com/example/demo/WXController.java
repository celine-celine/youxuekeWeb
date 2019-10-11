package com.example.demo;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/wx")
public class WXController {
    private static String appId = "wx1cdd6220c0c38a92";
    private static String appSecret = "79cf7468ad1d5b4f8d5bf974c546c5cb";
    private static String grantType = "authorization_code";
    private static String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
    public  static String ssKey;

    @RequestMapping("/session")
    public String getSession(@RequestParam("code") String code) {
        return this.getSessionByCode(code);
    }

    @SuppressWarnings("unchecked")
    private String getSessionByCode(String code) {
        String url = requestUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type="
                + grantType;
        // 发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = null;
        try {
            json = mapper.readValue(data, Map.class);
            ssKey = (String) json.get("session_key");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 形如{"session_key":"6w7Br3JsRQzBiGZwvlZAiA==","openid":"oQO565cXXXXXEvc4Q_YChUE8PqB60Y"}的字符串
        return (String) json.get("openid");
    }
}
