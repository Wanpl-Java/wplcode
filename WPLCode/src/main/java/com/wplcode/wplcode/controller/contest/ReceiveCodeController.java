package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.ReceiveCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReceiveCodeController {

    private final ReceiveCodeService receiveCodeService;

    @PostMapping("/receiveCode/")
    public JSONObject receiveCode(@RequestParam Map<String, String> data) {
        return receiveCodeService.receiveCode(data);
    }
}
