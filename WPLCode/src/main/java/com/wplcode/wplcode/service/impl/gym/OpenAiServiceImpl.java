package com.wplcode.wplcode.service.impl.gym;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.mapper.GymSubmissionMapper;
import com.wplcode.wplcode.mapper.TopicMapper;
import com.wplcode.wplcode.pojo.PO.GymSubmission;
import com.wplcode.wplcode.pojo.PO.Topic;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.gym.OpenAiService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    @Autowired
    private GymSubmissionMapper gymSubmissionMapper;

    @Autowired
    private TopicMapper topicMapper;

    // 智能对话的客户端
    private final ChatClient chatClient;

    public OpenAiServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public JSONObject openAi(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        String code = data.get("code");
        String language = data.get("language");
        Integer topicId = Integer.parseInt(data.get("topicId"));
        Topic topic = topicMapper.selectById(topicId);
        String inputExample = topic.getInputExample().trim();
        String outputExample = topic.getOutputExample().trim();
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        // TODO 告诉openAi问题是什么
        String userInput = "根据下面的go代码:\n" + code + "," +
                "输入样例为" + inputExample + ", 先帮我看下代码能否编译成功,如果不能编译成功,直接回答我编译失败啦;如果能，则帮我看下代码输出结果是多少,如果输出结果是"
                + outputExample + ",则回答我答案正确;反之，则我回答我答案错误。再帮我看下这个代码是不是go语言写的，如果是，则回答我的时候需要在开头加上这一句话:" + uuid + "。";
        // TODO System.out.println(userInput);
        String message = this.chatClient.prompt() // prompt:提示词
                .user(userInput) //  用户信息
                .call() // 远程请求大模型
                .content(); // 返回文本
        System.out.println(message);
        // TODO 用户代码编译失败的情况
        if (message != null && message.contains("编译失败啦")) {
            resp.set("error_message", "Compile error!");
            gymSubmissionMapper.insert(new GymSubmission(
                    null,
                    user.getId(),
                    topicId,
                    topic.getTitle(),
                    language,
                    "Compile error",
                    0,
                    0,
                    new Date()
            ));
            return resp;
        }
        // TODO 处理用户传来的无效代码
        if (message != null && !message.contains(uuid)) {
            resp.set("error_message", "compile error!");
            gymSubmissionMapper.insert(new GymSubmission(
                    null,
                    user.getId(),
                    topicId,
                    topic.getTitle(),
                    language,
                    "Compile error",
                    0,
                    0,
                    new Date()
            ));
            return resp;
        }
        // TODO 用户代码编译成功但答案错误的情况
        if (message != null && message.contains("答案错误")) {
            resp.set("error_message", "Wrong answer!");
            gymSubmissionMapper.insert(new GymSubmission(
                    null,
                    user.getId(),
                    topicId,
                    topic.getTitle(),
                    language,
                    "Wrong answer",
                    RandomUtil.randomInt(100, 1000),
                    RandomUtil.randomInt(10, 100),
                    new Date()
            ));
            return resp;
        }
        resp.set("error_message", "success");
        gymSubmissionMapper.insert(new GymSubmission(
                null,
                user.getId(),
                topicId,
                topic.getTitle(),
                language,
                "Accept",
                RandomUtil.randomInt(100, 1000),
                RandomUtil.randomInt(10, 100),
                new Date()
        ));
        return resp;
    }

    @Override
    public JSONObject openAi_debug(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        String code = data.get("code");
        String language = data.get("language");
        String inputExample = data.get("inputExample");
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        // TODO 告诉openAi问题是什么
        String userInput = "根据下面的go代码:\n" + code + "," +
                "输入为" + inputExample + ",先帮我看下这个代码是不是go语言写的，如果是，则回答我的时候只需要在开头加上这一句话:" + uuid + "。再帮我看下代码能否编译成功,如果不能编译成功,直接回答我编译失败啦;如果能，则帮我看下代码输出结果是多少(只需要直接回答我答案是多少即可)。";
        String message = this.chatClient.prompt() // prompt:提示词
                .user(userInput) //  用户信息
                .call() // 远程请求大模型
                .content(); // 返回文本
        System.out.println(message);
        // TODO 用户代码编译失败的情况
        if (message != null && message.contains("编译失败啦")) {
            resp.set("error_message", "Compile error");
            return resp;
        }
        // TODO 处理用户传来的无效代码
        if (message != null && !message.contains(uuid)) {
            resp.set("error_message", "compile error");
            return resp;
        }
        String output = "";
        if (message != null) {
            int idx = message.indexOf("输出结果是");
            output = message.substring(idx + 5, message.length() - 1);
        }
        resp.set("output", output);
        resp.set("error_message", "success");
        return resp;
    }
}
