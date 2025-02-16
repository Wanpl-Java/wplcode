package com.wplcode.wplcode.controller.contest;

import com.wplcode.wplcode.service.contest.RegisterContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegisterContestController {

    private final RegisterContestService registerContestService;

    @PostMapping("/registerContest/")
    public Map<String, String> registerContest(@RequestParam(value = "contestId") String contestId,
                                               @RequestParam(value = "isOk") String isOk) {
        return registerContestService.registerContest(contestId, isOk);
    }
}
