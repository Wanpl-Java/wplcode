<template>
    <div v-if="now_show === 'topic_bank'">
        <div style="font-weight: 500; font-size: 32px; margin-top: 5px;">
            WPLCode在线题库
        </div>
        <div>
            <input v-model="input_topic_name" type="text" style="border-radius: 5px; width: 500px; margin-top: 10px; height: 36px;" placeholder=" Search topic">
            <img @click="search_topic();" src="../assets/icon14.png" style="margin-bottom: 8px; cursor: pointer;">
        </div>
        <hr>
        <table class="table" style="border: 1px solid black; border-collapse: collapse; margin-top: 20px;">
            <thead>
                <tr style="font-size: 14px;">
                    <th scope="col">#</th>
                    <th scope="col">Title</th>
                    <th scope="col">Source</th>
                    <th scope="col">Algorithm</th>
                    <th scope="col">Difficulty</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="topic in topics" :key="topic.id" style="font-size: 14px;">
                    <td>{{ topic.id }}</td>
                    <td>
                        <a href="javascript:void(0)" @click="to_topic_info(topic.id);" style="text-decoration: none; color: #0000CC;">{{ topic.title }}</a>
                    </td>
                    <td>{{ topic.source }}</td>
                    <td>{{ topic.algorithm }}</td>
                    <td v-if="topic.difficulty === 'easy'" style="color: #5CB85C;">{{ topic.difficulty }}</td>
                    <td v-else-if="topic.difficulty === 'middle'" style="color: #F0AD4E;">{{ topic.difficulty }}</td>
                    <td v-else-if="topic.difficulty === 'hard'" style="color: #D9534F;">{{ topic.difficulty }}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div v-else-if="now_show === 'topic_info'">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div style="margin-top: 15px; font-size: 20px;">
                        {{ topics[topic_id - 1].id }}. {{ topics[topic_id - 1].title }}
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
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ topics[topic_id - 1].content }}
                    </div>
                    <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                        Input format
                    </div>
                    <div style="margin-top: 5px; text-align: left; margin-left: 70px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ topics[topic_id - 1].inputFormat }}
                    </div>
                    <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                        Output format
                    </div>
                    <div style="margin-top: 5px; text-align: left; margin-left: 70px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ topics[topic_id - 1].outputFormat }}
                    </div>
                    <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                        Data range
                    </div>
                    <div style="margin-top: 5px; text-align: left; margin-left: 70px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ topics[topic_id - 1].dataRange }}
                    </div>
                    <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                        Input example
                    </div>
                    <div class="card" style="text-align: left; margin-left: 50px; margin-top: 5px; background-color: #EFEFEF;">
                        <div style="text-align: left; margin-left: 10px;">
                            {{ topics[topic_id - 1].inputExample }}
                        </div>
                    </div>
                    <div style="margin-top: 10px; font-weight: 600; text-align: left; margin-left: 50px;">
                        Output example
                    </div>
                    <div class="card" style="text-align: left; margin-left: 50px; margin-top: 5px; background-color: #EFEFEF;">
                        <div style="text-align: left; margin-left: 10px;">
                            {{ topics[topic_id - 1].outputExample }}
                        </div>
                    </div>
                </div>
                <div class="col-12">
                    <div class="flex-container" style="margin-top: 15px; text-align: center; justify-content: center;">
                        Language:
                        <div style="font-size: 12px; margin-left: 10px;">
                            <!--<select v-model="lang_select" class="form-select" aria-label="Java" style="width: 200px; height: 35px; font-size: 14px; border: 1px solid black;">
                                <option v-for="lang in lang_select" :key="lang.value" placeholder="Java">{{ lang.value }}</option>
                            </select>-->
                            <el-select
                                v-model="lang_value"
                                placeholder="Choose language"
                                size="large"
                                style="width: 240px"
                                >
                                <el-option
                                    v-for="item in lang_select"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </div>
                        <svg @click="to_topic_bank();" xmlns="http://www.w3.org/2000/svg" style="width: 30px; margin-left: 5px; cursor: pointer;" class="ionicon" viewBox="0 0 512 512"><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M112 160l-64 64 64 64"/><path d="M64 224h294c58.76 0 106 49.33 106 108v20" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"/>
                        </svg>
                    </div>
                    <div style="margin-top: 10px;">
                        <div class="card" style="width: 750px; border: 1px solid black; background-color: #F8F9FA;">
                            <div style="text-align: left; margin-top: 10px; font-size: 20px; margin-left: 12px;">
                                挑战模式
                            </div>
                            <VAceEditor v-if="lang_value === ''" @init="editorInit" lang=""
                                theme="vibrant_ink" style="width: 750px; height: 500px; margin-top: 10px;" :options="{
                                    enableBasicAutocompletion: true, //启用基本自动完成
                                    enableSnippets: true, // 启用代码段
                                    enableLiveAutocompletion: true, // 启用实时自动完成
                                    fontSize: 15, //设置字号
                                    tabSize: 4, // 标签大小
                                    showPrintMargin: false, //去除编辑器里的竖线
                                    highlightActiveLine: true,
                                    border,
                                }" />
                            <VAceEditor v-else-if="lang_value === 'Java'" v-model:value="java_code_content" @init="editorInit" lang="java"
                                theme="vibrant_ink" style="width: 750px; height: 500px; margin-top: 10px;" :options="{
                                    enableBasicAutocompletion: true, //启用基本自动完成
                                    enableSnippets: true, // 启用代码段
                                    enableLiveAutocompletion: true, // 启用实时自动完成
                                    fontSize: 15, //设置字号
                                    tabSize: 4, // 标签大小
                                    showPrintMargin: false, //去除编辑器里的竖线
                                    highlightActiveLine: true,
                                    border,
                                }" />
                            <VAceEditor v-else-if="lang_value === 'C++'" v-model:value="cpp_code_content" @init="editorInit" lang="c_cpp"
                                theme="vibrant_ink" style="width: 750px; height: 500px; margin-top: 10px;" :options="{
                                    enableBasicAutocompletion: true, //启用基本自动完成
                                    enableSnippets: true, // 启用代码段
                                    enableLiveAutocompletion: true, // 启用实时自动完成
                                    fontSize: 15, //设置字号
                                    tabSize: 4, // 标签大小
                                    showPrintMargin: false, //去除编辑器里的竖线
                                    highlightActiveLine: true,
                                    border,
                                }" />
                            <VAceEditor v-else-if="lang_value === 'Python'" v-model:value="python_code_content" @init="editorInit" lang="python"
                                theme="vibrant_ink" style="width: 750px; height: 500px; margin-top: 10px;" :options="{
                                    enableBasicAutocompletion: true, //启用基本自动完成
                                    enableSnippets: true, // 启用代码段
                                    enableLiveAutocompletion: true, // 启用实时自动完成
                                    fontSize: 15, //设置字号
                                    tabSize: 4, // 标签大小
                                    showPrintMargin: false, //去除编辑器里的竖线
                                    highlightActiveLine: true,
                                    border,
                                }" />
                        </div>
                        <div class="flex-container" style="justify-content: center;">
                            <el-button @click="submit_code();" style="margin-top: 15px; font-size: 14px; color: white;" color="#5CB85C" round>{{ submit_btn_content }}</el-button>
                            <!--<button @click="submit_code" style="border: 1px solid black; margin-top: 15px; font-size: 14px;">Submit</button>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import $ from 'jquery';
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/mode-java';
import 'ace-builds/src-noconflict/mode-python'
import 'ace-builds/src-noconflict/theme-chaos';
import 'ace-builds/src-noconflict/theme-vibrant_ink';
import 'ace-builds/src-noconflict/ext-language_tools';
import { useStore } from 'vuex';

