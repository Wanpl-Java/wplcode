<template>
    <div v-if="have_begun !== 'none'" class="container">
        <div class="row">
            <div class="col-9" style="float: left; text-align: left;">
                <div style="margin-top: 20px; font-size: 24px;">
                    {{ contest.name }}
                    <el-button color="#FF7F52" style="width: 38px; font-size: 11px; color: white; height: 22px; font-weight: 600;" round>原创</el-button>
                </div>
                <div style="margin-top: 20px;">
                    Contest time: {{ contest.startTime }} To {{ contest.endTime }}
                </div>
                <div style="margin-top: 20px;">
                    Duration: {{ duration }}h
                </div>
                <div style="margin-top: 20px;">
                    Have already registrated: {{ contest.registerCounts }} people
                </div>
            </div>
            <div class="col-3">

            </div>
        </div>
    </div>
    <div v-if="have_begun === 'false'">
        <div style="text-align: center; margin-top: 30px; font-size: 20px; font-weight: 600;">
            There is still time before the start of the competition
        </div>
        <div style="margin-top: 15px;">
            <el-button v-if="days < 10" color="#C6C6C6" style="color: white; width: 150px; height: 70px; font-size: 24px; pointer-events: none;">0{{ days }} days</el-button>
            <el-button v-else-if="days >= 10" color="#C6C6C6" style="color: white; width: 150px; height: 70px; font-size: 24px; pointer-events: none;">{{ days }} days</el-button>
            <el-button v-if="hours < 10" color="#C6C6C6" style="color: white; width: 150px; height: 70px; font-size: 24px; pointer-events: none;">0{{ hours }} hours</el-button>
            <el-button v-else-if="hours >= 10" color="#C6C6C6" style="color: white; width: 150px; height: 70px; font-size: 24px; pointer-events: none;">{{ hours }} hours</el-button>
            <el-button v-if="minutes < 10" color="#C6C6C6" style="color: white; width: 150px; height: 70px; font-size: 24px; pointer-events: none;">0{{ minutes }} minutes</el-button>
            <el-button v-else-if="minutes >= 10" color="#C6C6C6" style="color: white; width: 150px; height: 70px; font-size: 24px; pointer-events: none;">{{ minutes }} minutes</el-button>
            <el-button v-if="seconds < 10" color="#C6C6C6" style="color: white; width: 150px; height: 70px; font-size: 24px; pointer-events: none;">0{{ seconds }} seconds</el-button>
            <el-button v-else-if="seconds >= 10" color="#C6C6C6" style="color: white; width: 150px; height: 70px; font-size: 24px; pointer-events: none;">{{ seconds }} seconds</el-button>
        </div>
        <div style="margin-top: 30px;">
            <el-button v-if="have_registered === 'false'" @click="register_contest('true');" color="#25BB9B" style="color: white; height: 50px; font-size: 24px; width: 635px;">Click here to register</el-button>
            <el-button v-else-if="have_registered === 'true'" color="#C6C6C6" style="color: white; height: 50px; font-size: 24px; width: 635px;">Have registered</el-button>
            <el-button v-else-if="have_registered === 'none' && $store.state.user.token === ''" @click="register_contest('none');" color="#25BB9B" style="color: white; height: 50px; font-size: 24px; width: 635px;">Click here to register</el-button>
        </div>
        <el-dialog v-model="registerMsg_DialogVisble" width="500" center style="margin-top: 220px;">
            <div style="text-align: center;">
                <img src="../assets/icon13.png" style="width: 70px;">
            </div>
            <div style="text-align: center; font-size: 20px;">
                Register successfully!
            </div>
        </el-dialog>
    </div>
    <div v-else-if="have_begun === 'true'">
        <div class="card" style="margin-top: 15px; height: 50px; text-align: center;">
            <div class="flex-container" style="margin-top: 10px; justify-content: center;">
                <a v-if="now_show === 'Topics'" href="javascript:void(0)" @click="to_topics" style="text-decoration: none; color: #C6C6C6; font-size: 20px; font-weight: 600; border-bottom: 1px solid black;">Topics</a>
                <a v-else-if="now_show !== 'Topics'" href="javascript:void(0)" @click="to_topics" style="text-decoration: none; color: #C6C6C6; font-size: 20px; font-weight: 600;">Topics</a>
                <a v-if="now_show === 'Submissions'" href="javascript:void(0)" @click="to_submissions" style="text-decoration: none; color: #C6C6C6; font-size: 20px; font-weight: 600; border-bottom: 1px solid black; margin-left: 150px;">Submissions</a>
                <a v-else-if="now_show !== 'Submissions'" href="javascript:void(0)" @click="to_submissions" style="text-decoration: none; color: #C6C6C6; font-size: 20px; font-weight: 600; margin-left: 150px;">Submissions</a>
                <a v-if="now_show === 'Rank'" href="javascript:void(0)" @click="to_rank" style="text-decoration: none; color: #C6C6C6; font-size: 20px; font-weight: 600; border-bottom: 1px solid black; margin-left: 150px;">Rank</a>
                <a v-else-if="now_show !== 'Rank'" href="javascript:void(0)" @click="to_rank" style="text-decoration: none; color: #C6C6C6; font-size: 20px; font-weight: 600; margin-left: 150px;">Rank</a>
            </div>
        </div>
        <table v-if="now_show === 'Topics'" class="table">
            <thead>
                <tr>
                    <th scope="col">Topic id</th>
                    <th scope="col">Title</th>
                    <th scope="col">Pass rate</th>
                    <th scope="col">My status</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="topic in topics" :key="topic.id">
                    <th scope="row">{{ topic.topicId }}</th>
                    <td>
                        <a href="javascript:void(0)" @click="to_official_contest(topic.topicId)" style="text-decoration: none; color: #0000CC;">{{ topic.title }}</a>
                    </td>
                    <td>0/0</td>
                    <td>Not pass</td>
                </tr>
            </tbody>
        </table>
        <table v-else-if="now_show === 'Submissions'" class="table">
            <thead>
                <tr>
                    <th scope="col">Submission id</th>
                    <th scope="col">Username</th>
                    <th scope="col">Topic id</th>
                    <th scope="col">Running result</th>
                    <th scope="col">Use language</th>
                    <th scope="col">Submit time</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="submission in submissions" :key="submission.id">
                    <th scope="row">{{ submission.id }}</th>
                    <td @click="to_profile(submission.owner);" v-if="submission.ownerRating >= 3000" style="color: #FF0000; cursor: pointer;">{{ submission.owner }}</td>
                    <td @click="to_profile(submission.owner);" v-else-if="submission.ownerRating >= 2400 && submission.ownerRating < 3000" style="color: #FF8C00; cursor: pointer;">{{ submission.owner }}</td>
                    <td @click="to_profile(submission.owner);" v-else-if="submission.ownerRating >= 2000 && submission.ownerRating < 2400" style="color: #FFD700; cursor: pointer;">{{ submission.owner }}</td>
                    <td @click="to_profile(submission.owner);" v-else-if="submission.ownerRating >= 1700 && submission.ownerRating < 2000" style="color: #25BB9B; cursor: pointer;">{{ submission.owner }}</td>
                    <td @click="to_profile(submission.owner);" v-else-if="submission.ownerRating >= 1300 && submission.ownerRating < 1700" style="color: #5EA1F4; cursor: pointer;">{{ submission.owner }}</td>
                    <td @click="to_profile(submission.owner);" v-else-if="submission.ownerRating < 1300" style="color: #C177E7; cursor: pointer;">{{ submission.owner }}</td>
                    <td>{{ submission.topicId }}</td>
                    <td v-if="submission.result === 'Accept'" style="color: #25BB9B;">{{ submission.result }}</td>
                    <td v-else-if="submission.result !== 'Accept'" style="color: #FF0000;">{{ submission.result }}</td>
                    <td>Java</td>
                    <td>{{ submission.submitTime }}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <el-dialog v-model="lateRegister_DialogVisble" width="500" center style="margin-top: 260px;">
        <div style="text-align: center;">
            Click the confirm button to register for this contest
        </div>
        <div class="flex-container" style="justify-content: center;">
            <el-button @click="late_register('true');" type="primary" style="margin-top: 20px;">Confirm</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { onMounted, ref } from 'vue';
