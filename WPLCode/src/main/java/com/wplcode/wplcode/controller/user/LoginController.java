package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login/")
    public Map<String, String> login(@RequestParam(value = "username_or_email") String username_or_email,
                                     @RequestParam(value = "password") String password) {
        return loginService.login(username_or_email, password);
    }
}
