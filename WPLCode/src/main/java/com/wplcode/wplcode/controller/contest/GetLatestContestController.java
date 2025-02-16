package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.GetLatestContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetLatestContestController {

    private final GetLatestContestService getLatestContestService;

    @GetMapping("/getLatestContest/")
    public JSONObject getLatestContest() {
        return getLatestContestService.getLatestContest();
    }
}
