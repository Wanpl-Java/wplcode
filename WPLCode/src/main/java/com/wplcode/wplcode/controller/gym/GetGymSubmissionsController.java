package com.wplcode.wplcode.controller.gym;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.gym.GetGymSubmissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetGymSubmissionsController {

    private final GetGymSubmissionsService gymSubmissionsService;

    @GetMapping("/getGymSubmissions/")
    public JSONObject getGymSubmissions(@RequestParam(value = "page") Integer page) {
        return gymSubmissionsService.getGymSubmissions(page);
    }
}
