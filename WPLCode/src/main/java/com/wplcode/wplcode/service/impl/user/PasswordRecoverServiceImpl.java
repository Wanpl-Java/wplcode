package com.wplcode.wplcode.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.DTO.UserDTO;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.PasswordRecoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PasswordRecoverServiceImpl implements PasswordRecoverService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Map<String, String> passwordRecover(UserDTO userDTO) {
        Map<String, String> resp = new HashMap<>();
        String password = userDTO.getPassword();
        String confirmedPassword = userDTO.getConfirmedPassword();
        String email = userDTO.getEmail();
        String code = userDTO.getCode();
        if (email == null || email.isEmpty()) {
            resp.put("error_message", "The email cannot be empty!");
            return resp;
        }
        if (password == null || password.isEmpty()) {
            resp.put("error_message", "The password cannot be empty!");
            return resp;
        }
        if (confirmedPassword == null || confirmedPassword.isEmpty()) {
            resp.put("error_message", "The confirm password cannot be empty!");
            return resp;
        }
        if (code == null || code.isEmpty()) {
            resp.put("error_message", "The verification code cannot be empty!");
            return resp;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        List<User> users = userMapper.selectList(queryWrapper);
        if (users.isEmpty()) {
            resp.put("error_message", "The email has not been registered yet!");
            return resp;
        }
        User user = users.get(0);
        String tmp = stringRedisTemplate.opsForValue().get("email_code_" + email);
        if (tmp == null) {
            resp.put("error_message", "The verification code has expired!");
            return resp;
        }

        if (!tmp.equals(code)) {
            resp.put("error_message", "The verification code is incorrect!");
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
        String encodePassword = passwordEncoder.encode(password);
        userMapper.updatePassword(encodePassword, user.getId());
        resp.put("error_message", "success");
        return resp;
    }
}
