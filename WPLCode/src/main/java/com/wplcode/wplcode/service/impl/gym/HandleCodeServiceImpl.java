package com.wplcode.wplcode.service.impl.gym;

import cn.hutool.json.JSONObject;
import com.jcraft.jsch.JSchException;
import com.wplcode.wplcode.mapper.GymSubmissionMapper;
import com.wplcode.wplcode.mapper.TopicMapper;
import com.wplcode.wplcode.pojo.PO.GymSubmission;
import com.wplcode.wplcode.pojo.PO.Topic;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.gym.HandleCodeService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import com.wplcode.wplcode.utils.gym.ExecuteCodeResponse;
import com.wplcode.wplcode.utils.gym.JavaDockerCodeSandBox;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HandleCodeServiceImpl implements HandleCodeService {

    private final GymSubmissionMapper gymSubmissionMapper;
    private final TopicMapper topicMapper;

    @Override
    public JSONObject handleCode(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        String code = data.get("code");
        String language = data.get("language");
        Integer topicId = Integer.parseInt(data.get("topicId"));
        Topic topic = topicMapper.selectById(topicId);
        String inputExample = topic.getInputExample();
        String outputExample = topic.getOutputExample().trim();
        try {
            ExecuteCodeResponse executeCodeResponse = JavaDockerCodeSandBox.main(code, language, inputExample);
            String message = executeCodeResponse.getMessage();
            // TODO System.err.println(executeCodeResponse.getOutputList().size());
            String output_str = "";
            if (executeCodeResponse.getOutputList().size() > 1) {
                output_str = executeCodeResponse.getOutputList().get(1);
            }
            // TODO Python取第一个作为输出数据
            if (!executeCodeResponse.getOutputList().isEmpty() && "Python".equals(language)) {
                output_str = executeCodeResponse.getOutputList().get(0);
            }
            Long memory = executeCodeResponse.getJudgeInfo().getMemory();
            Long time = executeCodeResponse.getJudgeInfo().getTime();
            if (message != null) {
                if (message.contains("编译错误") || message.contains("code.cpp") && memory == 0 && time == 0) {
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
                if (message.contains("OutOfMemoryError") || message.contains("Java heap space")) {
                    resp.set("error_message", "Memory limit exceed!");
                    gymSubmissionMapper.insert(new GymSubmission(
                            null,
                            user.getId(),
                            topicId,
                            topic.getTitle(),
                            language,
                            "Memory limit exceed",
                            Math.toIntExact(time),
                            Math.toIntExact(memory),
                            new Date()
                    ));
                    return resp;
                }
            }
            if (executeCodeResponse.getStatus() == 3) {
                resp.set("error_message", "RunTime error!");
                gymSubmissionMapper.insert(new GymSubmission(
                        null,
                        user.getId(),
                        topicId,
                        topic.getTitle(),
                        language,
                        "Runtime error",
                        Math.toIntExact(time),
                        Math.toIntExact(memory),
                        new Date()
                ));
                return resp;
            }
            if (output_str != null) {
                output_str = output_str.trim();
                // System.out.println("output_str = " + output_str.length());
                // System.out.println("outputExample = " + outputExample.length());
                if (memory > 1024 * 256) {
                    resp.set("error_message", "Memory limit exceed!");
                    gymSubmissionMapper.insert(new GymSubmission(
                            null,
                            user.getId(),
                            topicId,
                            topic.getTitle(),
                            language,
                            "Memory limit exceed",
                            Math.toIntExact(time),
                            99999,
                            new Date()
                    ));
                    return resp;
                }
                if (time > 1000 * 2 && "Java".equals(language) || time > 1000 * 2 && "Python".equals(language) || time > 10000 * 2 && "C++".equals(language)) {
                    resp.set("error_message", "Time limit exceed!");
                    gymSubmissionMapper.insert(new GymSubmission(
                            null,
                            user.getId(),
                            topicId,
                            topic.getTitle(),
                            language,
                            "Time limit exceed",
                            Math.toIntExact(time),
                            Math.toIntExact(memory),
                            new Date()
                    ));
                    return resp;
                }
                if (!output_str.equals(outputExample)) {
                    resp.set("error_message", "Wrong answer!");
                    gymSubmissionMapper.insert(new GymSubmission(
                            null,
                            user.getId(),
                            topicId,
                            topic.getTitle(),
                            language,
                            "Wrong answer",
                            Math.toIntExact(time),
                            Math.toIntExact(memory),
                            new Date()
                    ));
                    return resp;
                }
            } else {
                resp.set("error_message", "Wrong answer!");
                gymSubmissionMapper.insert(new GymSubmission(
                        null,
                        user.getId(),
                        topicId,
                        topic.getTitle(),
                        language,
                        "Wrong answer",
                        Math.toIntExact(time),
                        Math.toIntExact(memory),
                        new Date()
                ));
                return resp;
            }
            gymSubmissionMapper.insert(new GymSubmission(
                    null,
                    user.getId(),
                    topicId,
                    topic.getTitle(),
                    language,
                    "Accept",
                    Math.toIntExact(time),
                    Math.toIntExact(memory),
                    new Date()
            ));
        } catch (InterruptedException | JSchException | IOException e) {
            throw new RuntimeException(e);
        }
        resp.set("error_message", "success");
        return resp;
    }
}
