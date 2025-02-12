package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.GetContestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetContestResultController {

    private final GetContestResultService getContestResultService;

    @GetMapping("/getContestResult/")
    public JSONObject getContestResult(@RequestParam(value = "contestId") Integer contestId) {
        return getContestResultService.getContestResult(contestId);
    }
}
