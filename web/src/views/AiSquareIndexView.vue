<template>
    <div v-if="now_show === 'wplcode_ai_mainpage'" class="container">
        <div class="row">
            <div class="col-12">
                <div style="margin-top: 15px; font-size: 38px; font-weight: 600;">
                    WPLCode Ai
                </div>
            </div>
            <div class="col-4">
                <div>
                    <img src="../assets//icon16.png" style="width: 50px;">
                </div>
                <div style="margin-top: 5px; font-size: 22px; font-weight: 600;">
                    Cases
                </div>
                <div @click="common_xunfei_ai('Help me program with Java code to solve the A+B problem');" class="card" style="background-color: #F0F0F5; border: none; text-align: left; height: 115px; cursor: pointer; margin-top: 10px;">
                    <div style="margin-left: 20px; margin-right: 20px; margin-top: 10px;">
                        "Help me program with Java code to solve the A+B problem"
                    </div>
                </div>
                <div @click="common_xunfei_ai('How to solve the 01 backpack problem that requires the use of C++ language');" class="card" style="background-color: #F0F0F5; border: none; text-align: left; height: 115px; cursor: pointer; margin-top: 20px;">
                    <div style="margin-left: 20px; margin-right: 20px; margin-top: 10px;">
                        "How to solve the 01 backpack problem that requires the use of C++ language"
                    </div>
                </div>
            </div>
            <div class="col-4">
                <div>
                    <img src="../assets//icon17.png" style="width: 50px; margin-top: 5px;">
                </div>
                <div style="margin-top: 6px; font-size: 22px; font-weight: 600;">
                    Functions
                </div>
                <div class="card" style="background-color: #F0F0F5; border: none; text-align: left; height: 115px; margin-top: 10px;">
                    <div style="margin-left: 20px; margin-right: 20px; margin-top: 10px;">
                        Based on the XunFei Spark 4.0 Ultra Large Model
                    </div>
                </div>
                <div class="card" style="background-color: #F0F0F5; border: none; text-align: left; height: 115px; margin-top: 20px;">
                    <div style="margin-left: 20px; margin-right: 20px; margin-top: 10px;">
                        You can just self define roles
                    </div>
                </div>
            </div>
            <div class="col-4">
                <div>
                    <img src="../assets//icon18.png" style="width: 50px; margin-top: 6px;">
                </div>
                <div style="margin-top: 6px; font-size: 22px; font-weight: 600;">
                    Notices
                </div>
                <div class="card" style="background-color: #F0F0F5; border: none; text-align: left; height: 115px; margin-top: 10px;">
                    <div style="margin-left: 20px; margin-right: 20px; margin-top: 10px;">
                        AI cannot replace human thinking, and its answers can be chosen as simple references
                    </div>
                </div>
                <div class="card" style="background-color: #F0F0F5; border: none; text-align: left; height: 115px; margin-top: 20px;">
                    <div style="margin-left: 20px; margin-right: 20px; margin-top: 10px;">
                        The Spark 4.0 Ultra knowledge inventory is subject to certain limitations
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div @click="start_experience();" @mouseenter="enlarge_size();" @mouseleave="shrink_size();" class="card" style="margin-top: 25px; width: 250px; margin-left: 245px; border: none; cursor: pointer;">
                    <div :style="word_style">
                        Start experiencing<img src="../assets/icon19.png" :style="image_style">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else-if="now_show === 'dialogue_page'">
        <div class="flex-container" style="justify-content: left;">
            <img @click="to_wplcode_ai_mainpage();" src="../assets/icon20.png" style="width: 80px; cursor: pointer;">
            <div style="font-size: 18px; font-weight: 600; font-family: '隶书'; margin-top: 10px;">
                {{ flow_word }} 
            </div>
        </div>
        <div class="card" style="border: 1px solid black; margin-top: 5px; min-height: 360px; overflow: auto; max-height: 800px;">
            <div v-for="user_words in user_words_lst" :key="user_words.id">
                <div class="flex-container" style="justify-content: right; text-align: right; margin-right: 10px;">
                    <div style="margin-top: 3px; margin-right: 3px;">
                        :
                    </div>
                    <img :src="$store.state.user.photo" style="width: 30px; margin-top: 10px;">
                </div>
                <div style="text-align: right; margin-right: 45px;">
                    {{ user_words_lst[user_words.get('id')].get('output') }}
                </div>
                <div class="flex-container" style="justify-content: left; text-align: left; margin-left: 10px;">
                    <img src="../assets/icon22.png" style="width: 30px; margin-bottom: 10px;">
                    <div style="margin-left: 3px; margin-top: 3px; margin-bottom: 13px;">
                        :
                    </div>
                </div>
                <div v-if="ai_words_lst[user_words.get('id')].get('success') === true" style="text-align: left; margin-left: 45px;">
                    {{ ai_words_lst[user_words.get('id')].get('output') }}
                </div>
                <div v-else-if="ai_words_lst[user_words.get('id')].get('success') === false" style="text-align: left; margin-left: 45px;">
                    {{ dialogue_words }}
                </div>
            </div>
        </div>
        <img @click="stop_answer();" src="../assets/icon23.png" style="width: 40px; margin-bottom: 45px; cursor: pointer;">
        <textarea v-model="user_input" placeholder="Click here, feel free to ask" style="background-color: #F0F0F5; margin-top: 15px; width: 70%; border: none; border-radius: 5px; margin-left: 5px;">
        </textarea><img @click="common_xunfei_ai(user_input);" src="../assets/icon21.png" style="width: 40px; margin-bottom: 45px; margin-left: 5px; cursor: pointer;">
    </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import router from '../router/index';
