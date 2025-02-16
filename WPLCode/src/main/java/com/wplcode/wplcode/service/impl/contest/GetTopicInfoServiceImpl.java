package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.mapper.ContestMapper;
import com.wplcode.wplcode.pojo.PO.Contest;
import com.wplcode.wplcode.service.contest.GetTopicInfoService;
import com.wplcode.wplcode.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTopicInfoServiceImpl implements GetTopicInfoService {

    private final ContestMapper contestMapper;

    @Override
    public JSONObject getTopicInfo(String contestId, String topicId) {
        JSONObject resp = new JSONObject();
        Contest contest = contestMapper.selectById(Integer.parseInt(contestId));
        String content = contest.getContent();
        String[] split = content.split("#");
        String topicContent = "";
        if ("A".equals(topicId)) {
            topicContent = split[1];
        } else if ("B".equals(topicId)) {
            topicContent = split[2];
        } else if ("C".equals(topicId)) {
            topicContent = split[3];
        } else if ("D".equals(topicId)) {
            topicContent = split[4];
        } else if ("E".equals(topicId)) {
            topicContent = split[5];
        }
        String[] split1 = topicContent.split("@");
        String topic_title = split1[1];
        String topic_content = split1[2];
        String topic_input_format = split1[3];
        String topic_output_format = split1[4];
        String data_range = split1[5];
        String input_example = split1[6];
        String output_example = split1[7];
        Topic topic = new Topic(
                topicId,
                topic_title,
                topic_content,
                topic_input_format,
                topic_output_format,
                data_range,
                input_example,
                output_example
        );
        resp.set("topic", topic);
        // resp.set("topic_title", topic_title);
        // resp.set("topic_content", topic_content);
        // resp.set("topic_input_format", topic_input_format);
        // resp.set("topic_output_format", topic_output_format);
        // resp.set("data_range", data_range);
        // resp.set("input_example", input_example);
        // resp.set("output_example", output_example);
        return resp;
    }
}
