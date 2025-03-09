package com.wplcode.wplcode.controller.XunFeiAi;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.utils.XunFeiAi.SparkManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class XunFeiAiController {

    private final SparkManager sparkManager;

    @GetMapping("/xunFeiAi/")
    public JSONObject xunFeiAi(@RequestParam(value = "userInput") String userInput) {
        JSONObject resp = new JSONObject();
        resp.set("error_message", sparkManager.sendHttpTOSpark(userInput));
        return resp;
    }
}
