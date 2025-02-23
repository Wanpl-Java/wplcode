package com.wplcode.wplcode.controller.talks;

import com.wplcode.wplcode.service.talks.SendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SendMessageController {

    private final SendMessageService sendMessageService;

    @PostMapping("/sendMessage/")
    public Map<String, String> sendMessage(@RequestParam Map<String, String> data) {
        return sendMessageService.sendMessage(data);
    }
}
