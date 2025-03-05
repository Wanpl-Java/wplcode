package com.wplcode.wplcode.controller.helpComment;

import com.wplcode.wplcode.service.helpComment.UpdateHelpCommentDislikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateHelpCommentDislikeController {

    private final UpdateHelpCommentDislikeService updateHelpCommentDislikeService;

    @PutMapping("/updateHelpCommentDislike/")
    public void updateHelpCommentDislike(@RequestParam(value = "type") String type,
                                         @RequestParam(value = "commentId") Integer commentId) {
        updateHelpCommentDislikeService.updateHelpCommentDislike(type, commentId);
    }
}
