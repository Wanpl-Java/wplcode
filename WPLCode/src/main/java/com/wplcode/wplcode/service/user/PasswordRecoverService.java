package com.wplcode.wplcode.service.user;

import com.wplcode.wplcode.pojo.DTO.UserDTO;

import java.util.Map;

public interface PasswordRecoverService {
    public Map<String, String> passwordRecover(UserDTO userDTO);
}
