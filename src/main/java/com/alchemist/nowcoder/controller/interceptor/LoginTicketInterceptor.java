package com.alchemist.nowcoder.controller.interceptor;

import com.alchemist.nowcoder.entity.LoginTicket;
import com.alchemist.nowcoder.entity.User;
import com.alchemist.nowcoder.service.UserService;
import com.alchemist.nowcoder.util.CookieUtil;
import com.alchemist.nowcoder.util.HostHolder;
import com.alchemist.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取凭证
        String cookie = request.getHeader("cookie");
        String ticket = CookieUtil.getValue(request, "ticket");

        if(ticket != null) {
            // 查询凭证
//            LoginTicket loginTicket = userService.findLoginTicketByTicket(ticket);
            String ticketKey = RedisKeyUtil.getTicketKey(ticket);
            LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
            // 检查此凭证是否有效
            if(loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                // 根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                // 在本次请求中持有用户
                hostHolder.setUsers(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


        User user = hostHolder.getUser();
        if(user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
