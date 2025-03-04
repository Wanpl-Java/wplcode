<template>
    <div class="card" style="width: 100%; margin-top: 20px; border: 1px solid black;">
        <div style="text-align: left; margin-left: 15px; margin-top: 5px;">
            Current or upcoming contests
        </div>
        <table class="table table-striped table-bordered" style="margin-top: 5px;">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Writers</th>
                    <th scope="col">Start</th>
                    <th scope="col">Length</th>
                    <th scope="col">Registration</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="cou_contest in cou_contests" :key="cou_contest.id">
                    <th scope="row">
                        <div style="font-size: 14px; font-weight: 500;">
                            {{ cou_contest.name }}
                        </div>
                    </th>
                    <td>
                        <div style="font-size: 14px; font-weight: 500;">
                            {{ cou_contest.writer }}
                        </div>
                    </td>
                    <td>
                        <div style="font-size: 14px; font-weight: 500;">
                            {{ cou_contest.startTime }}
                        </div>
                    </td>
                    <td>
                        <div v-if="cou_lens[cou_len_map.get(cou_contest.id)].hours < 10 && cou_lens[cou_len_map.get(cou_contest.id)].minutes < 10" style="font-size: 14px; font-weight: 500;">
                            0{{ cou_lens[cou_len_map.get(cou_contest.id)].hours }}:0{{ cou_lens[cou_len_map.get(cou_contest.id)].minutes }}
                        </div>
                        <div v-else-if="cou_lens[cou_len_map.get(cou_contest.id)].hours < 10 && cou_lens[cou_len_map.get(cou_contest.id)].minutes >= 10" style="font-size: 14px; font-weight: 500;">
                            0{{ cou_lens[cou_len_map.get(cou_contest.id)].hours }}:{{ cou_lens[cou_len_map.get(cou_contest.id)].minutes }}
                        </div>
                        <div v-else-if="cou_lens[cou_len_map.get(cou_contest.id)].hours >= 10 && cou_lens[cou_len_map.get(cou_contest.id)].minutes < 10" style="font-size: 14px; font-weight: 500;">
                            {{ cou_lens[cou_len_map.get(cou_contest.id)].hours }}:0{{ cou_lens[cou_len_map.get(cou_contest.id)].minutes }}
                        </div>
                        <div v-else-if="cou_lens[cou_len_map.get(cou_contest.id)].hours >= 10 && cou_lens[cou_len_map.get(cou_contest.id)].minutes >= 10" style="font-size: 14px; font-weight: 500;">
                            {{ cou_lens[cou_len_map.get(cou_contest.id)].hours }}:{{ cou_lens[cou_len_map.get(cou_contest.id)].minutes }}
                        </div>
                    </td>
                    <td>
                        <a v-if="cou_registration_to_start_lens[cou_len_map.get(cou_contest.id)] === 'ok'" href="javascript:void(0)" @click="to_contest_page(cou_contest.id);" style="font-size: 14px; font-weight: 600; color: white; background-color: #CC0000; background-clip: content-box;">
                            Register »
                        </a>
                        <div v-else-if="cou_registration_to_start_lens[cou_len_map.get(cou_contest.id)] !== 'ok'" style="font-size: 12px;">
                            Before registration {{ cou_registration_to_start_lens[cou_len_map.get(cou_contest.id)] }}
                        </div>
                        <!--<div v-else-if="value === null">
                            <el-countdown format="DD [days] HH:mm:ss" :value="value" value-style="font-size: 12px;">
                                <template #title>
                                <div style="display: inline-flex; align-items: center;">
                                    <el-icon style="margin-right: 4px" :size="12">
                                    <Calendar />
                                    </el-icon>
                                    Before registration
                                </div>
                                </template>
                            </el-countdown>
                        </div>-->
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="card" style="width: 100%; margin-top: 20px; border: 1px solid black;">
        <div style="text-align: left; margin-left: 15px; margin-top: 5px;">
            Past contests
        </div>
        <table class="table table-striped table-bordered" style="margin-top: 5px;">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Writers</th>
                    <th scope="col">Start</th>
                    <th scope="col">Length</th>
                    <th scope="col">Result</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="past_contest in past_contests" :key="past_contest.id">
                    <th scope="row">
                        <div style="font-size: 14px; font-weight: 500;">
                            {{ past_contest.name }}
                        </div>
                    </th>
                    <td>
                        <div style="font-size: 14px; font-weight: 500;">
                            {{ past_contest.writer }}
                        </div>
                    </td>
                    <td>
                        <div style="font-size: 14px; font-weight: 500;">
                            {{ past_contest.startTime }}
                        </div>
                    </td>
                    <td>
                        <div v-if="past_lens[past_len_map.get(past_contest.id)].hours < 10 && past_lens[past_len_map.get(past_contest.id)].minutes < 10" style="font-size: 14px; font-weight: 500;">
                            0{{ past_lens[past_len_map.get(past_contest.id)].hours }}:0{{ past_lens[past_len_map.get(past_contest.id)].minutes }}
                        </div>
                        <div v-else-if="past_lens[past_len_map.get(past_contest.id)].hours < 10 && past_lens[past_len_map.get(past_contest.id)].minutes >= 10" style="font-size: 14px; font-weight: 500;">
                            0{{ past_lens[past_len_map.get(past_contest.id)].hours }}:{{ past_lens[past_len_map.get(past_contest.id)].minutes }}
                        </div>
                        <div v-else-if="past_lens[past_len_map.get(past_contest.id)].hours >= 10 && past_lens[past_len_map.get(past_contest.id)].minutes < 10" style="font-size: 14px; font-weight: 500;">
                            {{ past_lens[past_len_map.get(past_contest.id)].hours }}:0{{ past_lens[past_len_map.get(past_contest.id)].minutes }}
                        </div>
                        <div v-else-if="past_lens[past_len_map.get(past_contest.id)].hours >= 10 && past_lens[past_len_map.get(past_contest.id)].minutes >= 10" style="font-size: 14px; font-weight: 500;">
                            {{ past_lens[past_len_map.get(past_contest.id)].hours }}:{{ past_lens[past_len_map.get(past_contest.id)].minutes }}
                        </div>
                    </td>
                    <td>
                        <a href="javascript:void(0)" @click="to_final_standings(past_contest.id);" style="font-size: 14px; font-weight: 500; color: #0000CC;">
                            Final standings
                        </a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <a href="javascript:void(0)" @click="createContest_DialogVisble = true;" style="color: #0000CC; text-align: right; float: right; margin-top: 3px;">Create a contest?</a>
    <el-dialog v-model="createContest_DialogVisble" width="500" center>
        <div style="font-weight: 600; text-align: center; font-size: 24px;">
            Create a contest
        </div>
        <div style="margin-left: 20px; margin-top: 20px;">
            Contest name<input v-model="contest_name" type="text" style="margin-left: 10px; width: 300px;">
        </div>
        <div class="flex-container" style="margin-left: 20px; margin-top: 30px;">
            Contest time
            <div class="block">
                <el-date-picker
                    v-model="contest_time"
                    type="datetimerange"
                    range-separator="To"
                    start-placeholder="Start time"
                    end-placeholder="End time"
                    style="width: 300px; margin-right: 28px;"
                />
            </div>
        </div>
        <div class="flex-container" style="margin-left: 20px; margin-top: 0px;">
            Registration &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;time
            <div class="demo-datetime-picker">
                <div class="block">
                <el-date-picker
                    v-model="registration_time"
                    type="datetime"
                    placeholder="Select date and time"
                    style="width: 300px; margin-right: 45px;"
                />
                </div>
            </div>
        </div>
        <div class="flex-container" style="margin-left: 40px; margin-top: 10px;">
            Content
            <el-button @click="topic_DialogVisble = true;" type="primary" style="margin-left: 30px;" :icon="Edit" circle />
            <el-dialog v-model="topic_DialogVisble" width="520" center>
                <div style="font-weight: 600; text-align: center; font-size: 24px;">
                    Topic content
                </div>
                <div v-if="topic_id === 1" class="card" style="width: 430px; margin-left: 30px; margin-top: 10px;">
                    <div style="margin-top: 10px; text-align: center;">
                        Topic 1
                    </div>
                    <div class="flex-container" style="margin-top: 10px;">
                        <div style="margin-left: 47px;">Title</div><input v-model="title[0]" type="text" style="width: 280px; margin-left: 50px;">
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 35px;">Content</div>
                        <textarea v-model="content[0]" type="text" style="width: 280px; margin-left: 38px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 20px;">Input format</div>
                        <textarea v-model="input_format[0]" type="text" style="width: 280px; margin-left: 22px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 13px;">Output format</div>
                        <textarea v-model="output_format[0]" type="text" style="width: 280px; margin-left: 16px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 25px;">Data range</div>
                        <textarea v-model="data_range[0]" type="text" style="width: 280px; margin-left: 28px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 15px;">Input example</div>
                        <textarea v-model="input_example[0]" type="text" style="width: 280px; margin-left: 17px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 10px;">Output example</div>
                        <textarea v-model="output_example[0]" type="text" style="width: 280px; margin-left: 10px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 20px; justify-content: center;">
                        <el-button @click="add_topic(2);" color="#626aef">Add topic</el-button>
                        <el-button @click="conserve_topics(0);" type="danger">Conserve</el-button>
                    </div>
                    <div style="visibility: hidden; font-size: 10px;">
                        &nbsp;
                    </div>
                </div>
                <div v-else-if="topic_id === 2" class="card" style="width: 430px; margin-left: 30px; margin-top: 10px;">
                    <div style="margin-top: 10px; text-align: center;">
                        Topic 2
                    </div>
                    <div class="flex-container" style="margin-top: 10px;">
                        <div style="margin-left: 47px;">Title</div><input v-model="title[1]" type="text" style="width: 280px; margin-left: 50px;">
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 35px;">Content</div>
                        <textarea v-model="content[1]" type="text" style="width: 280px; margin-left: 38px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 20px;">Input format</div>
                        <textarea v-model="input_format[1]" type="text" style="width: 280px; margin-left: 22px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 13px;">Output format</div>
                        <textarea v-model="output_format[1]" type="text" style="width: 280px; margin-left: 16px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 25px;">Data range</div>
                        <textarea v-model="data_range[1]" type="text" style="width: 280px; margin-left: 28px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 15px;">Input example</div>
                        <textarea v-model="input_example[1]" type="text" style="width: 280px; margin-left: 17px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 10px;">Output example</div>
                        <textarea v-model="output_example[1]" type="text" style="width: 280px; margin-left: 10px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 20px; justify-content: center;">
                        <el-button @click="topic_id = 1;" type="warning">Return last topic</el-button>
                        <el-button @click="add_topic(3);" color="#626aef">Add topic</el-button>
                        <el-button @click="conserve_topics(1);" type="danger">Conserve</el-button>
                    </div>
                    <div style="visibility: hidden; font-size: 10px;">
                        &nbsp;
                    </div>
                </div>
                <div v-else-if="topic_id === 3" class="card" style="width: 430px; margin-left: 30px; margin-top: 10px;">
                    <div style="margin-top: 10px; text-align: center;">
                        Topic 3
                    </div>
                    <div class="flex-container" style="margin-top: 10px;">
                        <div style="margin-left: 47px;">Title</div><input v-model="title[2]" type="text" style="width: 280px; margin-left: 50px;">
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 35px;">Content</div>
                        <textarea v-model="content[2]" type="text" style="width: 280px; margin-left: 38px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 20px;">Input format</div>
                        <textarea v-model="input_format[2]" type="text" style="width: 280px; margin-left: 22px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 13px;">Output format</div>
                        <textarea v-model="output_format[2]" type="text" style="width: 280px; margin-left: 16px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 25px;">Data range</div>
                        <textarea v-model="data_range[2]" type="text" style="width: 280px; margin-left: 28px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 15px;">Input example</div>
                        <textarea v-model="input_example[2]" type="text" style="width: 280px; margin-left: 17px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 10px;">Output example</div>
                        <textarea v-model="output_example[2]" type="text" style="width: 280px; margin-left: 10px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 20px; justify-content: center;">
                        <el-button @click="topic_id = 2;" type="warning">Return last topic</el-button>
                        <el-button @click="add_topic(4);" color="#626aef">Add topic</el-button>
                        <el-button @click="conserve_topics(2);" type="danger">Conserve</el-button>
                    </div>
                    <div style="visibility: hidden; font-size: 10px;">
                        &nbsp;
                    </div>
                </div>
                <div v-else-if="topic_id === 4" class="card" style="width: 430px; margin-left: 30px; margin-top: 10px;">
                    <div style="margin-top: 10px; text-align: center;">
                        Topic 4
                    </div>
                    <div class="flex-container" style="margin-top: 10px;">
                        <div style="margin-left: 47px;">Title</div><input v-model="title[3]" type="text" style="width: 280px; margin-left: 50px;">
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 35px;">Content</div>
                        <textarea v-model="content[3]" type="text" style="width: 280px; margin-left: 38px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 20px;">Input format</div>
                        <textarea v-model="input_format[3]" type="text" style="width: 280px; margin-left: 22px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 13px;">Output format</div>
                        <textarea v-model="output_format[3]" type="text" style="width: 280px; margin-left: 16px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 25px;">Data range</div>
                        <textarea v-model="data_range[3]" type="text" style="width: 280px; margin-left: 28px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 15px;">Input example</div>
                        <textarea v-model="input_example[3]" type="text" style="width: 280px; margin-left: 17px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 10px;">Output example</div>
                        <textarea v-model="output_example[3]" type="text" style="width: 280px; margin-left: 10px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 20px; justify-content: center;">
                        <el-button @click="topic_id = 3;" type="warning">Return last topic</el-button>
                        <el-button @click="add_topic(5);" color="#626aef">Add topic</el-button>
                        <el-button @click="conserve_topics(3);" type="danger">Conserve</el-button>
                    </div>
                    <div style="visibility: hidden; font-size: 10px;">
                        &nbsp;
                    </div>
                </div>
                <div v-else-if="topic_id === 5" class="card" style="width: 430px; margin-left: 30px; margin-top: 10px;">
                    <div style="margin-top: 10px; text-align: center;">
                        Topic 5
                    </div>
                    <div class="flex-container" style="margin-top: 10px;">
                        <div style="margin-left: 47px;">Title</div><input v-model="title[4]" type="text" style="width: 280px; margin-left: 50px;">
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 35px;">Content</div>
                        <textarea v-model="content[4]" type="text" style="width: 280px; margin-left: 38px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 20px;">Input format</div>
                        <textarea v-model="input_format[4]" type="text" style="width: 280px; margin-left: 22px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 13px;">Output format</div>
                        <textarea v-model="output_format[4]" type="text" style="width: 280px; margin-left: 16px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 25px;">Data range</div>
                        <textarea v-model="data_range[4]" type="text" style="width: 280px; margin-left: 28px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 15px;">Input example</div>
                        <textarea v-model="input_example[4]" type="text" style="width: 280px; margin-left: 17px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 15px;">
                        <div style="margin-left: 10px;">Output example</div>
                        <textarea v-model="output_example[4]" type="text" style="width: 280px; margin-left: 10px;"></textarea>
                    </div>
                    <div class="flex-container" style="margin-top: 20px; justify-content: center;">
                        <el-button @click="topic_id = 4;" type="warning">Return last topic</el-button>
                        <el-button @click="conserve_topics(4);" type="danger">Conserve</el-button>
                    </div>
                    <div style="visibility: hidden; font-size: 10px;">
                        &nbsp;
                    </div>
                </div>
            </el-dialog>
        </div>
        <div class="flex-container" style="margin-top: 20px; justify-content: center;">
            <el-button @click="create_contest" color="#626aef" style="ak">Create</el-button>
            <el-button @click="cancel_create_contest" type="danger">Cancel</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { ref } from 'vue'
