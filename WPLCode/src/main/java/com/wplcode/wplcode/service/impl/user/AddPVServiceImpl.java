package com.wplcode.wplcode.service.impl.user;

import com.wplcode.wplcode.mapper.UserMapper;
import com.wplcode.wplcode.service.user.AddPVService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPVServiceImpl implements AddPVService {

    private final StringRedisTemplate stringRedisTemplate;
    private final UserMapper userMapper;

    @Override
    public void addPV(String username) {
        Long increment = stringRedisTemplate.opsForValue().increment(username + "_pv");
        Integer visitors = Math.toIntExact(increment);
        if (visitors % 10 == 0) {
            userMapper.updateVisitors(visitors, username);
        }
        System.out.println(increment);
    }
}
