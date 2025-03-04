package com.wplcode.wplcode.controller.user;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.user.FindUserExistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindUserExistController {

    private final FindUserExistService findUserExistService;

    @GetMapping("/findUserExist/")
    public JSONObject findUserExist(@RequestParam(value = "username") String username) {
        return findUserExistService.findUserExist(username);
    }
}
