package com.alchemist.nowcoder.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

public class CommunityUtil {

    //生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //MD5加密
     public static String md5(String key) {
        if(StringUtils.isBlank(key)) {
            return null;
        }
        //Spring自带
         return DigestUtils.md5DigestAsHex(key.getBytes());
     }

    // 序列化
    // ① 服务器给浏览器返回编码（code），比如0代表什么意思，1代表什么意思
    // ② 服务器给浏览器返回提示信息（msg），成果或失败
    // ③ 还可能返回业务数据（map）
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }
}
