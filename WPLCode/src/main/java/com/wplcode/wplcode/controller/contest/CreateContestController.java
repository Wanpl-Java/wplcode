package com.wplcode.wplcode.controller.contest;

import com.wplcode.wplcode.service.contest.CreateContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CreateContestController {

    private final CreateContestService createContestService;

    @PostMapping("/createContest/")
    public void createContest(@RequestParam Map<String, String> data) throws ParseException {
        createContestService.createContest(data);
    }
}
