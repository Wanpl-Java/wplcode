package com.wplcode.wplcode.utils.gym;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JavaDockerCodeSandBox extends JavaCodeSandBoxTemplate {
    private static Boolean FIRST_INIT = true;
    private static String tmp = "";

    // TODO 处理改进用户传过来的java code，便于后续docker传参
    public static String handle_java_code(String code) {
        StringBuilder java_res = new StringBuilder();
        // TODO 1.先处理"="
        for (int i = 0; i < code.length(); i ++ ) {
            // a=1
            if (i < code.length() - 1) {
                if (code.charAt(i) != ' ' && code.charAt(i) != '=' && code.charAt(i) != '>' && code.charAt(i) != '<' && code.charAt(i) != '!' && code.charAt(i + 1) == '=' || code.charAt(i) == '=' && code.charAt(i + 1) != ' ' && code.charAt(i + 1) != '=') {
                    java_res.append(code.charAt(i));
                    java_res.append(" ");
                } else {
                    java_res.append(code.charAt(i));
                }
            } else {
                java_res.append(code.charAt(i));
            }
        }
        // TODO 2.取出Scanner变量
        String[] split = java_res.toString().split(" ");
        StringBuilder toc = new StringBuilder();
        for (int i = 0; i < split.length; i ++ ) {
            if (i < split.length - 1 && "Scanner".equals(split[i])) {
                toc = new StringBuilder(split[i + 1]);
                break;
            }
        }
        // TODO 3.输入参数代码如(sc.nextInt())改为Integer.parseInt(args[cnt ++ ]) + 4.添加java_cnt
        StringBuilder java_ans = new StringBuilder();
        boolean have_java_cnt = false;
        for (int i = 0; i < split.length; i ++ ) {
            String nowStr = split[i];
            if (!have_java_cnt && nowStr.contains("(System.in)")) {
                have_java_cnt = true;
                java_ans.append(split[i]).append(" ");
                java_ans.append("int java_cnt = 0;");
                continue;
            }
            if (nowStr.contains(toc + ".nextInt()")) {
                split[i] = split[i].replace(toc + ".nextInt()", "Integer.parseInt(args[java_cnt ++ ])");
            } else if (nowStr.contains(toc + ".nextDouble()")) {
                split[i] = split[i].replace(toc + ".nextDouble()", "Double.parseDouble(args[java_cnt ++ ])");
            } else if (nowStr.contains(toc + ".nextBoolean()")) {
                split[i] = split[i].replace(toc + ".nextBoolean()", "Boolean.parseBoolean(args[java_cnt ++ ])");
            } else if (nowStr.contains(toc + ".nextFloat()")) {
                split[i] = split[i].replace(toc + ".nextFloat()", "Float.parseFloat(args[java_cnt ++ ])");
            } else if (nowStr.contains(toc + ".next()")) {
                split[i] = split[i].replace(toc + ".next()", "args[java_cnt ++ ]");
            } else if (nowStr.contains(toc + ".nextByte()")) {
                split[i] = split[i].replace(toc + ".nextByte()", "Byte.parseByte(args[java_cnt ++ ])");
            } else if (nowStr.contains(toc + ".nextShort()")) {
                split[i] = split[i].replace(toc + ".nextShort()", "Short.parseShort(args[java_cnt ++ ])");
            }
            java_ans.append(split[i]).append(" ");
        }
        // TODO 5.修改类名
        String java_ans_str = java_ans.toString();
        java_ans_str = java_ans_str.replace("class Main", "class Main" + tmp);
        System.err.println(java_ans_str);
        return java_ans_str;
    }

    // TODO 处理C++代码
    public static String handle_cpp_code(String code) {
        if (code.contains("main()")) {
            int cpp_idx = code.indexOf("main()");
            StringBuilder cpp_code = new StringBuilder(code.substring(0, cpp_idx));
            for (int i = cpp_idx; i < code.length(); i ++ ) {
                cpp_code.append(code.substring(i, i + 1));
                if (cpp_code.charAt(i) == '{') {
                    // 添加文件读取代码段
                    cpp_code.append("\n    freopen(\"input.txt\", \"r\", stdin);");
                    if (i + 1 < code.length()) {
                        cpp_code.append(code.substring(i + 1));
                    }
                    break;
                }
            }
            return cpp_code.toString();
        }
        return code;
    }

    // TODO 处理Python代码
    public static String handle_python_code(String code) {
        if (code.contains("''' show your python code! '''")) {
            code = code.replace("''' show your python code! '''", "");
        }
        code = code.trim();
        return "import sys\n" +
                "with open(\"input.txt\", \"r\") as file:\n" +
                "    sys.stdin = file\n" +
                "    " + code;
    }

    // TODO 处理Go代码
    public static String handle_go_code(String code) {
        return code;
    }

    public static ExecuteCodeResponse main(String code, String language, String inputExample) throws InterruptedException, JSchException, IOException {
        JavaDockerCodeSandBox javaDockerCodeSandBox = new JavaDockerCodeSandBox();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        // TODO docker内执行cmd操作->执行java代码的轮次
        executeCodeRequest.setInputList(Arrays.asList("", inputExample));
        // executeCodeRequest.setInputList(Arrays.asList("1 2","3 4"));
        // String code = ResourceUtil.readStr("Main.java", StandardCharsets.UTF_8);
        // System.out.println(code);
        if ("Java".equals(language)) {
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < uuid.length(); i ++ ) {
                if (uuid.charAt(i) == '-' || uuid.charAt(i) >= '0' && uuid.charAt(i) <= '9') {
                    continue;
                } else {
                    tmp += uuid.charAt(i);
                }
            }
            System.out.println("tmp = " + tmp);
        }
        /*code = "import java.util.*;\n" + "public class Main" + tmp + " {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner sc = new Scanner(System.in);\n" +
                "        int a = sc.nextInt(), b = sc.nextInt();\n" +
                "        System.out.println(a + b);\n" +
                "        System.out.println(\"Hello World\");\n" +
                "    }\n" +
                "}";*/
        // System.out.println(code);
        if ("Java".equals(language)) {
            executeCodeRequest.setCode(handle_java_code(code));
        } else if ("C++".equals(language)) {
            executeCodeRequest.setCode(handle_cpp_code(code));
        } else if ("Python".equals(language)) {
            executeCodeRequest.setCode(handle_python_code(code));
        } else if ("Go".equals(language)) {
            executeCodeRequest.setCode(handle_go_code(code));
        }
        // System.out.println(executeCodeRequest.getCode());
        executeCodeRequest.setLanguage(language);
        ExecuteCodeResponse executeCodeResponse = javaDockerCodeSandBox.codeExecute(executeCodeRequest, tmp);
        System.out.println(executeCodeResponse);
        // TODO 删除linux_java文件
        Session session = JschUtil.getSession("192.168.174.136", 22, "wpl", "123456");
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        if ("Java".equals(language)) {
            channelExec.setCommand("rm /home/wpl/wpl_java/Main" + tmp + ".java");
        } else if ("C++".equals(language)) {
            channelExec.setCommand("rm /home/wpl/wpl_cpp/input.txt /home/wpl/wpl_cpp/code.cpp");
        } else if ("Python".equals(language)) {
            channelExec.setCommand("rm /home/wpl/wpl_python/input.txt /home/wpl/wpl_python/code.py");
        } else if ("Go".equals(language)) {
            channelExec.setCommand("rm /home/wpl/wpl_go/input.txt /home/wpl/wpl_go/code.go");
        }

        InputStream in = channelExec.getInputStream();
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        channelExec.disconnect();
        session.disconnect();
        return executeCodeResponse;
    }

    // TODO 将windows文件传到linux
    public static void windows_to_linux(String windowsPath, String language) {
        Session session = JschUtil.getSession("192.168.174.136", 22, "wpl", "123456");// 这里的IP，端口，用户名和密码需要自己指定
        Sftp sftp = JschUtil.createSftp(session);
        if ("Java".equals(language)) {
            sftp.put(windowsPath, "/home/wpl/wpl_java/");
        } else if ("C++".equals(language)) {
            sftp.put(windowsPath, "/home/wpl/wpl_cpp/");
        } else if ("Python".equals(language)) {
            sftp.put(windowsPath, "/home/wpl/wpl_python/");
        } else if ("Go".equals(language)) {
            sftp.put(windowsPath, "/home/wpl/wpl_go/");
        }
        sftp.close();
    }

    // TODO 执行linux命令将文件传到docker
    public static void linux_to_docker(String containerId, String language) throws JSchException, IOException {
        Session session = JschUtil.getSession("192.168.174.136", 22, "wpl", "123456");
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        if ("Java".equals(language)) {
            channelExec.setCommand("docker cp /home/wpl/wpl_java/Main" + tmp + ".java" + " " + containerId + ":/");
        } else if ("C++".equals(language)) {
            channelExec.setCommand("docker cp /home/wpl/wpl_cpp/input.txt" + " " + containerId + ":/"
                                 + "&& docker cp /home/wpl/wpl_cpp/code.cpp" + " " + containerId + ":/");
        } else if ("Python".equals(language)) {
            channelExec.setCommand("docker cp /home/wpl/wpl_python/input.txt" + " " + containerId + ":/"
                                 + "&& docker cp /home/wpl/wpl_python/code.py" + " " + containerId + ":/");
        } else if ("Go".equals(language)) {
            channelExec.setCommand("docker cp /home/wpl/wpl_go/input.txt" + " " + containerId + ":/"
                    + "&& docker cp /home/wpl/wpl_go/code.go" + " " + containerId + ":/");
        }
        InputStream in = channelExec.getInputStream();
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        channelExec.disconnect();
        session.disconnect();
    }

    @Override
    public List<ExecuteMessage> runCode(List<String> inputList, File userCodeFile, long timeout, String language) throws JSchException, IOException {
        // TODO 将windows的文件上传到linux服务器
        if ("Java".equals(language)) {
            String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
            String windowsPath = userCodeParentPath + "\\Main" + tmp + ".java";
            System.out.println("userCodeParentPath = " + userCodeParentPath);
            System.out.println("windowsPath = " + windowsPath);
            windows_to_linux(windowsPath, language);
        } else if ("C++".equals(language)) {
            String cppPath_1 = "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\cpp\\input.txt";
            String cppPath_2 = "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\cpp\\code.cpp";
            windows_to_linux(cppPath_1, language);
            windows_to_linux(cppPath_2, language);
        } else if ("Python".equals(language)) {
            String pythonPath_1 = "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\python\\input.txt";
            String pythonPath_2 = "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\python\\code.py";
            windows_to_linux(pythonPath_1, language);
            windows_to_linux(pythonPath_2, language);
        } else if ("Go".equals(language)) {
            String goPath_1 = "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\go\\input.txt";
            String goPath_2 = "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\go\\code.go";
            windows_to_linux(goPath_1, language);
            windows_to_linux(goPath_2, language);
        }

        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerTlsVerify(false)
                // 这里填最上面填的ip端口号，ip换成服务器ip
                .withDockerHost("tcp://192.168.174.136:2375")
                //.withDockerHost("tcp://192.168.174.134:2375")
                // docker API版本号，可以用docker version查看
                //.withApiVersion("1.47")
                .withApiVersion("1.48")
                .build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        // String image = "openjdk:8-alpine";
        String image = "openjdk:8"; // TODO java默认镜像
        if ("C++".equals(language)) {
            image = "gcc";
        } else if ("Python".equals(language)) {
            image = "python:3.9";
        } else if ("Go".equals(language)) {
            image = "golang:1.22.2";
        }
        /*if (FIRST_INIT){
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback(){

                @Override
                public void onNext(PullResponseItem item) {
                    System.out.println("下载镜像 " + item.getStatus());
                    super.onNext(item);
                }
            };
            try {
                pullImageCmd.exec(pullImageResultCallback).awaitCompletion();
            } catch (InterruptedException e) {
                System.out.println("拉取镜像异常");
                throw new RuntimeException(e);
            }
            System.out.println("下载完成");
            FIRST_INIT = false;
        }*/

        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        HostConfig hostConfig = new HostConfig();
        //限制内存
        hostConfig.withMemory(100 * 1000 * 1000L);
        hostConfig.withCpuCount(1L);
        // TODO
        // hostConfig.setBinds(new Bind(userCodeParentPath,new Volume("/app")));
        CreateContainerResponse createContainerResponse = containerCmd
                .withHostConfig(hostConfig)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .withAttachStdin(true)
                .withNetworkDisabled(false)  //禁用联网功能
                .withReadonlyRootfs(false)   //禁止往root目录写文件
                .withTty(true)
                .exec();
        System.out.println(createContainerResponse);
        String containerId = createContainerResponse.getId();
        System.out.println(containerId);

        dockerClient.startContainerCmd(containerId).exec();
        linux_to_docker(containerId, language);

        // TODO wpl's code

        ArrayList<ExecuteMessage> executeMessages = new ArrayList<>();
        int cnt = 0;

        for (String input : inputList) {
            cnt ++;
            ExecuteMessage executeMessage = new ExecuteMessage();

            String[] inputArray = input.split(" ");
            String[] cmdArray = ArrayUtil.append(new String[]{"java","-cp","/app", "Main"},inputArray);
            if (cnt == 1) { // TODO 编译java代码
                if ("Java".equals(language)) {
                    cmdArray = new String[]{"javac", "Main" + tmp + ".java"};
                } else if ("C++".equals(language)) {
                    // TODO 编译c++代码
                    cmdArray = new String[]{"g++", "-o", "code", "code.cpp"};
                } else if ("Python".equals(language)) {
                    cmdArray = new String[]{"python", "code.py"};
                } else if ("Go".equals(language)) {
                    cmdArray = new String[]{"ls"};
                }
            } else if (cnt == 2) { // TODO 运行java代码
                // TODO JAVA
                if ("Java".equals(language)) {
                    cmdArray = ArrayUtil.append(new String[]{"java", "Main" + tmp}, inputArray);
                } else if ("C++".equals(language)) {
                    // TODO 运行c++代码
                    cmdArray = new String[]{"./code"};
                    // cmdArray = new String[]{"ls"};
                } else if ("Python".equals(language)) {
                    // TODO Python一步到位即可
                    cmdArray = new String[]{"cat", "input.txt"};
                } else if ("Go".equals(language)) {
                    cmdArray = new String[]{"ls"};
                }
            } else if (cnt == 3) { // TODO 暂时用不到
                cmdArray = new String[]{"mkdir", "jvm"};
            }
            ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                    .withCmd(cmdArray)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    .exec();

            System.out.println("创建执行命令: " + execCreateCmdResponse);
            String exeId = execCreateCmdResponse.getId();
            final boolean[] isTimeout = {true};

            ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback(){

                @Override
                public void onComplete() {
                    isTimeout[0] = false;

                    super.onComplete();
                }

                @Override
                public void onNext(Frame frame) {
                    StreamType streamType = frame.getStreamType();
                    if (StreamType.STDERR.equals(streamType)){
                        executeMessage.setErrorMessage(new String(frame.getPayload()));
                        System.out.println("输出错误结果: " + new String(frame.getPayload()));

                    }else {
                        executeMessage.setMessage(new String(frame.getPayload()));
                        System.out.println("输出结果: " + new String(frame.getPayload()));
                    }
                    super.onNext(frame);
                }
            };

            final Long[] maxMemory = {0L};
            StatsCmd statsCmd = dockerClient.statsCmd(containerId);
            ResultCallback<Statistics> statisticsResultCallback = statsCmd.exec(new ResultCallback<Statistics>() {
                @Override
                public void onStart(Closeable closeable) {

                }

                @Override
                public void onNext(Statistics statistics) {
                    System.out.println("内存占用情况: " + statistics.getMemoryStats().getUsage());
                    maxMemory[0] = Math.max(statistics.getMemoryStats().getUsage(), maxMemory[0]);

                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void close() throws IOException {

                }
            });
            statsCmd.close();

            try {
                //计算程序执行时间
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();

                dockerClient.execStartCmd(exeId)
                        .exec(execStartResultCallback)
                        //毫秒
                        .awaitCompletion(timeout, TimeUnit.MILLISECONDS);

                stopWatch.stop();
                executeMessage.setExecuteTime(stopWatch.getLastTaskTimeMillis());
            } catch (InterruptedException e) {
                System.out.println("程序执行异常");
                throw new RuntimeException(e);
            }

            //设置内存消耗
            executeMessage.setExecuteMemory(maxMemory[0]/1000);

            executeMessages.add(executeMessage);
        }
        dockerClient.removeContainerCmd(containerId).withForce(true).exec();
        System.out.println(executeMessages);

        return executeMessages;
    }
}
