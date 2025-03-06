<template>
    <div class="card" style="width: 18rem; margin-left: 20px; margin-top: 20px;">
        <ul class="list-group list-group-flush">
            <li class="list-group-item" style="border: 1px solid black; border-bottom: none;">
                <div style="float: left; color: #3B5998; font-weight: 600;">
                    → Pay attention
                </div>
            </li>
            <li class="list-group-item" style="border: 1px solid black;">
                <div style="color: #3B5998; font-weight: 600;">
                    Before contest
                </div>
                <a href="javascript:void(0)" @click="to_latest_contest(latest_contest.id);" style="color: #0000CC;">{{ latest_contest.name }}</a>
                <div style="color: #777;">{{ days }} days</div>
                <a v-if="can_register === true" href="javascript:void(0)" @click="to_latest_contest(latest_contest.id);" style="color: #0000CC; font-size: 15px;">Register now »</a>
                <a v-else-if="can_register === false" href="/contests/" style="color: #0000CC; font-size: 15px;">To contests »</a>
                <div style="color: #777; font-size: 12px;">
                    *has extra registration
                    <el-popover
                        placement="bottom"
                        :width="585"
                        :height="30"
                        trigger="hover"
                        content="If you are late to register in 5 minutes before the start, you can register later during the extra registration. Extra registration opens 10 minutes after the contest starts and lasts 25 minutes."
                    >
                        <template #reference>
                            <img @click="asklogo_DialogVisble = true;" src="../assets/icon1.png" style="width: 12px; margin-bottom: 10px; cursor: pointer;">
                        </template>
                    </el-popover>
                    <el-dialog v-model="asklogo_DialogVisble" width="360" center>
                        <div style="font-weight: 600;">
                            If you are late to register in 5 minutes before the start, you can register later during the extra registration. Extra registration opens 10 minutes after the contest starts and lasts 25 minutes.
                        </div>
                    </el-dialog>
                </div>
            </li>
        </ul>
    </div>
    <div v-if="jwt_token !== null" class="card" style="width: 18rem; margin-left: 20px; margin-top: 20px;">
        <ul class="list-group list-group-flush">
            <li class="list-group-item" style="border: 1px solid black; border-bottom: none;">
                <div style="float: left; color: #3B5998; font-weight: 600;">
                    → {{ user.username }}
                </div>
            </li>
            <li class="list-group-item" style="border: 1px solid black;">
                <div class="container">
                    <div class="row">
                        <div class="col-6">
                            <div class="flex-container">
                                <img src="../assets/icon2.png" style="width: 20px;">
                                <div style="font-size: 12px; margin-top: 5px; margin-left: 2px;">
                                    Friends: {{ user.friends }}
                                </div>
                            </div>
                            <ul class="nav-links" style="float: left; font-size: 12px;">
                                <li>
                                    <a href="/settings/" style="font-size: 12px; color: #0000CC; float: left;">Settings</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)" @click="to_all_submissions();" style="font-size: 12px; color: #0000CC; float: left;">Submissions</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)" @click="to_talks();" style="font-size: 12px; color: #0000CC; float: left;">Talks</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-6" style="text-align: right; vertical-align: middle;">
                            <img :src="user.photo" style="width: 50px; margin-top: 8px;">
                            <div style="font-size: 12px; margin-right: 7px;">
                                photo
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="card" style="width: 18rem; margin-left: 20px; margin-top: 20px;">
        <ul class="list-group list-group-flush">
            <li class="list-group-item" style="border: 1px solid black; border-bottom: none;">
                <div style="float: left; color: #3B5998; font-weight: 600;">
                    → Top rated
                </div>
            </li>
            <table class="rtable" style="border: 1px solid black; border-collapse: collapse;">
                <tbody>
                    <tr>
                        <th class="left" style="width:2.25em;">#</th>
                        <th class="">User</th>
                        <th class="" style="width:5em;">Rating</th>
                    </tr>
                    <tr v-for="user in rating_top_10_users" :key="user.rank">
                        <td>{{ user.rank }}</td>
                        <td v-if="user.rating >= 3000" style="color: #FF0000;">
                            <a @click="to_profile(user.username);" style="cursor: pointer;">
                                {{ user.username }}
                            </a>
                        </td>
                        <td v-else-if="user.rating >= 2400 && user.rating < 3000" style="color: #FF8C00;">
                            <a @click="to_profile(user.username);" style="cursor: pointer;">
                                {{ user.username }}
                            </a>
                        </td>
                        <td v-else-if="user.rating >= 2000 && user.rating < 2400" style="color: #FFD700;">
                            <a @click="to_profile(user.username);" style="cursor: pointer;">
                                {{ user.username }}
                            </a>
                        </td>
                        <td v-else-if="user.rating >= 1700 && user.rating < 2000" style="color: #25BB9B;">
                            <a @click="to_profile(user.username);" style="cursor: pointer;">
                                {{ user.username }}
                            </a>
                        </td>
                        <td v-else-if="user.rating >= 1300 && user.rating < 1700" style="color: #5EA1F4;">
                            <a @click="to_profile(user.username);" style="cursor: pointer;">
                                {{ user.username }}
                            </a>
                        </td>
                        <td v-else-if="user.rating < 1300" style="color: #C177E7;">
                            <a @click="to_profile(user.username);" style="cursor: pointer;">
                                {{ user.username }}
                            </a>
                        </td>
                        <td>{{ user.rating }}</td>
                    </tr>
                </tbody>
            </table>
            <li class="list-group-item" style="border: 1px solid black; border-top: none;">
                <div class="flex-container">
                    <a href="/provinceRating/" style="font-size: 12px; color: #0000CC;">Provinces</a>
                    <div style="font-size: 12px; color: #0000CC;">&nbsp;|&nbsp;</div>
                    <a href="/cityRating/" style="font-size: 12px; color: #0000CC;">Cities</a>
                    <a href="/rating/" style="font-size: 12px; color: #0000CC; margin-left: 96px;">View all →</a>
                </div>
            </li>
        </ul>
    </div>
    <div class="card" style="width: 18rem; margin-left: 20px; margin-top: 20px;">
        <ul class="list-group list-group-flush">
            <li class="list-group-item" style="border: 1px solid black; border-bottom: none;">
                <div style="float: left; color: #3B5998; font-weight: 600;">
                    → Find user
                </div>
            </li>
            <li class="list-group-item" style="border: 1px solid black;">
                <div style="text-align: left; margin-top: 5px;">
                    Username:<input type="text" v-model="input_username" style="width: 150px; height: 20px; margin-left: 10px;"> 
                </div>
                <button @click="findUser" style="border: 1px solid black; margin-top: 10px; text-align: right; font-size: 14px; float: right; margin-right: 11px;">
                    Find
                </button>
            </li>
        </ul>
    </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import router from '../router/index';
