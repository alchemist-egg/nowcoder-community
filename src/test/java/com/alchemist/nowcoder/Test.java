package com.alchemist.nowcoder;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        String a = "172.16.254.1";
        Solution.validIPAddress(a);
    }
}

class Solution {
    public static String validIPAddress(String queryIP) {

        if (queryIP.indexOf(".") >= 0) {
            String[] ips = queryIP.split("\\.");
            if (ips.length != 4) return "Neither";
            for (String ip : ips) {
                System.out.println(ip);
                if (ip.length() > 3 || (ip.length() > 1 && ip.charAt(0) == '0')) return "Neither";
                int sum = 0;
                for (int i = 0; i < ip.length(); i++) {
                    char c = ip.charAt(i);
                    if(c < '0' || c > '9') return "Neither";
                    sum = sum*10 + c - '0';
                }
                if (sum > 255) return "Neither";
            }
            return "IPv4";
        } else {
            String[] ips = queryIP.split(":");
            if (ips.length != 8) return "Neither";
            for (String ip : ips) {
                if (ip.length() > 4) return "Neither";
                for (int i = 0; i < ip.length(); i++) {
                    char c = ip.charAt(i);
                    if(!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) return "Neither";
                }
            }
            return "IPv6";
        }

    }

}