import $ from 'jquery'
import { useStore } from 'vuex'
import router from '@/router';

export default {
    setup() {
        let contestId = "";
        let contest = ref(new Object());
        let duration = ref(0);

        let submissions = ref([]);

        const store = useStore();

        let topics = ref([]);

        let days = ref(0);
        let hours = ref(0);
        let minutes = ref(0);
        let seconds = ref(0);

        // 展示的是Topics or Submissions or Rank
        let now_show = ref("Topics");

        let registerMsg_DialogVisble = ref(false);
        // 比赛后选择报名
        let lateRegister_DialogVisble = ref(false);

        // 正式比赛是否开始(false、true、none)
        let have_begun = ref("none");
        // 当前用户该场比赛是否报名(false、true、none)
        let have_registered = ref("none");

        const to_topics = () => {
            now_show.value = "Topics";
        }

        const to_submissions = () => {
            now_show.value = "Submissions";
        };

        const to_rank = () => {
            now_show.value = "Rank";
        };

        const to_profile = username => {
            store.commit("updateProfileUsername", username);
            // localStorage.setItem("profile_username", username);
            setTimeout(() => {
                router.push({ 
                    name: 'profile_index',
                    params: {
                        username,
                    }
                });
            }, 20);
        };

        const to_official_contest = topicId => {
            if (store.state.user.token === "") {
                router.push({ name: 'login_index' });
                return;
            }
            if (have_registered.value !== "true") {
                lateRegister_DialogVisble.value = true;
                return;
            }
            router.push({
                name: 'officialContest_index',
                params: {
                    "contestId": contestId,
                    "topicId": topicId,
                }
            })
        };

        onMounted(() => {
            for (let i = 0; i < window.location.pathname.length; i ++ ) {
                if (window.location.pathname[i] >= '0' && window.location.pathname[i] <= '9') {
                    contestId += window.location.pathname[i];
                }
            }
            contestId = parseInt(contestId);
            register_contest("false");
            refresh_contest();
            refresh_topics();
            refresh_submissions("", "");
        });

        // 比赛开始后报名
        const late_register = isOk => {
            $.ajax({
                url: "http://localhost:3020/registerContest/",
                type: "post",
                data: {
                    "contestId": contestId,
                    "isOk": isOk,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success() {
                    location.reload();
                }
            });
        };

        // 报名此场比赛
        const register_contest = isOk => {
            // 未登录用户
            if (isOk === "none") {
                router.push({ name: 'login_index' });
                return;
            }
            $.ajax({
                url: "http://localhost:3020/registerContest/",
                type: "post",
                data: {
                    "contestId": contestId,
                    "isOk": isOk,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === 'have registered!') {
                        have_registered.value = "true";
                    } else if (resp.error_message === 'have not registered!' && isOk === "true") {
                        have_registered.value = "true";
                        registerMsg_DialogVisble.value = true;
                    } else if (resp.error_message === 'have not registered!' && isOk === "false") {
                        have_registered.value = "false";
                    }
                }
            });
        };

        const refresh_submissions = (topicId, username) => {
            $.ajax({
                url: "http://localhost:3020/getSubmissions/",
                type: "get",
                data: {
                    "contestId": contestId,
                    "topicId": topicId,
                    "username": username,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    submissions.value = resp.submissions;
                }
            });
        };

        const refresh_topics = () => {
            $.ajax({
                url: "http://localhost:3020/getTopics/",
                type: "get",
                data: {
                    contestId,
                },
                success(resp) {
                    topics.value = resp.topics;
                    // console.log(resp);
                }
            });
        };

        const refresh_contest = () => {
            $.ajax({
                url: "http://localhost:3020/getContestInfo/",
                type: "get",
                data: {
                    contestId,
                },
                success(resp) {
                    contest.value = resp.contest;
                    duration.value = resp.db_hours;
                    days.value = resp.days;
                    hours.value = resp.hours;
                    minutes.value = resp.minutes;
                    seconds.value = resp.seconds;
                    // console.log(resp);
                    let intervalId = setInterval(() => {
                        if (days.value > 0 || hours.value > 0 || minutes.value > 0 || seconds.value > 0) {
                            have_begun.value = "false";
                            if (seconds.value > 0) {
                                seconds.value -= 1;
                            } else {
                                if (minutes.value > 0) {
                                    minutes.value -= 1;
                                    seconds.value = 59;
                                } else {
                                    if (hours.value > 0) {
                                        hours.value -= 1;
                                        minutes.value = 59;
                                        seconds.value = 59;
                                    } else {
                                        days.value -= 1;
                                        hours.value = 23;
                                        minutes.value = 59;
                                        seconds.value = 59;
                                    }
                                }
                            }
                        } else {
                            have_begun.value = "true";
                            clearInterval(intervalId);
                        }
                    }, 1000);
                }
            });
        };

        return {
            contestId,
            contest,
            duration,
            days,
            hours,
            minutes,
            seconds,
            have_begun,
            have_registered,
            registerMsg_DialogVisble,
            register_contest,
            now_show,
            to_topics,
            to_submissions,
            to_rank,
            topics,
            to_official_contest,
            lateRegister_DialogVisble,
            late_register,
            submissions,
            to_profile,
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