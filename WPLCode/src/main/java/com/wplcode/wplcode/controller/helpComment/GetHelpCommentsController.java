package com.wplcode.wplcode.controller.helpComment;

import com.wplcode.wplcode.service.helpComment.GetHelpCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GetHelpCommentsController {

    private final GetHelpCommentsService getHelpCommentsService;

    @GetMapping("/getHelpComments/")
    public Map<String, Object> getHelpComments(@RequestParam(value = "page") Integer page) {
        return getHelpCommentsService.getHelpComments(page);
    }
}
