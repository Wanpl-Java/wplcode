package com.wplcode.wplcode.service.impl.helpComment;

import com.wplcode.wplcode.mapper.HelpCommentMapper;
import com.wplcode.wplcode.pojo.PO.HelpComment;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.helpComment.UpdateHelpCommentLikeService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateHelpCommentLikeServiceImpl implements UpdateHelpCommentLikeService {

    private final HelpCommentMapper helpCommentMapper;

    @Override
    public void updateHelpCommentLike(String type, Integer commentId) {
        HelpComment helpComment = helpCommentMapper.selectById(commentId);
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        if ("addLike".equals(type)) {
            helpCommentMapper.updateLikes(helpComment.getLikes() + 1, commentId);
            helpCommentMapper.updateLikesLst(helpComment.getLikesLst() + user.getId() + ",", commentId);
        } else if ("removeLike".equals(type)) {
            helpCommentMapper.updateLikes(helpComment.getLikes() - 1, commentId);
            String likesLst = helpComment.getLikesLst();
            String[] split = likesLst.split(",");
            StringBuilder res = new StringBuilder();
            for (String s : split) {
                if (!s.equals(Integer.toString(user.getId()))) {
                    res.append(s).append(",");
                }
            }
            helpCommentMapper.updateLikesLst(res.toString(), commentId);
        }
    }
}
