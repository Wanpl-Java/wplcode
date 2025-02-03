package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.pojo.VO.UserVO;
import com.wplcode.wplcode.service.user.TopRatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TopRatedController {

    private final TopRatedService topRatedService;

    @GetMapping("/topRated/")
    public List<UserVO> getTopRatedUsers() {
        return topRatedService.getTopRatedUsers();
    }
}
