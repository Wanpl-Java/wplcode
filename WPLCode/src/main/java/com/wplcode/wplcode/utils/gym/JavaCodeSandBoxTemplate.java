package com.wplcode.wplcode.utils.gym;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JavaCodeSandBoxTemplate {
    private static final String GLOBAL_CODE_DIR_PATH = "src/main/java/com/wplcode/wplcode/files";
    private static String GLOBAL_JAVA_CLASS_NAME = "Main.java";
    private static final long TIME_OUT = 10000L;
    private static final Logger log = LoggerFactory.getLogger(JavaCodeSandBoxTemplate.class);

    public File saveCodeToFile(String code){

        //取到项目根目录
        String userDir = System.getProperty("user.dir");
        // todo 这里得考虑不同系统下的分隔符问题 linus windows
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_PATH;

        //如果文件路径不存在就创建目录
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        //记得把代码隔离，因为不能把所有的Main.class都放在同一个目录下
        String uuid = UUID.randomUUID().toString();
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < uuid.length(); i ++ ) {
            if (uuid.charAt(i) >= '0' && uuid.charAt(i) <= '9' || uuid.charAt(i) == '-') {
                continue;
            } else {
                tmp.append(uuid.charAt(i));
            }
        }
        String userCodeParentPath = globalCodePathName + File.separator + tmp;
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;

        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
        return userCodeFile;
    }

    public ExecuteMessage compileFile(File userCodeFile){
        System.out.println("userCodeFile = " + userCodeFile);
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());

        try {

            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");

            if (executeMessage.getExitCode() != 0){
                throw new RuntimeException("编译错误");
            }
            return executeMessage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<ExecuteMessage> runCode(List<String> inputList, File userCodeFile, long timeout, String language) throws JSchException, IOException {
        ArrayList<ExecuteMessage> executeMessages = new ArrayList<>();
        //取到项目根目录

        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
        for (String input : inputList) {
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, input);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                new Thread(() -> {
                    try {
                        Thread.sleep(timeout);
                        if (runProcess.isAlive()){
                            runProcess.destroy();
                            throw new RuntimeException("程序运行超时");
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                System.out.println(executeMessage);
                executeMessages.add(executeMessage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return executeMessages;
    }

    public ExecuteCodeResponse getOutputResponse(List<ExecuteMessage> executeMessages){
        //得到所有测试用例运行所花的最大值，有一个超时了就不符合要求
        long maxTime = 0;
        //得到所有测试用例所消耗的内存
        long maxMemery = 0;
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        ArrayList<String> outputList = new ArrayList<>();
        for (ExecuteMessage executeMessage : executeMessages) {
            String errorMessage = executeMessage.getErrorMessage();
            if (StrUtil.isNotBlank(errorMessage)) {
                //执行中存在错误，代码运行错误
                //就将报错信息放入到返回结果信息中
                executeCodeResponse.setMessage(errorMessage);
                executeCodeResponse.setStatus(3);
                break;
            }
            //没有错误就将程序成功执行的结果放入到返回的列表中
            outputList.add(executeMessage.getMessage());
            Long executeTime = executeMessage.getExecuteTime();
            Long executeMemory = executeMessage.getExecuteMemory();
            if (executeTime != null) {
                maxTime = Math.max(maxTime, executeTime);
            }
            if (executeMemory != null){
                maxMemery = Math.max(maxMemery,executeMemory);
            }

        }
        //如果全部正常执行的话
        if (outputList.size() == executeMessages.size()) {
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMemory(maxMemery);
        judgeInfo.setTime(maxTime);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }

    public boolean doDelete(File userCodeFile){
        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
        if (userCodeFile.getParentFile() != null) {
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("删除" + (del ? "成功" : "失败"));
            return del;
        }
        return true;
    }

    private ExecuteCodeResponse getErrorResponse(Throwable e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        //代表沙箱错误
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }

    public ExecuteCodeResponse codeExecute(ExecuteCodeRequest executeCodeRequest, String get_tmp) {
        ExecuteCodeResponse executeCodeResponse = null;
        File userCodeFile = null;
        try {
            List<String> inputList = executeCodeRequest.getInputList();
            String language = executeCodeRequest.getLanguage();
            String code = executeCodeRequest.getCode();
            if ("Java".equals(language)) {
                GLOBAL_JAVA_CLASS_NAME = "Main" + get_tmp + ".java";
            } else if ("C++".equals(language)) {
                try {
                    System.err.println(language);
                    // TODO C++保存文件到本地
                    // TODO 保存输入数据
                    File file_1 = FileUtil.writeString(inputList.get(1), "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\cpp\\input.txt", StandardCharsets.UTF_8);
                    // TODO 保存C++代码
                    File file_2 = FileUtil.writeString(code, "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\cpp\\code.cpp", StandardCharsets.UTF_8);
                    List<ExecuteMessage> executeMessages = runCode(inputList, null, TIME_OUT, language);
                    // 删除文件
                    doDelete(file_1);
                    doDelete(file_2);
                    executeCodeResponse = getOutputResponse(executeMessages);
                    return executeCodeResponse;
                } catch (Exception e) {

                }
            } else if ("Python".equals(language)) {
                System.err.println(language);
                return null;
            }
            userCodeFile = saveCodeToFile(code);

            ExecuteMessage executeMessage = compileFile(userCodeFile);
            System.out.println("executeMessage = " + executeMessage);
            List<ExecuteMessage> executeMessages = runCode(inputList,userCodeFile,TIME_OUT, language);
            executeCodeResponse = getOutputResponse(executeMessages);
            boolean doDelete = doDelete(userCodeFile);
            if (!doDelete) {
                log.error("delete file error,userCodeFilePath = {}",userCodeFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("ERR");
            e.printStackTrace();
            //删除用户提交的文件
            boolean doDelete = doDelete(userCodeFile);
            if (!doDelete){
                log.error("delete file error,userCodeFilePath = {}",userCodeFile.getAbsolutePath());
            }
            return getErrorResponse(e);
        }
        return executeCodeResponse;
    }
}
