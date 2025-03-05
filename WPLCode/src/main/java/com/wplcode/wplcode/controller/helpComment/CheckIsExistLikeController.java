package com.wplcode.wplcode.controller.helpComment;

import com.wplcode.wplcode.service.helpComment.CheckIsExistLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CheckIsExistLikeController {

    private final CheckIsExistLikeService checkIsExistLikeService;

    @GetMapping("/checkIsExistLike/")
    public Map<String, Boolean> checkIsExistLike() {
        return checkIsExistLikeService.checkIsExistLike();
    }
}
