package com.wplcode.wplcode.service.gym;

import cn.hutool.json.JSONObject;

import java.util.Map;

public interface HandleCodeService {
    public JSONObject handleCode(Map<String, String> data);
}
