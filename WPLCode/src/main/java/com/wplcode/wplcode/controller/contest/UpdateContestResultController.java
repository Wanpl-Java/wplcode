package com.wplcode.wplcode.controller.contest;

import com.wplcode.wplcode.service.contest.UpdateContestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UpdateContestResultController {

    private final UpdateContestResultService updateContestResultService;

    @PostMapping("/updateContestResult/")
    public void updateContestResult() {
        updateContestResultService.updateContestResult();
    }
}
