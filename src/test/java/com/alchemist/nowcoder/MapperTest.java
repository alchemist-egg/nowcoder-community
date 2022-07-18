package com.alchemist.nowcoder;


import com.alchemist.nowcoder.dao.*;
import com.alchemist.nowcoder.entity.*;
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

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MessageMapper messageMapper;

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

    @Test
    void testInsertDiscussPost() {
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(888);
        discussPost.setTitle("apple");
        discussPost.setContent("多发点");
        discussPost.setType(0);
        discussPost.setStatus(0);
        discussPost.setCreateTime(new Date());
        discussPost.setCommentCount(8);
        discussPost.setScore(71.5);
        int i = discussPostMapper.insertDiscussPost(discussPost);
        System.out.println(i);
    }

    @Test
    void testSelectDiscussPostById() {
        DiscussPost discussPost = discussPostMapper.selectDiscussPostById(111);
        System.out.println(discussPost);
    }

    @Test
    void testSelectCommentsByEntity() {
        List<Comment> comments = commentMapper.selectCommentsByEntity(1, 228, 0, 20);
        int count = commentMapper.selectCountByEntity(1, 228);
        System.out.println("count:"+ count);
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

    @Test
    public void testSelectLetters() {
        List<Message> list = messageMapper.selectConversations(111, 0, 20);
        for (Message message : list) {
            System.out.println(message);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        list = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : list) {
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);

    }

}
