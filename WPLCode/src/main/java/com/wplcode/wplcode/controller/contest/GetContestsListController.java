package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.GetContestsListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetContestsListController {

    private final GetContestsListService getContestsListService;

    @GetMapping("/getContestsList/")
    public JSONObject getContestsList() {
        return getContestsListService.getContestsList();
    }
}
