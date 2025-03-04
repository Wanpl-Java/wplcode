package com.wplcode.wplcode.service.gym;

import cn.hutool.json.JSONObject;

import java.util.Map;

public interface DebugCodeService {
    public JSONObject debugCode(Map<String, String> data);
}
