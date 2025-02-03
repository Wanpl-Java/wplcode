package com.wplcode.wplcode;

import cn.hutool.core.util.RandomUtil;
import com.wplcode.wplcode.utils.EmailApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class WplCodeApplicationTests {

    @Autowired
    private EmailApi emailApi;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Test
    void contextLoads() {

    }

    @Test
    void testMailSend() {
        emailApi.sendHtmlEmail("测试", "<h1>测试邮件</h1>", "3040919634@qq.com");
    }

    @Test
    void testMailSend1() {
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println(mailSender);
        message.setFrom(username);
        message.setTo("3040919634@qq.com");
        String code = RandomUtil.randomNumbers(6);
        String content = "【验证码】您的验证码为：" + code + " 。 验证码五分钟内有效，逾期作废。\n\n\n" +
                "------------------------------\n\n\n" ;
        message.setText(content);
        mailSender.send(message);
    }
}
