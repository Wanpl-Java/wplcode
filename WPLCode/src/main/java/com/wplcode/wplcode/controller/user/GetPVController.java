package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.service.user.GetPVService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPVController {

    private final GetPVService getPVService;

    @GetMapping("/getPV/")
    public Integer getPV(@RequestParam(value = "username") String username) {
        return getPVService.getPV(username);
    }
}
