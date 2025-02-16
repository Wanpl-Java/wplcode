package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.GetSubmissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSubmissionsController {

    private final GetSubmissionsService getSubmissionsService;

    @GetMapping("/getSubmissions/")
    public JSONObject getResults(@RequestParam(value = "contestId") String contestId,
                                 @RequestParam(value = "topicId") String topicId,
                                 @RequestParam(value = "username") String username) {
        return getSubmissionsService.getSubmissions(contestId, topicId, username);
    }
}
