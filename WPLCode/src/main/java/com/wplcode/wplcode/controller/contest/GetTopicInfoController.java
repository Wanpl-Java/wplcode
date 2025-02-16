package com.wplcode.wplcode.controller.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.contest.GetTopicInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetTopicInfoController {

    private final GetTopicInfoService getTopicInfoService;

    @GetMapping("/getTopicInfo/")
    public JSONObject getTopicInfo(@RequestParam(value = "contestId") String contestId,
                                   @RequestParam(value = "topicId") String topicId) {
        return getTopicInfoService.getTopicInfo(contestId, topicId);
    }
}
