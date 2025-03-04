package com.wplcode.wplcode.service.impl.gym;

import cn.hutool.json.JSONObject;
import com.jcraft.jsch.JSchException;
import com.wplcode.wplcode.service.gym.DebugCodeService;
import com.wplcode.wplcode.utils.gym.ExecuteCodeResponse;
import com.wplcode.wplcode.utils.gym.JavaDockerCodeSandBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DebugCodeServiceImpl implements DebugCodeService {

    @Override
    public JSONObject debugCode(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        String code = data.get("code");
        String language = data.get("language");
        String inputExample = data.get("inputExample");
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
                    resp.set("error_message", "Compile error");
                    return resp;
                }
                if (message.contains("OutOfMemoryError") || message.contains("at Main")) {
                    resp.set("error_message", "Memory limit exceed");
                    return resp;
                }
            }
            if (executeCodeResponse.getStatus() == 3) {
                resp.set("error_message", "RunTime error");
                return resp;
            }
            if (output_str != null) {
                output_str = output_str.trim();
                if (memory > 1024 * 256) {
                    resp.set("error_message", "Memory limit exceed");
                    return resp;
                }
                if (time > 1000 * 2 && "Java".equals(language) || time > 1000 * 2 && "Python".equals(language) || time > 10000 * 2 && "C++".equals(language)) {
                    resp.set("error_message", "Time limit exceed");
                    return resp;
                }
            }
            resp.set("error_message", "success");
            resp.set("output", output_str);
        } catch (InterruptedException | JSchException | IOException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}
