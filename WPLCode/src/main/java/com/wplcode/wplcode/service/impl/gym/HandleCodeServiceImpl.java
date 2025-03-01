package com.wplcode.wplcode.service.impl.gym;

import cn.hutool.json.JSONObject;
import com.jcraft.jsch.JSchException;
import com.wplcode.wplcode.mapper.GymSubmissionMapper;
import com.wplcode.wplcode.mapper.TopicMapper;
import com.wplcode.wplcode.pojo.PO.Topic;
import com.wplcode.wplcode.service.gym.HandleCodeService;
import com.wplcode.wplcode.utils.gym.ExecuteCodeResponse;
import com.wplcode.wplcode.utils.gym.JavaDockerCodeSandBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HandleCodeServiceImpl implements HandleCodeService {

    private final GymSubmissionMapper gymSubmissionMapper;
    private final TopicMapper topicMapper;

    @Override
    public JSONObject handleCode(Map<String, String> data) {
        JSONObject resp = new JSONObject();
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
            Long memory = executeCodeResponse.getJudgeInfo().getMemory();
            Long time = executeCodeResponse.getJudgeInfo().getTime();
            if (message != null) {
                if (message.contains("编译错误")) {
                    resp.set("error_message", "Compile error!");
                    return resp;
                }
                if (message.contains("OutOfMemoryError") || message.contains("at Main")) {
                    resp.set("error_message", "Memory limit exceed!");
                    return resp;
                }
            }
            if (output_str != null) {
                output_str = output_str.trim();
                // System.out.println("output_str = " + output_str.length());
                // System.out.println("outputExample = " + outputExample.length());
                if (memory > 1024 * 256) {
                    resp.set("error_message", "Memory limit exceed!");
                    return resp;
                }
                if (time > 10000 * 2) {
                    resp.set("error_message", "Time limit exceed!");
                    return resp;
                }
                if (!output_str.equals(outputExample)) {
                    resp.set("error_message", "Wrong answer!");
                    return resp;
                }
            } else {
                resp.set("error_message", "Wrong answer!");
                return resp;
            }
        } catch (InterruptedException | JSchException | IOException e) {
            throw new RuntimeException(e);
        }
        resp.set("error_message", "success");
        return resp;
    }
}
