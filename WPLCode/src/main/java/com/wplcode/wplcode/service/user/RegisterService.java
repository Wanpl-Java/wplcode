package com.wplcode.wplcode.service.user;

import com.wplcode.wplcode.pojo.DTO.UserDTO;

import java.util.Map;

public interface RegisterService {
    public Map<String, String> register(UserDTO userDTO);
}
