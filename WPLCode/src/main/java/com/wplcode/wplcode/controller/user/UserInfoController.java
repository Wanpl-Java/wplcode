package com.wplcode.wplcode.controller.user;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/userinfo/")
    public JSONObject getUserInfo() {
        return userInfoService.getUserInfo();
    }
}
