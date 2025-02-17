package com.wplcode.wplcode.service.contest;

import cn.hutool.json.JSONObject;

public interface GetSubmissionsService {
    public JSONObject getSubmissions(String contestId, String topicId, String username, Integer page);
}
