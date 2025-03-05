package com.wplcode.wplcode.service.impl.helpComment;

import com.wplcode.wplcode.mapper.HelpCommentMapper;
import com.wplcode.wplcode.pojo.PO.HelpComment;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.helpComment.AddHelpCommentService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AddHelpCommentServiceImpl implements AddHelpCommentService {

    private final HelpCommentMapper helpCommentMapper;

    @Override
    public void addHelpComment(String content) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        HelpComment helpComment = new HelpComment(
                null,
                user.getUsername(),
                user.getPhoto(),
                user.getRating(),
                content,
                0,
                0,
                new Date(),
                "",
                ""
        );
        helpCommentMapper.insert(helpComment);
    }
}
