package com.wplcode.wplcode.service.contest;

import cn.hutool.json.JSONObject;

public interface GetTopicInfoService {
    public JSONObject getTopicInfo(String contestId, String topicId);
}
