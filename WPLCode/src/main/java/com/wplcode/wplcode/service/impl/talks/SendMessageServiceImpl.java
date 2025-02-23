package com.wplcode.wplcode.service.impl.talks;

import com.wplcode.wplcode.mapper.TalkMapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.Talk;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import com.wplcode.wplcode.service.talks.SendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SendMessageServiceImpl implements SendMessageService {

    private final TalkMapper talkMapper;
    private final UserMapper userMapper;

    @Override
    public Map<String, String> sendMessage(Map<String, String> data) {
        Map<String, String> resp = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        String To = data.get("To");
        String Content = data.get("Content");
        // 1.判断To和Content是否为空
        if (To == null) {
            resp.put("error_message", "The to cannot be empty!");
            return resp;
        }
        if (Content == null) {
            resp.put("error_message", "The content cannot be empty!");
            return resp;
        }
        To = To.trim();
        Content = Content.trim();
        if (To.isEmpty()) {
            resp.put("error_message", "The to cannot be empty!");
            return resp;
        }
        if (Content.isEmpty()) {
            resp.put("error_message", "The content cannot be empty!");
            return resp;
        }
        // 2.判断收件方是否存在
        if (userMapper.findByUsername(To) == null) {
            resp.put("error_message", "The toUser does not exist!");
            return resp;
        }
        // 3.判断消息内容长度是否超过18
        if (Content.length() > 18) {
            resp.put("error_message", "The length of the content cannot be greater than 18!");
            return resp;
        }
        Talk talk = new Talk(
                null,
                user.getUsername(),
                To,
                Content,
                new Date()
        );
        talkMapper.insert(talk);
        resp.put("error_message", "success");
        return resp;
    }
}
