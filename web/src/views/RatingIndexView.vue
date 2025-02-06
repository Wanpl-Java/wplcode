<template>
    <table class="table table-striped table-bordered" style="border: 1px solid black; border-collapse: collapse; margin-top: 20px;">
        <tbody style="font-size: 12px; font-weight: 600; text-align: left;">
            <tr>
                <th class="left" style="width: 50px; text-align: center;">Rank</th>
                <th class="" style="width: 800px;">&nbsp;Who</th>
                <th class="" style="text-align: center;">Participations</th>
                <th class="" style="width:5em; text-align: center;">Rating</th>
            </tr>
            <tr v-for="user in rating_users" :key="user.rank">
                <td style="text-align: center;">{{ user.rank }}</td>
                <td v-if="user.rating >= 3000" style="color: #FF0000;">&nbsp;<img src="../assets/China.jpg" style="width: 20px;">
                    <a @click="to_profile(user.username);" style="cursor: pointer; margin-left: 3px;">
                        {{ user.username }}
                    </a>
                </td>
                <td v-else-if="user.rating >= 2400 && user.rating < 3000" style="color: #FF8C00;">&nbsp;<img src="../assets/China.jpg" style="width: 20px;">
                    <a @click="to_profile(user.username);" style="cursor: pointer; margin-left: 3px;">
                        {{ user.username }}
                    </a>
                </td>
                <td v-else-if="user.rating >= 2000 && user.rating < 2400" style="color: #FFD700;">&nbsp;<img src="../assets/China.jpg" style="width: 20px;">
                    <a @click="to_profile(user.username);" style="cursor: pointer; margin-left: 3px;">
                        {{ user.username }}
                    </a>
                </td>
                <td v-else-if="user.rating >= 1700 && user.rating < 2000" style="color: #25BB9B;">&nbsp;<img src="../assets/China.jpg" style="width: 20px;">
                    <a @click="to_profile(user.username);" style="cursor: pointer; margin-left: 3px;">
                        {{ user.username }}
                    </a>
                </td>
                <td v-else-if="user.rating >= 1300 && user.rating < 1700" style="color: #5EA1F4;">&nbsp;<img src="../assets/China.jpg" style="width: 20px;">
                    <a @click="to_profile(user.username);" style="cursor: pointer; margin-left: 3px;">
                        {{ user.username }}
                    </a>
                </td>
                <td v-else-if="user.rating < 1300" style="color: #C177E7;">&nbsp;<img src="../assets/China.jpg" style="width: 20px;">
                    <a @click="to_profile(user.username);" style="cursor: pointer; margin-left: 3px;">
                        {{ user.username }}
                    </a>
                </td>
                <td style="text-align: center;">{{ user.participation }}</td>
                <td style="text-align: center;">{{ user.rating }}</td>
            </tr>
        </tbody>
    </table>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'
import router from '../router/index'

export default {
    setup() {
        let rating_users = ref([]);
        const store = useStore();

        const refresh_rating_users = () => {
            $.ajax({
                url: "http://localhost:3020/getAllRating/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    rating_users.value = resp;
                }
            });
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

        $.ajax({
            url: "http://localhost:3020/analyseCity/",
            type: "get",
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success(resp) {
                console.log(resp);
            }
        });

        refresh_rating_users();

        return {
            rating_users,
            to_profile,
        }
    }
}
</script>

<style scoped>
</style>