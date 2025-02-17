package com.wplcode.wplcode.controller.contest;

import com.wplcode.wplcode.service.contest.GetPassRateService;
import com.wplcode.wplcode.utils.PassRate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetPassRateController {

    private final GetPassRateService getPassRateService;

    @GetMapping("/getPassRate/")
    public List<PassRate> getPassRate(@RequestParam(value = "contestId") String contestId) {
        return getPassRateService.getPassRate(contestId);
    }
}
