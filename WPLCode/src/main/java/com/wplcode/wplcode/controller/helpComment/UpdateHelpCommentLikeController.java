package com.wplcode.wplcode.controller.helpComment;

import com.wplcode.wplcode.service.helpComment.UpdateHelpCommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateHelpCommentLikeController {

    private final UpdateHelpCommentLikeService updateHelpCommentLikeService;

    @PutMapping("/updateHelpCommentLike/")
    public void updateHelpCommentLike(@RequestParam(value = "type") String type,
                                      @RequestParam(value = "commentId") Integer commentId) {
        updateHelpCommentLikeService.updateHelpCommentLike(type, commentId);
    }
}
