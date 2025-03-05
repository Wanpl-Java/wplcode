package com.wplcode.wplcode.service.impl.helpComment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.HelpCommentMapper;
import com.wplcode.wplcode.pojo.PO.HelpComment;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.helpComment.CheckIsExistDislikeService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckIsExistDislikeServiceImpl implements CheckIsExistDislikeService {

    private final HelpCommentMapper helpCommentMapper;

    @Override
    public Map<String, Boolean> checkIsExistDislike() {
        Map<String, Boolean> resp = new HashMap<>();
        QueryWrapper<HelpComment> queryWrapper = new QueryWrapper<>();
        List<HelpComment> helpComments = helpCommentMapper.selectList(queryWrapper);
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        for (HelpComment helpComment : helpComments) {
            Integer id = helpComment.getId();
            String[] split = helpComment.getDislikesLst().split(",");
            boolean ok = false;
            for (int i = 0; i < split.length; i ++ ) {
                if (Integer.toString(user.getId()).equals(split[i])) {
                    resp.put(Integer.toString(id), true);
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                resp.put(Integer.toString(id), false);
            }
        }
        return resp;
    }
}
