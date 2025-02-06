package com.wplcode.wplcode.service.impl.user;

import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.SpecifyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecifyInfoServiceImpl implements SpecifyInfoService {

    private final UserMapper userMapper;

    @Override
    public User specifyInfo(String username) {
        return userMapper.findByUsername(username);
    }
}
