package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.GetTopicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetTopicsController {

    private final GetTopicsService getTopicsService;

    @GetMapping("/getTopics/")
    public JSONObject getTopics(@RequestParam(value = "contestId") String contestId) {
        return getTopicsService.getTopics(contestId);
    }
}
