package com.wplcode.wplcode.service.impl.contest;

import cn.hutool.json.JSONObject;
import com.wplcode.wplcode.mapper.SubmissionMapper;
import com.wplcode.wplcode.pojo.PO.Submission;
import com.wplcode.wplcode.pojo.PO.User;
import com.wplcode.wplcode.service.contest.ReceiveCodeService;
import com.wplcode.wplcode.service.impl.utils.UserDetailsImpl;
import com.wplcode.wplcode.utils.CodeInterface;
import lombok.RequiredArgsConstructor;
import org.joor.Reflect;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReceiveCodeServiceImpl implements ReceiveCodeService {

    private final SubmissionMapper submissionMapper;

    private String addUid(String code, String uid) {
        int k = code.indexOf(" implements com.wplcode.wplcode.utils.CodeInterface");
        return code.substring(0, k) + uid + code.substring(k);
    }

    private List<String> fixCode(String code) {
        code = code.trim();
        List<String> lst = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);
        code += uid;
        int start_k = code.indexOf("public class Main");
        String imp = code.substring(0, start_k);
        int k = code.indexOf("(String[] args)");
        int middle_k = k;
        boolean ok = false;
        for (int i = k; i < code.length(); i ++ ) {
            if (code.charAt(i) == '{') {
                ok = true;
                continue;
            }
            if (ok && code.charAt(i) == '\n') {
                middle_k = i + 1;
                break;
            }
            if (ok && code.charAt(i) != '\n' && code.charAt(i) != ' ') {
                middle_k = i;
                break;
            }
        }
        int end_k = code.indexOf("}" + uid);
        code = code.substring(0, end_k);
        for (int i = end_k - 1; i >= 0; i -- ) {
            if (code.charAt(i) == '}') {
                end_k = i;
                break;
            }
        }
        lst.add(imp);
        lst.add(code.substring(middle_k, end_k));
        return lst;
    }

    private String removeScanner(String code, String[] strs) {
        int idx = 0;
        String symbol = "";
        String[] s = code.split(" ");
        boolean ok = false;
        for (String str : s) {
            if ("Scanner".equals(str)) {
                ok = true;
                continue;
            }
            if (ok) {
                symbol = str;
                break;
            }
        }

        // lef前一个leff前两个
        String newCode = "", lef = "", leff = "";
        for (String str : s) {
            if (!leff.isEmpty()) {
                leff = str;
                newCode += str + " ";
                continue;
            }
            if (!lef.isEmpty()) {
                lef = str;
                newCode += str + " ";
                continue;

            }
            String res = symbol + ".next";
            if (str.contains(res + "Int()")) {
                str = str.replace(res + "Int()", strs[idx ++ ]);
            }
            if (str.contains(res + "Long()")) {
                str = str.replace(res + "Long()", strs[idx ++ ]);
            }
            if (str.contains(res + "Double()")) {
                str = str.replace(res + "Double()", strs[idx ++ ]);
            }
            if (str.contains(res + "Float()")) {
                str = str.replace(res + "Float()", strs[idx ++ ]);
            }
            if (str.contains(res + "()")) {
                str = str.replace(res + "()", strs[idx ++ ]);
            }
            if (str.contains(res + "Boolean()")) {
                str = str.replace(res + "Boolean()", strs[idx ++ ]);
            }
            if (str.contains(res + "Byte()")) {
                str = str.replace(res + "Byte()", strs[idx ++ ]);
            }
            if (str.contains(res + "Line()")) {
                str = str.replace(res + "Line()", strs[idx ++ ]);
            }
            if (str.contains(res + "Short()")) {
                str = str.replace(res + "Short()", strs[idx ++ ]);
            }
            newCode += str + " ";
        }

        return newCode;
    }

    @Override
    public JSONObject receiveCode(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        Integer contestId = Integer.parseInt(data.get("contestId"));
        String topicId = data.get("topicId");
        String code = data.get("code");
        String inputExample = data.get("inputExample");
        String outputExample = data.get("outputExample");
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);
        String[] input_example_lst = inputExample.split(" ");
        String[] output_example_lst = outputExample.split(" ");
        List<String> lst = fixCode(code);
        lst.set(1, removeScanner(lst.get(1), input_example_lst));
        // System.out.println(Arrays.toString(input_example_lst));
        // System.out.println(Arrays.toString(output_example_lst));
        String tmp = "package com.wplcode.wplcode.utils;\n" +
                "\n" +
                lst.get(0) +
                "import java.io.ByteArrayOutputStream;\n" +
                "import java.io.PrintStream;" +
                "\n" +
                "public class Code implements com.wplcode.wplcode.utils.CodeInterface {\n" +
                "\n" +
                "    @Override\n" +
                "    public String getResult() {\n" +
                "            ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);\n" +
                "            PrintStream cacheStream = new PrintStream(baoStream);//临时输出\n" +
                "            PrintStream oldStream = System.out;//缓存系统输出\n" +
                "            System.setOut(cacheStream);\n" +
                lst.get(1) + "\n" +
                "            System.setOut(oldStream);//还原到系统输出\n" +
                "            String result = baoStream.toString();//存放控制台输出的字符串\n" +
                "            return result;" +
                "    }\n" +
                "}";
        String resp_result = "";
        try {
            CodeInterface codeInterface = Reflect.compile(
                    "com.wplcode.wplcode.utils.Code" + uid, addUid(tmp, uid)
            ).create().get();
            String code_result = codeInterface.getResult();
            code_result = code_result.trim();
            outputExample = outputExample.trim();
            System.out.println("code_result = " + code_result);
            System.out.println("outputExample = " + outputExample);
            if (outputExample.equals(code_result)) {
                resp_result = "Accept";
            } else {
                resp_result = "Wrong answer";
            }
        } catch (Exception e) {
            resp_result = "RE or CE";
            e.printStackTrace();
        }
        // System.out.println(codeInterface.getResult());
        // System.out.println(contestId);
        // System.out.println(topicId);
        // System.out.println(code);
        // System.out.println(inputExample);
        // System.out.println(outputExample);
        Submission submission = new Submission(
                null,
                contestId,
                topicId,
                user.getUsername(),
                resp_result,
                new Date()
        );
        submissionMapper.insert(submission);
        return resp;
    }
}
