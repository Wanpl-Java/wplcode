package com.wplcode.wplcode.service.impl.gym;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.TopicMapper;
import com.wplcode.wplcode.pojo.PO.Topic;
import com.wplcode.wplcode.service.gym.SearchGymTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchGymTopicServiceImpl implements SearchGymTopicService {

    private final TopicMapper topicMapper;

    @Override
    public JSONObject searchGymTopic(String topicName) {
        JSONObject resp = new JSONObject();
        QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", topicName);
        Topic topic = null;
        topic = topicMapper.selectOne(queryWrapper);
        if (topic == null) {
            resp.set("error_message", "cannot find the topic!");
        } else {
            resp.set("error_message", "success");
        }
        resp.set("topic", topic);
        return resp;
    }
}
