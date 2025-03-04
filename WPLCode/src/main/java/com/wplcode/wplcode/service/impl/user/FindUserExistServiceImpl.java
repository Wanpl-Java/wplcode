package com.wplcode.wplcode.service.impl.user;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.FindUserExistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserExistServiceImpl implements FindUserExistService {

    private final UserMapper userMapper;

    @Override
    public JSONObject findUserExist(String username) {
        JSONObject resp = new JSONObject();
        User user = userMapper.findByUsername(username);
        if (user == null) {
            resp.set("error_message", "The user is not exist!");
            return resp;
        }
        resp.set("error_message", "success");
        return resp;
    }
}
