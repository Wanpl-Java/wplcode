package com.wplcode.wplcode.controller.other;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.other.GetAllSubmissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAllSubmissionsController {

    private final GetAllSubmissionsService getAllSubmissionsService;

    @GetMapping("/getAllSubmissions/")
    public JSONObject getAllSubmissions(@RequestParam(value = "page") Integer page) {
        return getAllSubmissionsService.getAllSubmissions(page);
    }
}
