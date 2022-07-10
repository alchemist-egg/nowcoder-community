package com.alchemist.nowcoder;


import com.alchemist.nowcoder.dao.DiscussPostMapper;
import com.alchemist.nowcoder.dao.LoginTicketMapper;
import com.alchemist.nowcoder.dao.UserMapper;
import com.alchemist.nowcoder.entity.DiscussPost;
import com.alchemist.nowcoder.entity.LoginTicket;
import com.alchemist.nowcoder.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
//@ContextConfiguration(classes = NowcoderCommunityApplication.class)
public class MapperTest {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);
    }

    @Test
    void testSelectDiscussPostList() {
        List<DiscussPost> list = discussPostMapper.selectDiscussPost(149, 0, 10);
        for(DiscussPost dis : list) {
            System.out.println(list);
        }

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

    @Test
    public void testLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000*60*10) );

        loginTicketMapper.insertLoginTicket(loginTicket);

        LoginTicket loginTicket2 = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket2);

        loginTicketMapper.updateStatus("abc", 1);
        loginTicket2 = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket2);
    }

}
