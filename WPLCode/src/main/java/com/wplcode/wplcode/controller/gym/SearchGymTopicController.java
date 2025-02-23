package com.wplcode.wplcode.controller.gym;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.service.gym.SearchGymTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchGymTopicController {

    private final SearchGymTopicService searchGymTopicService;

    @GetMapping("/searchGymTopic/")
    public JSONObject searchGymTopic(@RequestParam(value = "topicName") String topicName) {
        return searchGymTopicService.searchGymTopic(topicName);
    }
}
