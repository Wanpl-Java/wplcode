package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.CheckIsFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckIsFriendController {

    private final CheckIsFriendService checkIsFriendService;

    @GetMapping("/checkIsFriend/")
    public boolean checkIsFriend(@RequestParam(value = "username") String username) {
        return checkIsFriendService.checkIsFriend(username);
    }
}
