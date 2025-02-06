package com.wplcode.wplcode.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import com.wplcode.wplcode.service.user.UpdateSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UpdateSettingsServiceImpl implements UpdateSettingsService {

    private final UserMapper userMapper;

    @Override
    public Map<String, String> updateSettings(Map<String, String> data) {
        Map<String, String> resp = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        String username = data.get("username");
        String province = data.get("province");
        String city = data.get("city");
        if (username == null) {
            resp.put("error_message", "The username cannot be empty!");
            return resp;
        }
        username = username.trim();
        province = province.trim();
        city = city.trim();
        if (username.isEmpty()) {
            resp.put("error_message", "The username cannot be empty!");
            return resp;
        }
        if (username.length() > 10) {
            resp.put("error_message", "The length of the username cannot exceed 10 characters!");
            return resp;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if (userMapper.selectOne(queryWrapper) != null && !username.equals(user.getUsername())) {
            resp.put("error_message", "The username already exists!");
            return resp;
        }
        userMapper.updateSettings(username, province, city, user.getId());
        resp.put("error_message", "success");
        return resp;
    }
}
