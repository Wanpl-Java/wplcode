package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.mapper.ContestMapper;
import com.wplcode.wplcode.pojo.PO.Contest;
import com.wplcode.wplcode.service.contest.GetTopicsService;
import com.wplcode.wplcode.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTopicsServiceImpl implements GetTopicsService {

    private final ContestMapper contestMapper;

    @Override
    public JSONObject getTopics(String contestId) {
        JSONObject resp = new JSONObject();
        Contest contest = contestMapper.selectById(contestId);
        String content = contest.getContent();
        List<Topic> topics = new ArrayList<>();
        String[] split = content.split("#");
        String str = "A";
        for (String s : split) {
            if (!"".equals(s)) {
                String[] split1 = s.split("@");
                String topic_id = str;
                String topic_title = split1[1];
                String topic_content = split1[2];
                String input_format = split1[3];
                String output_format = split1[4];
                String data_range = split1[5];
                String input_example = split1[6];
                String output_example = split1[7];
                Topic topic = new Topic(
                        topic_id,
                        topic_title,
                        topic_content,
                        input_format,
                        output_format,
                        data_range,
                        input_example,
                        output_example
                );
                topics.add(topic);
                if ("A".equals(str)) {
                    str = "B";
                } else if ("B".equals(str)) {
                    str = "C";
                } else if ("C".equals(str)) {
                    str = "D";
                } else if ("D".equals(str)) {
                    str = "E";
                }
            }
        }
        resp.set("topics", topics);
        return resp;
    }
}
