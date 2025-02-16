package com.wplcode.wplcode.service.contest;

import cn.hutool.json.JSONObject;

import java.util.Map;

public interface ReceiveCodeService {
    public JSONObject receiveCode(Map<String, String> data);
}
