package com.wplcode.wplcode.service.impl.user;

import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import com.wplcode.wplcode.service.user.RemoveFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveFriendServiceImpl implements RemoveFriendService {

    private final UserMapper userMapper;

    @Override
    public void removeFriend(String username) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        String[] split = user.getFriendList().split(",");
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < split.length; i ++ ) {
            if (!"".equals(split[i]) && i != split.length - 1) {
                res.append(split[i]);
                res.append(",");
            }
        }
        userMapper.updateFriends(user.getFriends() - 1, res.toString(), user.getId());
    }
}
