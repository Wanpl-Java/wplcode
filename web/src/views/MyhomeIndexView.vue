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
                <a href="#" style="color: #0000CC;">WPLCode Round 1(Div.3)</a>
                <div style="color: #777;">2 days</div>
                <a href="#" style="color: #0000CC; font-size: 15px;">Register now »</a>
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
                                    Friends: 0
                                </div>
                            </div>
                            <ul class="nav-links" style="float: left; font-size: 12px;">
                                <li>
                                    <a href="/settings/" style="font-size: 12px; color: #0000CC; float: left;">Settings</a>
                                </li>
                                <li>
                                    <a href="#" style="font-size: 12px; color: #0000CC; float: left;">Submissions</a>
                                </li>
                                <li>
                                    <a href="#" style="font-size: 12px; color: #0000CC; float: left;">Talks</a>
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

export default {
    setup() {
        let jwt_token = ref(localStorage.getItem("wplcode_jwt_token"));
        let user = ref(new Object());
        const asklogo_DialogVisble = ref(false);

        const store = useStore();

        const rating_top_10_users = ref([]);

        let input_username = ref("");

        const findUser = () => {
            store.commit("updateProfileUsername", input_username.value);
            setTimeout(() => {
                router.push({
                    name: 'profile_index',
                    params: {
                        "username": input_username.value,
                    }
                });
                input_username.value = "";
            }, 20);
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