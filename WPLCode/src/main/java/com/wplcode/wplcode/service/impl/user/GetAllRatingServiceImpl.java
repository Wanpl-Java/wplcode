package com.wplcode.wplcode.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.pojo.VO.UserVO;
import com.wplcode.wplcode.service.user.GetAllRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllRatingServiceImpl implements GetAllRatingService {

    private final UserMapper userMapper;

    @Override
    public List<UserVO> getAllRating() {
        List<UserVO> resp = new ArrayList<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");
        int rank = 1;
        for (User user : userMapper.selectList(queryWrapper)) {
            UserVO userVO = new UserVO(
                    rank ++,
                    user.getUsername(),
                    user.getRating(),
                    user.getParticipation()
            );
            resp.add(userVO);
        }
        return resp;
    }
}
