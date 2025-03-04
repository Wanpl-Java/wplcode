package com.wplcode.wplcode.controller.gym;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.gym.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/openAi/")
    public JSONObject openAi(@RequestParam Map<String, String> data) {
        return openAiService.openAi(data);
    }

    @PostMapping("/openAiDebug/")
    public JSONObject openAiDebug(@RequestParam Map<String, String> data) {
        return openAiService.openAi_debug(data);
    }
}
