package com.wplcode.wplcode.service.impl.user;

import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import com.wplcode.wplcode.service.user.CheckIsFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckIsFriendServiceImpl implements CheckIsFriendService {

    private final UserMapper userMapper;

    @Override
    public boolean checkIsFriend(String username) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        String[] split = user.getFriendList().split(",");
        int id = userMapper.findByUsername(username).getId();
        for (String s : split) {
            if (s.equals(String.valueOf(id))) {
                return true;
            }
        }
        return false;
    }
}
