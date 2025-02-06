package com.wplcode.wplcode.controller.user;

import com.wplcode.wplcode.pojo.VO.UserVO;
import com.wplcode.wplcode.service.user.GetAllRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetAllRatingController {

    private final GetAllRatingService getAllRatingService;

    @GetMapping("/getAllRating/")
    public List<UserVO> getAllRating() {
        return getAllRatingService.getAllRating();
    }
}
