package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.pojo.DTO.UserDTO;
import com.wplcode.wplcode.service.user.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register/")
    public Map<String, String> register(@RequestBody @Validated UserDTO userDTO, BindingResult bindingResult) {
        System.out.println(userDTO);
        if (bindingResult.hasErrors()) {
            Map<String, String> resp = new HashMap<>();
            resp.put("error_message", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resp;
        } else {
            return registerService.register(userDTO);
        }
    }
}
