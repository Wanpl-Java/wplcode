package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.RemoveFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RemoveFriendController {

    private final RemoveFriendService removeFriendService;

    @PutMapping("/removeFriend/")
    public void removeFriend(@RequestParam(value = "username") String username) {
        removeFriendService.removeFriend(username);
    }
}
