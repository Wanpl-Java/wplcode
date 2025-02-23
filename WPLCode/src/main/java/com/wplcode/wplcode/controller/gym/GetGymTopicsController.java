package com.wplcode.wplcode.controller.gym;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.gym.GetGymTopicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetGymTopicsController {

    private final GetGymTopicsService gymTopicsService;

    @GetMapping("/getGymTopics/")
    public JSONObject getGymTopics() {
        return gymTopicsService.getGymTopics();
    }
}
