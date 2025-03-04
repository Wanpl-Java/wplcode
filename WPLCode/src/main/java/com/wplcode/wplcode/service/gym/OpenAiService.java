package com.wplcode.wplcode.service.gym;

import cn.hutool.json.JSONObject;

import java.util.Map;

public interface OpenAiService {
    public JSONObject openAi(Map<String, String> data);

    public JSONObject openAi_debug(Map<String, String> data);
}