import { useRoute } from 'vue-router'
import { computed } from 'vue'

export default {
    /*watch: {
        route_name() {
            let reload_info = localStorage.getItem("reload_info");
            if (reload_info) {
                localStorage.removeItem("reload_info");
                location.reload();
            }
        },
    },*/
    setup() {
        let jwt_token = ref(localStorage.getItem("wplcode_jwt_token"));
        let user = ref(new Object());
        const asklogo_DialogVisble = ref(false);

        const store = useStore();

        const route = useRoute();
        let route_name = computed(() => route.name);

        const rating_top_10_users = ref([]);

        let input_username = ref("");

        let latest_contest = ref(new Object());
        let days = ref(0);
        let can_register = ref(false);

        let page_visit_cnt = ref(store.state.pageVisit.page_visit_cnt);

        const findUser = () => {
            // 为防止影响用户好的体验，判断查询的用户是否存在，如果不存在直接提示用户相关信息并return
            $.ajax({
                url: "http://localhost:3020/findUserExist/",
                type: "get",
                data: {
                    "username": input_username.value,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        store.commit("updateProfileUsername", input_username.value);
                        router.push({
                            name: 'profile_index',
                            params: {
                                "username": input_username.value,
                            }
                        });
                        input_username.value = "";
                    } else {
                        alert("The user who you searched is not exist!");
                        input_username.value = "";
                    }
                }
            });
        };

        const refresh_topRated = () => {
            $.ajax({
                url: "http://localhost:3020/topRated/",
                type: "get",
                success(resp) {
                    rating_top_10_users.value = resp;
                }
            });
        };

        refresh_topRated();

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

        const refresh_latest_contest = () => {
            $.ajax({
                url: "http://localhost:3020/getLatestContest/",
                type: "get",
                success(resp) {
                    latest_contest.value = resp.contest;
                    days.value = resp.days;
                    can_register.value = resp.msg;
                    // console.log(resp);
                }
            });
        };

        refresh_latest_contest();

        const to_latest_contest = contestId => {
            router.push({
                name: "contest_index",
                params: {
                    contestId,
                }
            })
        };

        const to_all_submissions = () => {
            if (jwt_token.value === null) {
                alert("Please login first!");
                router.push({
                    name: "login_index"
                });
                return;
            } else {
                router.push({
                    name: 'allSubmissions_index'
                });
            }
        };

        const to_talks = () => {
            router.push({
                name: 'talks_index'
            });
        };

        onMounted(() => {
            if (jwt_token.value !== null) {
                $.ajax({
                    url: "http://localhost:3020/userinfo/",
                    type: "get",
                    headers: {
                        Authorization: "Bearer " + jwt_token.value,
                    },
                    success(resp) {
                        user.value = resp.user;
                        store.commit("updateUser", resp.user);
                        console.log(resp);
                    }
                });
            }
        });

        return {
            jwt_token,
            asklogo_DialogVisble,
            rating_top_10_users,
            user,
            refresh_topRated,
            to_profile,
            input_username,
            findUser,
            latest_contest,
            to_latest_contest,
            days,
            can_register,
            to_all_submissions,
            to_talks,
            page_visit_cnt,
            route_name,
        }
    }
}
</script>

<style scoped>
.flex-container {
  display: flex;
  align-items: center; 
}
tr, td, th {
    border: 1px solid black;
    /*color: rgb(225, 0, 255);*/
}
</style>