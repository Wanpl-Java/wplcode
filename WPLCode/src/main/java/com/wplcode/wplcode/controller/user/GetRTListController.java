package com.wplcode.wplcode.controller.user;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.user.GetRTListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetRTListController {

    private final GetRTListService getRTListService;

    @GetMapping("/getRTList/")
    public JSONObject getRTList(@RequestParam(value = "username") String username) {
        return getRTListService.getRTList(username);
    }
}
