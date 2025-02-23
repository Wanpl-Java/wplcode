package com.wplcode.wplcode.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.DTO.UserDTO;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Map<String, String> register(UserDTO userDTO) {
        Map<String, String> resp = new HashMap<>();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String confirmedPassword = userDTO.getConfirmedPassword();
        String email = userDTO.getEmail();
        String code = userDTO.getCode();
        if (username == null) {
            resp.put("error_message", "The username cannot be empty!");
            return resp;
        }
        if (password == null) {
            resp.put("error_message", "The password cannot be empty!");
            return resp;
        }
        if (confirmedPassword == null) {
            resp.put("error_message", "The confirm password cannot be empty!");
            return resp;
        }
        if (email == null) {
            resp.put("error_message", "The email cannot be empty!");
            return resp;
        }
        if (code == null) {
            resp.put("error_message", "The verification code cannot be empty!");
            return resp;
        }

        username = username.trim();
        password = password.trim();
        confirmedPassword = confirmedPassword.trim();
        email = email.trim();
        code = code.trim();

        if (username.isEmpty()) {
            resp.put("error_message", "The username cannot be empty!");
            return resp;
        }

        if (password.isEmpty()) {
            resp.put("error_message", "The password cannot be empty!");
            return resp;
        }

        if (confirmedPassword.isEmpty()) {
            resp.put("error_message", "The confirm password cannot be empty!");
            return resp;
        }

        if (email.isEmpty()) {
            resp.put("error_message", "The email cannot be empty!");
            return resp;
        }

        if (code.isEmpty()) {
            resp.put("error_message", "The verification code cannot be empty!");
            return resp;
        }

        String tmp = stringRedisTemplate.opsForValue().get("email_code_" + email);
        if (tmp == null) {
            resp.put("error_message", "The verification code has expired!");
            return resp;
        }

        if (!tmp.equals(code)) {
            resp.put("error_message", "The verification code is incorrect!");
            return resp;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if (userMapper.selectOne(queryWrapper) != null) {
            resp.put("error_message", "The username already exists!");
            return resp;
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        if (userMapper.selectOne(queryWrapper) != null) {
            resp.put("error_message", "The email already exists!");
            return resp;
        }
        if (username.length() > 10) {
            resp.put("error_message", "The length of the username cannot exceed 10 characters!");
            return resp;
        }
        if (password.length() < 6) {
            resp.put("error_message", "The password must be at least 6 characters!");
            return resp;
        }
        if (password.length() > 18) {
            resp.put("error_message", "The length of the password cannot exceed 18 characters!");
            return resp;
        }
        if (!password.equals(confirmedPassword)) {
            resp.put("error_message", "Password and confirmation password do not match!");
            return resp;
        }
        resp.put("error_message", "success");
        String encodePassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png";
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = f.format(now);
        nowTime += ",";
        User user = new User(
                null,
                username,
                encodePassword,
                email,
                photo,
                1200,
                0,
                "",
                "",
                0,
                0,
                "1200,",
                nowTime,
                ""
        );
        userMapper.insert(user);
        stringRedisTemplate.delete("email_code_" + email);
        return resp;
    }
}
