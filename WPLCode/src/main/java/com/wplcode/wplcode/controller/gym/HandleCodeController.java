package com.wplcode.wplcode.controller.gym;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.gym.HandleCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HandleCodeController {

    private final HandleCodeService handleCodeService;

    @PostMapping("/handleCode/")
    public JSONObject handleCode(@RequestParam Map<String, String> data) {
        return handleCodeService.handleCode(data);
    }
}
