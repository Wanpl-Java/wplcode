package com.wplcode.wplcode.service.user;

import cn.hutool.json.JSONObject;

public interface FindUserExistService {
    public JSONObject findUserExist(String username);
}
