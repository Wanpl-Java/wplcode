package com.wplcode.wplcode.service.impl.user;

import com.wplcode.wplcode.service.user.GetPVService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPVServiceImpl implements GetPVService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Integer getPV(String username) {
        String s = stringRedisTemplate.opsForValue().get(username + "_pv");
        return s == null ? 0 : Integer.parseInt(s);
    }
}