export default {
    components: {
        VAceEditor,
    },
    setup() {
        const script = document.createElement('script');
        script.src = 'https://try2.fit2cloud.cn/api/application/embed?protocol=https&host=try2.fit2cloud.cn&token=33352bb80a1c2db2';
        document.head.appendChild(script);

        let input_topic_name = ref("");
        let topics = ref([]);
        let topic_id = ref(0);

        const store = useStore();

        let submit_btn_content = ref("Submit");

        let now_show = ref("topic_bank"); // 默认展示题库页面

        let lang_value = ref('');

        let java_code_content = ref("import java.util.*;\n\npublic class Main {\n    public static void main(String[] args) {\n\n    }\n}");
        let cpp_code_content = ref("#include <bits/stdc++.h>\n\nusing namespace std;\n\nint main() {\n\n    return 0;\n}");
        let python_code_content = ref("");

        const lang_select = [
            {
                value: 'Java',
                label: 'Java',
            },
            {
                value: 'C++',
                label: 'C++',
            },
            {
                value: 'Python',
                label: 'Python',
            },
        ];

        const to_topic_info = id => {
            topic_id.value = id;
            now_show.value = "topic_info";
            console.log(topic_id.value);
        };

        const to_topic_bank = () => { // 一种个人认为优雅的业务设计
            location.reload();
        }

        const submit_code = () => {
            if (lang_value.value === "") {
                alert("Please choose your language!");
                return;
            }
            if (lang_value.value === 'Java') {
                submit_btn_content.value = "Running...";
                $.ajax({
                    url: "http://localhost:3020/handleCode/",
                    type: "post",
                    data: {
                        "code": java_code_content.value,
                        "language": lang_value.value,
                        "topicId": topic_id.value,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        if (resp.error_message !== 'success') {
                            alert(resp.error_message);
                        } else {
                            alert("Submit successfully!");
                        }
                        java_code_content.value = "import java.util.*;\n\npublic class Main {\n    public static void main(String[] args) {\n\n    }\n}";
                        submit_btn_content.value = "Submit";
                    }
                });
            } else if (lang_value.value === 'C++') {
                submit_btn_content.value = "Running...";
                $.ajax({
                    url: "http://localhost:3020/handleCode/",
                    type: "post",
                    data: {
                        "code": cpp_code_content.value,
                        "language": lang_value.value,
                        "topicId": topic_id.value,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        if (resp.error_message !== 'success') {
                            alert(resp.error_message);
                        } else {
                            alert("Submit successfully!");
                        }
                        cpp_code_content.value = "#include <bits/stdc++.h>\n\nusing namespace std;\n\nint main() {\n\n    return 0;\n}";
                        submit_btn_content.value = "Submit";
                    }
                });
            } else if (lang_value.value === 'Python') {
                alert("Python!");
            }
        };

        const search_topic = () => {
            if (input_topic_name.value === "") {
                refresh_topics();
                return;
            }
            $.ajax({
                url: "http://localhost:3020/searchGymTopic/",
                type: "get",
                data: {
                    "topicName": input_topic_name.value,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        topics.value = [];
                        topics.value.push(resp.topic);
                    } else {
                        alert(resp.error_message);
                    }
                    input_topic_name.value = "";
                }
            });
        };

        const refresh_topics = () => {
            $.ajax({
                url: "http://localhost:3020/getGymTopics/",
                type: "get",
                success(resp) {
                    topics.value = resp.topics;
                }
            });
        };

        refresh_topics();

        ace.config.set(
            "basePath", 
            "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/");

        return {
            input_topic_name,
            topics,
            search_topic,
            now_show,
            topic_id,
            to_topic_info,
            to_topic_bank,
            submit_code,
            lang_select,
            lang_value,
            java_code_content,
            cpp_code_content,
            python_code_content,
            submit_btn_content,
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