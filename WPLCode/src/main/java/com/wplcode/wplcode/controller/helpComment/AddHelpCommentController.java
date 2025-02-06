package com.wplcode.wplcode.controller.helpComment;

import com.wplcode.wplcode.service.helpComment.AddHelpCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddHelpCommentController {

    private final AddHelpCommentService addHelpCommentService;

    @PostMapping("/addHelpComment/")
    public void addHelpComment(@RequestParam(value = "content") String content) {
        addHelpCommentService.addHelpComment(content);
    }
}
