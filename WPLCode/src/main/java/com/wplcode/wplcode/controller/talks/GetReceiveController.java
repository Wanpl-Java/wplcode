package com.wplcode.wplcode.controller.talks;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.talks.GetReceiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetReceiveController {

    private final GetReceiveService getReceiveService;

    @GetMapping("/getReceive/")
    public JSONObject getReceive() {
        return getReceiveService.getReceive();
    }
}
