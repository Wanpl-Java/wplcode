<template>
    <div class="container">
        <div class="row">
            <div class="col-6">
                <div style="margin-top: 15px; font-size: 20px;">
                    {{ topic.topicId }}. {{ topic.title }}
                </div>
                <div style="margin-top: 10px; font-size: 16px;">
                    time limit per test: 2 seconds
                </div>
                <div style="margin-top: 5px; font-size: 16px;">
                    memory limit per test: 256 megabytes
                </div>
                <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                    Problem discrption
                </div>
                <div style="margin-top: 5px; text-align: left; margin-left: 70px;">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ topic.content }}
                </div>
                <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                    Input format
                </div>
                <div style="margin-top: 5px; text-align: left; margin-left: 70px;">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ topic.inputFormat }}
                </div>
                <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                    Output format
                </div>
                <div style="margin-top: 5px; text-align: left; margin-left: 70px;">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ topic.outputFormat }}
                </div>
                <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                    Data range
                </div>
                <div style="margin-top: 5px; text-align: left; margin-left: 70px;">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ topic.dataRange }}
                </div>
                <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                    Input example
                </div>
                <div class="card" style="text-align: left; margin-left: 50px; margin-top: 5px; background-color: #EFEFEF;">
                    <div style="text-align: left; margin-left: 10px;">
                        {{ topic.inputExample }}
                    </div>
                </div>
                <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                    Output example
                </div>
                <div class="card" style="text-align: left; margin-left: 50px; margin-top: 5px; background-color: #EFEFEF;">
                    <div style="text-align: left; margin-left: 10px;">
                        {{ topic.outputExample }}
                    </div>
                </div>
            </div>
            <div class="col-6">
                <div class="flex-container" style="margin-top: 15px; font-size: 20px; text-align: center; justify-content: center;">
                    Language:
                    <div style="font-size: 12px; margin-left: 20px;">
                        <select class="form-select" aria-label="Java" style="width: 200px; height: 35px; font-size: 14px; border: 1px solid black;">
                            <option selected>Java</option>
                        </select>
                    </div>
                    <a href="javascript:void(0)" @click="to_contest" style="text-decoration: none; color: #0000CC; font-size: 16px; margin-left: 10px;">Return to contest</a>
                </div>
                <div style="margin-top: 10px;">
                    <VAceEditor v-model:value="code_content" @init="editorInit" lang="c_cpp"
                        theme="textmate" style="height: 500px; border: 1px solid black;" :options="{
                            enableBasicAutocompletion: true, //启用基本自动完成
                            enableSnippets: true, // 启用代码段
                            enableLiveAutocompletion: true, // 启用实时自动完成
                            fontSize: 12, //设置字号
                            tabSize: 4, // 标签大小
                            showPrintMargin: false, //去除编辑器里的竖线
                            highlightActiveLine: true,
                            border
                        }" />
                    <div class="flex-container" style="justify-content: center;">
                        <button @click="submit_code" style="border: 1px solid black; margin-top: 15px; font-size: 14px;">Submit</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { onMounted, ref } from 'vue';
import $ from 'jquery'
import { useStore } from 'vuex'
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/theme-chrome';
import 'ace-builds/src-noconflict/ext-language_tools';
import router from '../router/index'; 

export default {
    components: {
        VAceEditor,
    },
    setup() {
        const script = document.createElement('script');
        script.src = 'https://try2.fit2cloud.cn/api/application/embed?protocol=https&host=try2.fit2cloud.cn&token=33352bb80a1c2db2';
        document.head.appendChild(script);

        let contestId = "";
        let topicId = "";

        let code_content = ref("");
        code_content.value = "import java.util.*;\n\n" + "public class Main {\n" + "    public static void main(String[] args) {\n\n" + "    }\n" + "}";

        let topic = ref(new Object());

        const store = useStore();

        onMounted(() => {
            topicId = window.location.pathname[window.location.pathname.length - 2];
            for (let i = 0; i < window.location.pathname.length; i ++ ) {
                if (window.location.pathname[i] >= '0' && window.location.pathname[i] <= '9') {
                    contestId += window.location.pathname[i];
                }
            }
            contestId = parseInt(contestId);
            refresh_topic_info();
        });

        const submit_code = () => {
            $.ajax({
                url: "http://localhost:3020/receiveCode/",
                type: "post",
                data: {
                    "contestId": contestId,
                    "topicId": topicId,
                    "code": code_content.value,
                    "inputExample": topic.value.inputExample,
                    "outputExample": topic.value.outputExample,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    alert("Submit successfully!");
                    code_content.value = "";
                    console.log(resp);
                }
            })
        };

        const to_contest = () => {
            router.push({
                name: 'contest_index',
                params: {
                    contestId,
                }
            });
        };

        const refresh_topic_info = () => {
            $.ajax({
                url: "http://localhost:3020/getTopicInfo/",
                type: "get",
                data: {
                    "contestId": contestId,
                    "topicId": topicId,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    topic.value = resp.topic;
                }
            });
        };
        ace.config.set(
            "basePath", 
            "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")
        return {
            contestId,
            topicId,
            topic,
            code_content,
            to_contest,
            submit_code,
        }
    }
}
</script>

<style scoped>
.flex-container {
  display: flex;
  align-items: center; 
}
</style>