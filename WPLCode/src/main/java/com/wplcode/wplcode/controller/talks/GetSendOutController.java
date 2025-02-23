package com.wplcode.wplcode.controller.talks;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.talks.GetSendOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSendOutController {

    private final GetSendOutService getSendOutService;

    @GetMapping("/getSendOut/")
    public JSONObject getSendOut() {
        return getSendOutService.getSendOut();
    }
}
