package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.GetContestInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetContestInfoController {

    private final GetContestInfoService getContestInfoService;

    @GetMapping("/getContestInfo/")
    public JSONObject getContestInfo(@RequestParam(value = "contestId") String contestId) {
        return getContestInfoService.getContestInfo(contestId);
    }
}
