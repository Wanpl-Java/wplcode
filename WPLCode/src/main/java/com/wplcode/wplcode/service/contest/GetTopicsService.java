package com.wplcode.wplcode.service.contest;

import cn.hutool.json.JSONObject;

public interface GetTopicsService {
    public JSONObject getTopics(String contestId);
}
