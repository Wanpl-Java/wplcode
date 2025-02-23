package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.AddFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddFriendController {

    private final AddFriendService addFriendService;

    @PutMapping("/addFriend/")
    public void addFriend(@RequestParam(value = "username") String username) {
        addFriendService.addFriend(username);
    }
}
