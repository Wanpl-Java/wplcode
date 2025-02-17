package com.wplcode.wplcode.controller.contest;

import com.wplcode.wplcode.service.contest.GetRegisterCountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetRegisterCountsController {

    private final GetRegisterCountsService getRegisterCountsService;

    @GetMapping("/getRegisterCounts/")
    public Integer getRegisterCounts(@RequestParam(value = "contestId") String contestId) {
        return getRegisterCountsService.getRegisterCounts(contestId);
    }
}
