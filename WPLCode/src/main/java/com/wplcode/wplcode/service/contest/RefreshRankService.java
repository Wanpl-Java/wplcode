package com.wplcode.wplcode.service.contest;

import cn.hutool.json.JSONObject;

import java.util.Map;

public interface RefreshRankService {
    public JSONObject refreshRank(Map<String, String> data);
}
