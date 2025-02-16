package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.RefreshRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RefreshRankController {

    private final RefreshRankService refreshRankService;

    @GetMapping("/refreshRank/")
    public JSONObject refreshRank(@RequestParam Map<String, String> data) {
        return refreshRankService.refreshRank(data);
    }
}
