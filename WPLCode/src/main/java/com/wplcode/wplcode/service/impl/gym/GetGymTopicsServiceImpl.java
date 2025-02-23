package com.wplcode.wplcode.service.impl.gym;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.TopicMapper;
import com.wplcode.wplcode.pojo.PO.Topic;
import com.wplcode.wplcode.service.gym.GetGymTopicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetGymTopicsServiceImpl implements GetGymTopicsService {

    private final TopicMapper topicMapper;

    @Override
    public JSONObject getGymTopics() {
        JSONObject resp = new JSONObject();
        QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        List<Topic> topics = topicMapper.selectList(queryWrapper);
        resp.set("topics", topics);
        return resp;
    }
}
