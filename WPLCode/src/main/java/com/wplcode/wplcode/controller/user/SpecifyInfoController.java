package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.user.SpecifyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpecifyInfoController {

    private final SpecifyInfoService specifyInfoService;

    @GetMapping("/specifyInfo/")
    public User specifyInfo(@RequestParam(value = "username") String username) {
        return specifyInfoService.specifyInfo(username);
    }
}
