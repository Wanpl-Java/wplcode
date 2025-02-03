package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.SendVCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SendVCodeController {

    private final SendVCodeService sendVCodeService;

    @PostMapping("/sendVCode/")
    public void sendVCode(@RequestParam(value = "email") String email) {
        sendVCodeService.sendVCode(email);
    }
}
