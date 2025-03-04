package com.wplcode.wplcode.controller.gym;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.gym.DebugCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DebugCodeController {

    private final DebugCodeService debugCodeService;

    @PostMapping("/debugCode/")
    public JSONObject debugCode(@RequestParam Map<String, String> data) {
        return debugCodeService.debugCode(data);
    }
}
