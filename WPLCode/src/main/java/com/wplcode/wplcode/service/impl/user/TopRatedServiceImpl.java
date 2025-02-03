package com.wplcode.wplcode.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.pojo.VO.UserVO;
import com.wplcode.wplcode.service.user.TopRatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopRatedServiceImpl implements TopRatedService {

    private final UserMapper userMapper;

    @Override
    public List<UserVO> getTopRatedUsers() {
        List<UserVO> resp = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        List<User> users = userMapper.selectList(queryWrapper);
        Integer rank = 1;
        for (User user : users) {
            UserVO userVO = new UserVO();
            userVO.setRank(rank ++);
            userVO.setUsername(user.getUsername());
            userVO.setRating(user.getRating());
            resp.add(userVO);
            if (rank > 10) {
                break;
            }
        }
        return resp;
    }
}