import { Edit } from '@element-plus/icons-vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import dayjs from 'dayjs';
import router from '../router/index';
// import { Calendar } from '@element-plus/icons-vue'

export default {
    components: {
        // Calendar,
    },
    setup() {
        let topic_id = ref(1);
        let createContest_DialogVisble = ref(false);
        let topic_DialogVisble = ref(false);
        let contest_name = ref("");
        const store = useStore();
        // 传给后端的题目最终内容，后期需要做字符串拼接
        let final_content = ["", "", "", "", ""];
        // 题目标题（如A+B）
        let title = ref(["", "", "", "", ""]);
        // 题目内容
        let content = ref(["", "", "", "", ""]);
        // 输入格式
        let input_format = ref(["", "", "", "", ""]);
        // 输出格式
        let output_format = ref(["", "", "", "", ""]);
        // 数据范围
        let data_range = ref(["", "", "", "", ""]);
        // 输入样例
        let input_example = ref(["", "", "", "", ""]);
        // 输出样例
        let output_example = ref(["", "", "", "", ""]);

        const contest_time = ref("");
        const registration_time = ref("");

        let cou_contests = ref([]);
        let past_contests = ref([]);
        let cou_lens = ref([]);
        let cou_len_map = ref(new Map());
        let past_lens = ref([]);
        let past_len_map = ref(new Map());
        let cou_registration_to_start_lens = ref([]);

        const value = ref(dayjs().add(1, 'month').startOf('month'));

        const to_contest_page = id => {
            router.push({
                name: 'contest_index',
                params: {
                    "contestId": id,
                },
            })
        };

        const to_final_standings = id => {
            router.push({
                name: 'standings_index',
                params: {
                    "contestId": id,
                },
            })
        };

        const update_contest_result = () => {
            $.ajax({
                url: "http://localhost:3020/updateContestResult/",
                type: "post",
                success() {
                    
                }
            })
        };

        update_contest_result();

        const get_format_time = time_str => {
            let year = "", month = "", day = "", hour_minute_second = "";
            let str = "";
            let tmp = "" + time_str.toString();
            let arr = tmp.split(" ");
            for (let i = 1; i < 5; i ++ ) {
                if (i === 1) {
                    if (arr[i] === "Jan") {
                        month = "01";
                    } else if (arr[i] === "Feb") {
                        month = "02";
                    } else if (arr[i] === "Mar") {
                        month = "03";
                    } else if (arr[i] === "Apr") {
                        month = "04";
                    } else if (arr[i] === "May") {
                        month = "05";
                    } else if (arr[i] === "Jun") {
                        month = "06";
                    } else if (arr[i] === "Jul") {
                        month = "07";
                    } else if (arr[i] === "Aug") {
                        month = "08";
                    } else if (arr[i] === "Sep") {
                        month = "09";
                    } else if (arr[i] === "Oct") {
                        month = "10";
                    } else if (arr[i] === "Nov") {
                        month = "11";
                    } else if (arr[i] === "Dec") {
                        month = "12";
                    }
                } else if (i === 2) {
                    day = arr[i];
                } else if (i === 3) {
                    year = arr[i];
                } else {
                    hour_minute_second = arr[i];
                }
            }
            str = year + "-" + month + "-" + day + " " + hour_minute_second;
            return str;
        };

        const add_topic = id => {
            let last_id = id - 2;
            let tmp = "";
            tmp = title.value[last_id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current title can't be empty!");
                return;
            }
            tmp = "";
            tmp = content.value[last_id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current content can't be empty!");
                return;
            }
            tmp = "";
            tmp = input_format.value[last_id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current input format can't be empty!");
                return;
            }
            tmp = "";
            tmp = output_format.value[last_id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current output format can't be empty!");
                return;
            }
            tmp = "";
            tmp = data_range.value[last_id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current data range can't be empty!");
                return;
            }
            tmp = "";
            tmp = input_example.value[last_id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current input example can't be empty!");
                return;
            }
            tmp = "";
            tmp = output_example.value[last_id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current output example can't be empty!");
                return;
            }
            tmp = "";
            topic_id.value = id;
        };

        const conserve_topics = id => {
            let tmp = "";
            tmp = title.value[id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current title can't be empty!");
                return;
            }
            tmp = "";
            tmp = content.value[id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current content can't be empty!");
                return;
            }
            tmp = "";
            tmp = input_format.value[id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current input format can't be empty!");
                return;
            }
            tmp = "";
            tmp = output_format.value[id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current output format can't be empty!");
                return;
            }
            tmp = "";
            tmp = data_range.value[id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current data range can't be empty!");
                return;
            }
            tmp = "";
            tmp = input_example.value[id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current input example can't be empty!");
                return;
            }
            tmp = "";
            tmp = output_example.value[id];
            tmp = tmp.trim();
            if (tmp === "") {
                alert("The current output example can't be empty!");
                return;
            }
            tmp = "";
            for (let i = 0; i <= id; i ++ ) {
                final_content[i] = "@" + title.value[i] + "@" + content.value[i] + "@" + input_format.value[i] + "@"
                                       + output_format.value[i] + "@" + data_range.value[i] + "@" + input_example.value[i] + "@" + output_example.value[i] + "@";
            }
            topic_DialogVisble.value = false;
            // console.log(final_content);
        };

        const check_time = (start_time, end_time) => {
            let start_date = new Date(start_time), end_date = new Date(end_time);
            let dif = end_date.getTime() - start_date.getTime();
            dif /= 1000 * 60 * 60;
            return dif < 24;
        };

        const create_contest = () => {
            if (store.state.user.token === null || store.state.user.token === '') {
                alert("Please login first!");
                router.push({
                    name: 'login_index',
                });
                return;
            }
            let now_time = new Date();
            let start_time = contest_time.value[0];
            let end_time = contest_time.value[1];
            contest_name.value = contest_name.value.trim();
            if (contest_name.value === "") {
                alert("The contest name cannot be empty!");
                return;
            }
            if (start_time < now_time || registration_time.value < now_time) {
                alert("The start time and the registration time can't be ahead of the now time!");
                return;
            }
            if (registration_time.value >= start_time) {
                alert("The registration time must be ahead of the start time!");
                return;
            }
            let final_start_time = get_format_time(start_time);
            let final_end_time = get_format_time(end_time);
            let final_registration_time = get_format_time(registration_time.value);
            if (!check_time(final_start_time, final_end_time)) {
                alert("The duration of the contest cannot exceed or equal 24 hours!");
                return;
            }
            let contentString = "#";
            for (let i = 0; i < 5; i ++ ) {
                if (final_content[i] !== "") {
                    contentString += final_content[i] + "#";
                } else {
                    break;
                }
            }
            //console.log(final_start_time);
            //console.log(final_end_time);
            //console.log(final_registration_time);
            $.ajax({
                url: "http://localhost:3020/createContest/",
                type: "post",
                data: {
                    "name": contest_name.value,
                    "start_time": final_start_time,
                    "end_time": final_end_time,
                    "registration_time": final_registration_time,
                    "content": contentString,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success() {
                    alert("Create contest successfully!");
                    createContest_DialogVisble.value = false;
                    contest_name.value = "";
                    contest_time.value = "";
                    registration_time.value = "";
                }
            });
        };

        const cancel_create_contest = () => {
            createContest_DialogVisble.value = false;
            contest_name.value = "";
            contest_time.value = "";
            registration_time.value = "";
        };

        const refresh_contests = () => {
            $.ajax({
                url: "http://localhost:3020/getContestsList/",
                type: "get",
                success(resp) {
                    cou_contests.value = resp.cou_contests;
                    past_contests.value = resp.past_contests;
                    cou_lens.value = resp.cou_lens;
                    past_lens.value = resp.past_lens;
                    cou_registration_to_start_lens.value = resp.cou_registration_to_start_lens;
                    console.log(cou_registration_to_start_lens.value);
                    for (let i = 0; i < cou_contests.value.length; i ++ ) {
                        cou_len_map.value.set(cou_contests.value[i].id, i);
                    }
                    for (let i = 0; i < past_contests.value.length; i ++ ) {
                        past_len_map.value.set(past_contests.value[i].id, i);
                    }
                    // console.log(resp);
                }
            });
        };

        refresh_contests();

        /*setInterval(() => {
            console.log(value1.value);
        }, 1000);*/

        return {
            createContest_DialogVisble,
            contest_name,
            contest_time,
            registration_time,
            Edit,
            create_contest,
            cancel_create_contest,
            topic_DialogVisble,
            title,
            content,
            input_format,
            output_format,
            data_range,
            input_example,
            output_example,
            conserve_topics,
            topic_id,
            add_topic,
            cou_contests,
            past_contests,
            cou_lens,
            past_lens,
            cou_registration_to_start_lens,
            cou_len_map,
            past_len_map,
            value,
            to_final_standings,
            to_contest_page,
        }
    }
}
</script> 

<style scoped>
.flex-container {
  display: flex;
  align-items: center; 
}
.block {
  padding: 10px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}
.block:last-child {
  border-right: none;
}
.block .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
.demo-datetime-picker {
  display: flex;
  width: 100%;
  padding: 0;
  flex-wrap: wrap;
}
.demo-datetime-picker .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}
.demo-datetime-picker .block:last-child {
  border-right: none;
}
.demo-datetime-picker .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
.countdown-footer {
  margin-top: 8px;
}
</style>