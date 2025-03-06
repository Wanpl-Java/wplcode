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
        <div style="text-align: right;">
            <a @click="to_my_gym_submissions();" href="javascript:void(0)" style="text-decoration: none; color: #0000CC;">→My submissions</a>
        </div>
    </div>
    <div v-else-if="now_show === 'topic_info'">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div style="margin-top: 15px; font-size: 20px; font-weight: 600;">
                        {{ topics[topic_id - 1].id }}. {{ topics[topic_id - 1].title }}
                    </div>
                    <div style="margin-top: 10px; font-size: 16px; font-weight: 600;">
                        time limit per test: 2 seconds
                    </div>
                    <div style="margin-top: 5px; font-size: 16px; font-weight: 600;">
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
                            <VAceEditor v-else-if="lang_value === 'Go'" v-model:value="go_code_content" @init="editorInit" lang="golang"
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
                            <el-button @click="to_debug();" style="width: 95px; height: 35px; margin-top: 15px; font-size: 14px;" type="warning" round>Debug</el-button>
                            <el-button @click="submit_code();" style="width: 95px; height: 35px; margin-top: 15px; font-size: 14px; color: white;" color="#5CB85C" round>{{ submit_btn_content }}</el-button>
                            <!--<button @click="submit_code" style="border: 1px solid black; margin-top: 15px; font-size: 14px;">Submit</button>-->
                        </div>
                        <el-dialog v-model="debug_DialogVisble" width="500" center style="margin-top: 120px; height: 300px;">
                            <div class="flex-container" style="font-size: 20px; font-weight: 600; justify-content: center;">
                                Code running status:
                                <div v-if="code_running_status === 'Finished'" style="color: #449D44; margin-left: 10px;">
                                    {{ code_running_status }}
                                </div>
                                <div v-else-if="code_running_status === ''" style="margin-left: 10px;">
                                    {{ code_running_status }}
                                </div>
                                <div v-else-if="code_running_status === 'Running'" style="color: #337AB7; margin-left: 10px;">
                                    {{ code_running_status }}<img style="width: 20px; margin-left: 5px;" src="https://cdn.acwing.com/static/web/gif/code_status_loading.gif">
                                </div>
                                <div v-else style="color: #D05451; margin-left: 10px;">
                                    {{ code_running_status }}
                                </div>
                            </div>
                            <hr>
                            <div class="card" style="border: 1px solid balck; height: 150px;">
                                <div style="margin-top: 5px; font-size: 16px; margin-left: 15px;">
                                    Input
                                </div>
                                <div>
                                    <textarea v-model="user_input_example" style="resize: none; margin-top: 5px; margin-left: 15px; width: 430px; background-color: #F5F5F5; border-radius: 5px; height: 30px; font-size: 12px;"></textarea>
                                </div>
                                <div style="margin-top: 5px; font-size: 16px; margin-left: 15px;">
                                    Output
                                </div>
                                <div>
                                    <textarea disabled v-model="user_output_example" style="resize: none; margin-top: 5px; margin-left: 15px; width: 430px; background-color: #F5F5F5; border-radius: 5px; height: 30px; font-size: 12px;"></textarea>
                                </div>
                            </div>
                            <div class="flex-container" style="justify-content: center;">
                                <el-button @click="debug_code(user_input_example);" class="flex-container" type="warning" style="margin-top: 10px;">Debug</el-button>
                                <el-button @click="debug_DialogVisble = false;" class="flex-container" type="danger" style="margin-top: 10px;">Exit</el-button>
                            </div>
                        </el-dialog>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else-if="now_show === 'my_gym_submissions'">
        <div class="flex-container" style="justify-content: right;">
            <div style="font-weight: 600;">
                Return
            </div>
            <svg @click="to_topic_bank();" xmlns="http://www.w3.org/2000/svg" style="width: 30px; margin-left: 5px; cursor: pointer;" class="ionicon" viewBox="0 0 512 512"><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M112 160l-64 64 64 64"/><path d="M64 224h294c58.76 0 106 49.33 106 108v20" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"/>
            </svg>
        </div>
        <table class="table" style="border: 1px solid black; border-collapse: collapse; margin-top: 0px;">
            <thead>
                <tr style="font-size: 14px;">
                    <th scope="col">#</th>
                    <th scope="col">Topic</th>
                    <th scope="col">Lang</th>
                    <th scope="col">Verdict</th>
                    <th scope="col">Time cost(ms)</th>
                    <th scope="col">Memory cost(KB)</th>
                    <th scope="col">When</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="gym_submisson in gym_submissons" :key="gym_submisson.id" style="font-size: 14px;">
                    <td>{{ gym_submisson.id }}</td>
                    <td>{{ gym_submisson.topicId }}.{{ gym_submisson.topicName }}</td>
                    <td>{{ gym_submisson.language }}</td>
                    <td v-if="gym_submisson.result === 'Accept'" style="color: #5CB85C;">{{ gym_submisson.result }}</td>
                    <td v-else-if="gym_submisson.result !== 'Accept'" style="color: #D9534F;">{{ gym_submisson.result }}</td>
                    <td>{{ gym_submisson.timeCost }}</td>
                    <td>{{ gym_submisson.memoryCost }}</td>
                    <td>{{ gym_submisson.submitTime }}</td>
                </tr>
            </tbody>
        </table>
        <nav aria-label="Page navigation example">
            <ul class="pagination pagination-sm" style="float: right;">
                <li class="page-item" @click="click_page(-2)">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number" @click="click_page(page.number)">
                    <a class="page-link" href="#">{{ page.number }}</a>
                </li>
                <li class="page-item" @click="click_page(-1)">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
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
import 'ace-builds/src-noconflict/mode-golang'
import 'ace-builds/src-noconflict/theme-chaos';
import 'ace-builds/src-noconflict/theme-vibrant_ink';
import 'ace-builds/src-noconflict/ext-language_tools';
import { useStore } from 'vuex';
import router from '../router/index';

