package com.wplcode.wplcode.service.impl.user;

import cn.hutool.core.util.RandomUtil;
import com.wplcode.wplcode.service.user.SendVCodeService;
import com.wplcode.wplcode.utils.EmailApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SendVCodeServiceImpl implements SendVCodeService {

    private final EmailApi emailApi;
    private final StringRedisTemplate stringRedisTemplate;
    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendVCode(String email) {
        try {
            String code = RandomUtil.randomNumbers(6);
            String content = "【验证码】您的验证码为：" + code + " 。 验证码十分钟内有效，逾期作废。\n\n\n" +
                    "------------------------------\n\n\n" ;
            stringRedisTemplate.opsForValue().set("email_code_"+email, code, 600000, TimeUnit.MILLISECONDS);
            emailApi.sendGeneralEmail(username, content, email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
