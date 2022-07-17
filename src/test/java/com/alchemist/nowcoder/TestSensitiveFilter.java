package com.alchemist.nowcoder;

import com.alchemist.nowcoder.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static com.alchemist.nowcoder.util.CommunityUtil.getJSONString;

@SpringBootTest
public class TestSensitiveFilter {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void TestFilter() {
        String s = sensitiveFilter.filter("^^(开票&%*的发放赌博都发的链接&78吸&……（毒");
        System.out.println(s);
    }

    @Test
    void Test() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 25);
        System.out.println(getJSONString(0, "ok", map));
    }
}
