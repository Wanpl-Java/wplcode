package com.wplcode.wplcode.service.impl.helpComment;

import com.wplcode.wplcode.mapper.HelpCommentMapper;
import com.wplcode.wplcode.pojo.PO.HelpComment;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.helpComment.UpdateHelpCommentDislikeService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateHelpCommentDislikeServiceImpl implements UpdateHelpCommentDislikeService {

    private final HelpCommentMapper helpCommentMapper;

    @Override
    public void updateHelpCommentDislike(String type, Integer commentId) {
        HelpComment helpComment = helpCommentMapper.selectById(commentId);
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        if ("addDislike".equals(type)) {
            helpCommentMapper.updateDislikes(helpComment.getDislikes() + 1, commentId);
            helpCommentMapper.updateDislikesLst(helpComment.getDislikesLst() + user.getId() + ",", commentId);
        } else if ("removeDislike".equals(type)) {
            helpCommentMapper.updateDislikes(helpComment.getDislikes() - 1, commentId);
            String dislikesLst = helpComment.getDislikesLst();
            String[] split = dislikesLst.split(",");
            StringBuilder res = new StringBuilder();
            for (String s : split) {
                if (!s.equals(Integer.toString(user.getId()))) {
                    res.append(s).append(",");
                }
            }
            helpCommentMapper.updateDislikesLst(res.toString(), commentId);
        }
    }
}