import { useStore } from 'vuex'
import $ from 'jquery'

export default {
    setup() {
        const store = useStore();

        let user_input = ref("");

        let now_show = ref("wplcode_ai_mainpage");

        let dialogue_words = ref("");

        // 对话展示模块上方的流动/动态文字
        let flow_word = ref("");

        // 对话展示模块分别记录用户和Ai说的话
        let user_words_lst = ref([]);
        let ai_words_lst = ref([]);

        // 对话展示模块当前对话的条数
        let dialogue_cnt = ref(0);

        let jwt_token = store.state.user.token; 

        let word_style = ref("font-size: 20px; font-weight: 600;");
        let image_style = ref("width: 30px; margin-bottom: 10px;");

        let need_stop_answer = ref(false);

        const enlarge_size = () => {
            word_style.value = "font-size: 22px; font-weight: 600;";
            image_style.value = "width: 31px; margin-bottom: 10px;";
        };

        const shrink_size = () => {
            word_style.value = "font-size: 20px; font-weight: 600;";
            image_style.value = "width: 30px; margin-bottom: 10px;";
        };

        const to_wplcode_ai_mainpage = () => {
            location.reload();
        };

        const stop_answer = () => {
            need_stop_answer.value = true;
        };

        // 常规调用讯飞ai大模型
        const common_xunfei_ai = userInput => {
            jwt_token = store.state.user.token; 
            if (jwt_token === '') {
                alert("Please login first!");
                router.push({
                    name: "login_index",
                });
                return;
            }
            setTimeout(() => {
                let mp = new Map();
                mp.set("id", dialogue_cnt.value);
                mp.set("output", userInput);
                user_words_lst.value.push(mp);
                let ai_mp = new Map();
                ai_mp.set("id", dialogue_cnt.value);
                ai_mp.set("output", "");
                ai_mp.set("success", false); // 还未完全输出结果
                ai_words_lst.value.push(ai_mp);
                dialogue_cnt.value ++;
                // console.log(user_words_lst.value);
                if (now_show.value === "dialogue_page") {
                    user_input.value = "";
                    $.ajax({
                        url: "http://localhost:3020/xunFeiAi/",
                        type: "get",
                        data: {
                            userInput,
                        },
                        headers: {
                            Authorization: "Bearer " + jwt_token,
                        },
                        success(resp) {
                            let tmp_str = resp.error_message;
                            let mpp = new Map();
                            mpp.set("id", dialogue_cnt.value - 1);
                            let len = tmp_str.length, idx = 0;
                            let tmp_interval = setInterval(() => {
                                if (need_stop_answer.value) {
                                    need_stop_answer.value = false;
                                    clearInterval(tmp_interval);
                                }
                                if (idx < len) {
                                    dialogue_words.value += tmp_str[idx ++ ];
                                    mpp.set("output", dialogue_words.value);
                                } else {
                                    mpp.set("success", true);
                                    ai_words_lst.value[dialogue_cnt.value - 1] = mpp;
                                    setTimeout(() => {
                                        dialogue_words.value = "";
                                    }, 100);
                                    clearInterval(tmp_interval);
                                }
                            }, 50);
                        }
                    })
                } else {
                    now_show.value = "dialogue_page";
                    refresh_flow_word();
                    $.ajax({
                        url: "http://localhost:3020/xunFeiAi/",
                        type: "get",
                        data: {
                            userInput,
                        },
                        headers: {
                            Authorization: "Bearer " + jwt_token,
                        },
                        success(resp) {
                            let tmp_str = resp.error_message;
                            let mpp = new Map();
                            mpp.set("id", dialogue_cnt.value - 1);
                            let len = tmp_str.length, idx = 0;
                            let tmp_interval = setInterval(() => {
                                if (need_stop_answer.value) {
                                    alert("jvm!");
                                    need_stop_answer.value = false;
                                    clearInterval(tmp_interval);
                                }
                                if (idx < len) {
                                    dialogue_words.value += tmp_str[idx ++ ];
                                    mpp.set("output", dialogue_words.value);
                                } else {
                                    mpp.set("success", true);
                                    ai_words_lst.value[dialogue_cnt.value - 1] = mpp;
                                    setTimeout(() => {
                                        dialogue_words.value = "";
                                    }, 100);
                                    clearInterval(tmp_interval);
                                }
                            }, 50);
                        }
                    })
                }
            }, 20);
        };

        const start_experience = () => {
            jwt_token = store.state.user.token; 
            if (jwt_token === '') {
                alert("Please login first!");
                router.push({
                    name: "login_index",
                });
                return;
            }
            setTimeout(() => {
                now_show.value = "dialogue_page";
                refresh_flow_word();
            }, 20);
        };

        const refresh_flow_word = () => {
            let tmp_flow_word = "WPLCode Ai,your most capable intelligent assistant!";
            let len = tmp_flow_word.length, idx = 0;
            let handle_flow_word = setInterval(() => {
                if (idx < len) {
                    flow_word.value += tmp_flow_word[idx ++ ];
                    console.log(flow_word.value);
                } else {
                    clearInterval(handle_flow_word);
                }
            }, 100);
            setInterval(() => {
                flow_word.value = "";
                setTimeout(() => {
                    let tmp_flow_word = "WPLCode Ai,your most capable intelligent assistant!";
                    let len = tmp_flow_word.length, idx = 0;
                    let handle_flow_word = setInterval(() => {
                        if (idx < len) {
                            flow_word.value += tmp_flow_word[idx ++ ];
                            console.log(flow_word.value);
                        } else {
                            clearInterval(handle_flow_word);
                        }
                    }, 100);
                }, 10);
            }, 10000);
        };

        onMounted(() => {

        });

        return {
            enlarge_size,
            word_style,
            shrink_size,
            image_style,
            start_experience,
            jwt_token,
            now_show,
            flow_word,
            to_wplcode_ai_mainpage,
            common_xunfei_ai,
            refresh_flow_word,
            dialogue_words,
            user_input,
            user_words_lst,
            ai_words_lst,
            dialogue_cnt,
            stop_answer,
            need_stop_answer,
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