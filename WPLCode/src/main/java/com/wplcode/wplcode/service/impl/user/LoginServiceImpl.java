package com.wplcode.wplcode.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.LoginService;
import com.wplcode.wplcode.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;

    @Override
    public Map<String, String> login(String username_or_email, String password) {
        Map<String, String> resp = new HashMap<>();
        if (username_or_email == null || username_or_email.isEmpty()) {
            resp.put("error_message", "The username or email cannot be empty!");
            return resp;
        }
        if (password == null || password.isEmpty()) {
            resp.put("error_message", "The password cannot be empty!");
            return resp;
        }
        User user = new User();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username_or_email);
        boolean ok = false;
        if (userMapper.selectCount(queryWrapper) > 0) {
            ok = true;
            user = userMapper.selectOne(queryWrapper);
        }
        if (!ok) {
            QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("email", username_or_email);
            if (userMapper.selectCount(queryWrapper1) == 0) {
                resp.put("error_message", "The username or email is wrong!");
                return resp;
            }
            user = userMapper.selectOne(queryWrapper1);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            resp.put("error_message", "The password! is wrong!");
            return resp;
        }
        String jwt = JwtUtil.createJWT(user.getId().toString());

        resp.put("error_message", "success");
        resp.put("wplcode_jwt_token", jwt);
        return resp;
    }
}
