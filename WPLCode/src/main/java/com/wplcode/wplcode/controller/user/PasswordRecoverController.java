package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.pojo.DTO.UserDTO;
import com.wplcode.wplcode.service.user.PasswordRecoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PasswordRecoverController {

    private final PasswordRecoverService passwordRecoverService;

    @PutMapping("/passwordRecover/")
    public Map<String, String> passwordRecover(@RequestBody UserDTO userDTO) {
        return passwordRecoverService.passwordRecover(userDTO);
    }
}
