package com.wplcode.wplcode.service.impl.talks;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.TalkMapper;
import com.wplcode.wplcode.pojo.PO.Talk;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import com.wplcode.wplcode.service.talks.GetSendOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSendOutServiceImpl implements GetSendOutService {

    private final TalkMapper talkMapper;

    @Override
    public JSONObject getSendOut() {
        JSONObject resp = new JSONObject();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        QueryWrapper<Talk> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender", user.getUsername());
        List<Talk> sendOuts = talkMapper.selectList(queryWrapper);
        resp.set("sendouts", sendOuts);
        return resp;
    }
}