export default {
    watch: {
        debug_DialogVisble(New) {
            // 当退出的时候
            if (New === false) {
                setTimeout(() => {
                    this.user_output_example = "";
                    this.code_running_status = "";
                }, 50);
            }
        },
        topic_id(New) {
            this.user_input_example = this.topics[New - 1].inputExample;
        },
    },
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

        let user_input_example = ref("");
        let user_output_example = ref("");

        const store = useStore();

        let submit_btn_content = ref("Submit");

        let now_show = ref("topic_bank"); // 默认展示题库页面

        let lang_value = ref('');

        let debug_DialogVisble = ref(false);

        let code_running_status = ref("");

        let java_code_content = ref("import java.util.*;\n\npublic class Main {\n    public static void main(String[] args) {\n\n    }\n}");
        let cpp_code_content = ref("#include <bits/stdc++.h>\n\nusing namespace std;\n\nint main() {\n\n    return 0;\n}");
        let python_code_content = ref("''' show your python code! '''\n");
        let go_code_content = ref("/* show your go code! */\n");

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
            {
                value: "Go",
                label: "Go",
            }
        ];

        let gym_submissons = ref([]);
        let current_page = 1;
        let pages = ref([]);
        let total_gym_submissions = 0;

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_gym_submissions / 15));
            if (page >= 1 && page <= max_pages) {
                refresh_gym_submissions(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_gym_submissions / 15));
            let new_pages = [];
            for (let i = current_page - 2; i <= current_page + 2; i ++ ) {
                if (i >= 1 && i <= max_pages) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        };

        const refresh_gym_submissions = page => {
            let jwt_token = store.state.user.token;
            if (jwt_token !== '') {
                current_page = page;
                $.ajax({
                    url: "http://localhost:3020/getGymSubmissions/",
                    type: "get",
                    data: {
                        page,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        gym_submissons.value = resp.gymSubmissions;
                        total_gym_submissions = resp.count;
                        update_pages();
                    }
                });
            }
        };

        refresh_gym_submissions(current_page);

        const to_my_gym_submissions = () => {
            now_show.value = "my_gym_submissions";
        };

        const to_topic_info = id => {
            topic_id.value = id;
            now_show.value = "topic_info";
            console.log(topic_id.value);
        };

        const to_topic_bank = () => { // 一种个人认为优雅的业务设计
            location.reload();
        }

        const to_debug = () => {
            // 用户需要登录后方可提交代码
            if (store.state.user.token === null || store.state.user.token === '') {
                alert("Please login first!");
                router.push({
                    name: "login_index",
                });
                return;
            }
            if (lang_value.value === "") {
                alert("Please choose your language!");
                return;
            }
            setTimeout(() => {
                debug_DialogVisble.value = true;
            }, 20);
        };

        const debug_code = (user_input_example) => {
            code_running_status.value = "Running";
            if (lang_value.value === "Java") {
                $.ajax({
                    url: "http://localhost:3020/debugCode/",
                    type: "post",
                    data: {
                        "code": java_code_content.value,
                        "language": lang_value.value,
                        "inputExample": user_input_example,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        // console.log(resp);
                        if (resp.error_message === 'success') {
                            user_output_example.value = resp.output;
                            code_running_status.value = "Finished";
                        } else {
                            code_running_status.value = resp.error_message;
                        }
                    }
                });
            } else if (lang_value.value === 'C++') {
                $.ajax({
                    url: "http://localhost:3020/debugCode/",
                    type: "post",
                    data: {
                        "code": cpp_code_content.value,
                        "language": lang_value.value,
                        "inputExample": user_input_example,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        // console.log(resp);
                        if (resp.error_message === 'success') {
                            user_output_example.value = resp.output;
                            code_running_status.value = "Finished";
                        } else {
                            code_running_status.value = resp.error_message;
                        }
                    }
                });
            } else if (lang_value.value === 'Python') {
                $.ajax({
                    url: "http://localhost:3020/debugCode/",
                    type: "post",
                    data: {
                        "code": python_code_content.value,
                        "language": lang_value.value,
                        "inputExample": user_input_example,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        // console.log(resp);
                        if (resp.error_message === 'success') {
                            user_output_example.value = resp.output;
                            code_running_status.value = "Finished";
                        } else {
                            code_running_status.value = resp.error_message;
                        }
                    }
                });
            } else if (lang_value.value === 'Go') {
                $.ajax({
                    url: "http://localhost:3020/openAiDebug/",
                    type: "post",
                    data: {
                        "code": go_code_content.value,
                        "language": lang_value.value,
                        "inputExample": user_input_example,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        // console.log(resp);
                        if (resp.error_message === 'success') {
                            user_output_example.value = resp.output;
                            code_running_status.value = "Finished";
                        } else {
                            code_running_status.value = resp.error_message;
                        }
                    }
                });
            }
        };

        const submit_code = () => {
            // 用户需要登录后方可提交代码
            if (store.state.user.token === null || store.state.user.token === '') {
                alert("Please login first!");
                router.push({
                    name: "login_index",
                });
                return;
            }
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
                            alert("Accept!");
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
                            alert("Accept!");
                        }
                        cpp_code_content.value = "#include <bits/stdc++.h>\n\nusing namespace std;\n\nint main() {\n\n    return 0;\n}";
                        submit_btn_content.value = "Submit";
                    }
                });
            } else if (lang_value.value === 'Python') {
                submit_btn_content.value = "Running...";
                $.ajax({
                    url: "http://localhost:3020/handleCode/",
                    type: "post",
                    data: {
                        "code": python_code_content.value,
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
                            alert("Accept!");
                        }
                        python_code_content.value = "''' show your python code! '''\n";
                        submit_btn_content.value = "Submit";
                    }
                });
            } else if (lang_value.value === 'Go') {
                judge_go();
            }
        };

        // 评测go语言代码专用
        const judge_go = () => {
            submit_btn_content.value = "Running...";
            $.ajax({
                url: "http://localhost:3020/openAi/",
                type: "post",
                data: {
                    "code": go_code_content.value,
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
                        alert("Accept!");
                    }
                    go_code_content.value = "/* show your go code! */\n";
                    submit_btn_content.value = "Submit";
                }
            });
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
            go_code_content,
            judge_go,
            debug_DialogVisble,
            debug_code,
            user_input_example,
            user_output_example,
            code_running_status,
            to_debug,
            to_my_gym_submissions,
            gym_submissons,
            current_page,
            pages,
            click_page,
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