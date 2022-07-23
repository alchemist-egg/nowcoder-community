package com.alchemist.nowcoder.controller;

import com.alchemist.nowcoder.entity.Event;
import com.alchemist.nowcoder.entity.User;
import com.alchemist.nowcoder.event.EventProducer;
import com.alchemist.nowcoder.service.LikeService;
import com.alchemist.nowcoder.util.CommunityConstant;
import com.alchemist.nowcoder.util.CommunityUtil;
import com.alchemist.nowcoder.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConstant{

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;


    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId) {
        User user = hostHolder.getUser();
        // 点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        // 点赞数
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 当前用户点赞状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);

        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        // 触发点赞事件
        if (likeStatus == 1) {
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);
            eventProducer.fireEvent(event);
        }

        return CommunityUtil.getJSONString(0, null, map);

    }

}
