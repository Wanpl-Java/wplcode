package com.wplcode.wplcode.controller.helpComment;

import com.wplcode.wplcode.service.helpComment.CheckIsExistDislikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CheckIsExistDislikeController {

    private final CheckIsExistDislikeService checkIsExistDislikeService;

    @GetMapping("/checkIsExistDislike/")
    public Map<String, Boolean> checkIsExistDislike() {
        return checkIsExistDislikeService.checkIsExistDislike();
    }
}
