package com.wplcode.wplcode;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.wplcode.wplcode.utils.EmailApi;
import com.wplcode.wplcode.utils.gym.ExecuteCodeRequest;
import com.wplcode.wplcode.utils.gym.ExecuteMessage;
import com.wplcode.wplcode.utils.gym.JavaDockerCodeSandBox;
import com.wplcode.wplcode.utils.gym.ProcessUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class WplCodeApplicationTests {

    /*private static Boolean FIRST_INIT = true;
    private static final String GLOBAL_CODE_DIR_PATH = "src/main/java/com/wplcode/wplcode/files";
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";
    private static final long TIME_OUT = 10000L;

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

    @Test
    void testDocker() {
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        String image = "openjdk:8-alpine";
        if (FIRST_INIT) {
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    System.out.println("下载镜像：" + item.getStatus());
                    super.onNext(item);
                }
            };
            try {
                pullImageCmd
                        .exec(pullImageResultCallback)
                        .awaitCompletion();
            } catch (InterruptedException e) {
                System.out.println("拉取镜像异常");
                throw new RuntimeException(e);
            }
        }
        System.out.println("下载完成");
    }


    // 1.拉取镜像
    public void pullImage(DockerClient dockerClient) {
        String image = "openjdk:8-alpine";
        if (FIRST_INIT) {
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    System.out.println("下载镜像：" + item.getStatus());
                    super.onNext(item);
                }
            };
            try {
                pullImageCmd
                        .exec(pullImageResultCallback)
                        .awaitCompletion();
            } catch (InterruptedException e) {
                System.out.println("拉取镜像异常");
                throw new RuntimeException(e);
            }
            System.out.println("下载完成");
            FIRST_INIT = false;
        }
    }

    // 2.创建容器
    public void createContainer(DockerClient dockerClient, String image) {

    }

    @Test
    public void dd() {

    }

    // 获取代码->编译代码->返回class文件
    public ExecuteMessage operateCode() {
        // 取到项目根目录
        String userDir = System.getProperty("user.dir");
        // todo 这里得考虑不同系统下的分隔符问题 linus windows
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_PATH;
        System.out.println("userDir = " + userDir);
        System.out.println("globalCodePathName = " + globalCodePathName);
        //如果文件路径不存在就创建目录
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        //记得把代码隔离，因为不能把所有的Main.class都放在同一个目录下
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;

        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World\");\n" +
                "    }\n" +
                "}";

        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
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

    @Test
    public void testWhat() {
        System.out.println("ResourceUtil.readStr(\"Main.java\", StandardCharsets.UTF_8) = " + ResourceUtil.readStr("E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\qs\\Main.java", StandardCharsets.UTF_8));
    }

    @Test
    public void testAll() {
        // 取到项目根目录
        String userDir = System.getProperty("user.dir");
        // todo 这里得考虑不同系统下的分隔符问题 linus windows
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_PATH;
        System.out.println("userDir = " + userDir);
        System.out.println("globalCodePathName = " + globalCodePathName);
        //如果文件路径不存在就创建目录
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        String uuid = UUID.randomUUID().toString();
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < uuid.length(); i ++ ) {
            if (uuid.charAt(i) >= '0' && uuid.charAt(i) <= '9' || uuid.charAt(i) == '-') {
                continue;
            } else {
                tmp.append(uuid.charAt(i));
            }
        }
        //记得把代码隔离，因为不能把所有的Main.class都放在同一个目录下
        String userCodeParentPath = globalCodePathName + File.separator + tmp;
        System.out.println("userCodeParentPath = " + userCodeParentPath);
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;

        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World\");\n" +
                "    }\n" +
                "}";

        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());

        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");

            if (executeMessage.getExitCode() != 0){
                throw new RuntimeException("编译错误");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String userCodeParentPath_2 = userCodeFile.getParentFile().getAbsolutePath();
        System.out.println("userCodeParentPath_2 = " + userCodeParentPath_2);

        List<String> inputList = new ArrayList<>();
        inputList.add("");
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        System.out.println("ResourceUtil.readStr(\"Main.java\", StandardCharsets.UTF_8) = " + ResourceUtil.readStr("E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\" + tmp + "\\Main.java", StandardCharsets.UTF_8));

        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerTlsVerify(false)
                // 这里填最上面填的ip端口号，ip换成服务器ip
                .withDockerHost("tcp://192.168.174.136:2375")
                // docker API版本号，可以用docker version查看
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

        String image = "openjdk:8-alpine";
        if (FIRST_INIT) {
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    System.out.println("下载镜像：" + item.getStatus());
                    super.onNext(item);
                }
            };
            try {
                pullImageCmd
                        .exec(pullImageResultCallback)
                        .awaitCompletion();
            } catch (InterruptedException e) {
                System.out.println("拉取镜像异常");
                throw new RuntimeException(e);
            }
            System.out.println("下载完成");
            FIRST_INIT = false;
        }

        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        HostConfig hostConfig = new HostConfig();
        hostConfig.withMemory(100 * 1000 * 1000L);
        hostConfig.withCpuCount(1L);
        // hostConfig.setBinds(new Bind(userCodeParentPath_2, new Volume("/app")));
        CreateContainerResponse createContainerResponse = containerCmd
                .withHostConfig(hostConfig)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .withAttachStdin(true)
                .withNetworkDisabled(true)  //禁用联网功能
                .withReadonlyRootfs(true)   //禁止往root目录写文件
                .withTty(true)
                .exec();
        System.out.println(createContainerResponse);
        String containerId = createContainerResponse.getId();
        // 启动容器
        dockerClient.startContainerCmd(containerId).exec();
        ArrayList<ExecuteMessage> executeMessages = new ArrayList<>();
        for (String input : inputList) {
            ExecuteMessage executeMessage = new ExecuteMessage();

            String[] inputArray = input.split(" ");
            String[] cmdArray = ArrayUtil.append(new String[]{"java","-cp","/app","Main"},inputArray);
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
                    System.out.println("frame = " + frame);
                    System.out.println("streamType = " + streamType);
                    System.out.println("StreamType.STDERR = " + StreamType.STDERR);
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
                        .awaitCompletion(5000, TimeUnit.MILLISECONDS);

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
        // 销毁容器
        dockerClient.removeContainerCmd(containerId).withForce(true).exec();
        System.out.println("executeMessages = " + executeMessages);
    }*/

    @Test
    public void test_to_linux_ftp() throws IOException {
        // 创建一个FtpClient对象
        FTPClient ftpClient = new FTPClient();
        // 创建ftp连接，默认21端口
        ftpClient.connect("192.168.174.136", 21);
        boolean login = ftpClient.login("wpl", "123456");
        System.out.println("是否登录成功：" + login);
        /*上传文件*/
        // 读取本地文件
        FileInputStream inputStream = new FileInputStream("C:\\Users\\admin\\Desktop\\idea.txt");
        // 被动模式：服务端开放端口给客户端用
        ftpClient.enterLocalPassiveMode();
        // 设置上传的路径，该路径必须已授权
        ftpClient.changeWorkingDirectory("/");
        // 修改上传文件的格式，采用二进制的方式
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        // 上传文件
        try {
            boolean storeFile = ftpClient.storeFile("idea.txt", inputStream);
            System.out.println("是否上传成功：" + storeFile); //是否上传成功
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            ftpClient.logout();
        }
    }

    @Test
    public void test_to_linux_sftp() {
        //新建会话，此会话用于ssh连接到跳板机（堡垒机），此处为xx.xx.xx.xx(IP)
        Session session = JschUtil.getSession("192.168.174.136", 22, "wpl", "123456");// 这里的IP，端口，用户名和密码需要自己指定
        Sftp sftp = JschUtil.createSftp(session);
        sftp.put("E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\utils\\TestCode.java", "/home/wpl/wpl_java/");
        sftp.close();
    }

    @Test
    public void test_linux_to_docker() throws IOException, JSchException {
        Session session = JschUtil.getSession("192.168.174.136", 22, "wpl", "123456");
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setCommand("docker cp /home/wpl/wpl_java/Main.java fb235c625d2f:/");

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
        System.out.println("output.toString() = " + output.toString());
    }

    @Test
    public void testPx() {
        String input = "1 2";
        String[] inputArray = input.split(" ");
        String[] cmdArray = ArrayUtil.append(new String[]{"java","-cp","/app","Main"},inputArray);
        System.out.println("cmdArray = " + Arrays.toString(cmdArray));
    }

    @Test
    public void handle_code() {
        String code = "import java.util.*;\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int vv == 2;\n" +
                "        Scanner sc= new Scanner(System.in);\n" +
                "        int a=sc.nextInt(), b =sc.nextInt();\n" +
                "        System.out.println(a + b);\n" +
                "    } \n" +
                "}";
        StringBuilder java_res = new StringBuilder();
        // TODO 1.先处理"="
        for (int i = 0; i < code.length(); i ++ ) {
            // a=1
            if (i < code.length() - 1) {
                if (code.charAt(i) != ' ' && code.charAt(i) != '=' && code.charAt(i + 1) == '=' || code.charAt(i) == '=' && code.charAt(i + 1) != ' ' && code.charAt(i + 1) != '=') {
                    java_res.append(code.charAt(i));
                    java_res.append(" ");
                } else {
                    java_res.append(code.charAt(i));
                }
            } else {
                java_res.append(code.charAt(i));
            }
        }
        System.out.println("res = \n" + java_res);
        // TODO 2.取出Scanner变量
        String[] split = java_res.toString().split(" ");
        // System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
        StringBuilder toc = new StringBuilder();
        for (int i = 0; i < split.length; i ++ ) {
            if (i < split.length - 1 && "Scanner".equals(split[i])) {
                toc = new StringBuilder(split[i + 1]);
                break;
            }
        }
        System.out.println("toc = " + toc);
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
        System.out.println("java_ans.toString() = \n" + java_ans);
    }

    @Test
    public void testCpp() throws JSchException, IOException, InterruptedException {
        String code = "#include <iostream>\n" +
                "\n" +
                "using namespace std;\n" +
                "\n" +
                "int main() {\n" +
                "    int a, b;\n" +
                "    cin >> a >> b;\n" +
                "    cout << a * b << endl;\n" +
                "    cout << \"hello wpl!\" << endl;\n" +
                "    return 0;\n" +
                "}\n";
        System.out.println(code);
        JavaDockerCodeSandBox.main(code, "C++", "3 7");
    }

    @Test
    public void handle_cpp_code() {
        String code = "#include <iostream>\n" +
                "\n" +
                "using namespace std;\n" +
                "\n" +
                "int main() {\n" +
                "    int a, b;" +
                "    cin >> a >> b;" +
                "    cout << a * b << endl;" +
                "    cout << \"hello wpl!\" << endl;\n" +
                "    return 0;\n" +
                "}\n";
        if (code.contains("main()")) {
            int cpp_idx = code.indexOf("main()");
            StringBuilder cpp_code = new StringBuilder(code.substring(0, cpp_idx));
            for (int i = cpp_idx; i < code.length(); i ++ ) {
                cpp_code.append(code.substring(i, i + 1));
                if (cpp_code.charAt(i) == '{') {
                    // 添加文件读取代码段
                    cpp_code.append("\n    freopen(\"wpl.txt\", \"r\", stdin);");
                    if (i + 1 < code.length()) {
                        cpp_code.append(code.substring(i + 1));
                    }
                    break;
                }
            }
            System.out.println(cpp_code);
        }
    }

    @Test
    public void saveCppFileToLocal() {
        FileUtil.writeString("1 2", "E:\\wplcode\\wplcode\\src\\main\\java\\com\\wplcode\\wplcode\\files\\cpp\\input.txt", StandardCharsets.UTF_8);
    }
}
