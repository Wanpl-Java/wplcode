<template>
    <div style="text-align: left; margin-top: 10px;">
        <a v-if="idx === 'Messages I sent out'" href="javascript:void(0)" @click="idx = 'Messages I sent out'" style="color: black;">Messages I sent out</a>
        <a v-else-if="idx !== 'Messages I sent out'" href="javascript:void(0)" @click="idx = 'Messages I sent out'" style="color: black; text-decoration: none;">Messages I sent out</a>
        <a v-if="idx === 'Messages I received'" href="javascript:void(0)" @click="idx = 'Messages I received'" style="color: black; margin-left: 10px;">Messages I received</a>
        <a v-else-if="idx !== 'Messages I received'" href="javascript:void(0)" @click="idx = 'Messages I received'" style="color: black; margin-left: 10px; text-decoration: none;">Messages I received</a>
        <div class="card" style="margin-top: 5px; width: 770px; background-color: #E1E1E1;">
            <div style="margin-left: 10px;">
                {{ username }}'s messages
            </div>
            <table v-if="idx === 'Messages I sent out'" class="table table-striped table-bordered" style="border: 1px solid black; text-align: center; font-size: 12px; border-collapse: collapse; width: 98%; margin-left: 7px;">
                <thead>
                    <tr style="font-size: 14px;">
                        <th scope="col">Author</th>
                        <th scope="col">Recipient</th>
                        <th scope="col">Content</th>
                        <th scope="col">When</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="sendout in sendouts" :key="sendout.id">
                        <td>{{ username }}</td>
                        <td>{{ sendout.receiver }}</td>
                        <td>{{ sendout.content }}</td>
                        <td>{{ sendout.talkTime }}</td>
                    </tr>
                </tbody>
            </table>
            <table v-else-if="idx === 'Messages I received'" class="table table-striped table-bordered" style="border: 1px solid black; text-align: center; font-size: 12px; border-collapse: collapse; width: 98%; margin-left: 7px;">
                <thead>
                    <tr style="font-size: 14px;">
                        <th scope="col">Author</th>
                        <th scope="col">Recipient</th>
                        <th scope="col">Content</th>
                        <th scope="col">When</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="receive in receives" :key="receive.id">
                        <td>{{ receive.sender }}</td>
                        <td>{{ username }}</td>
                        <td>{{ receive.content }}</td>
                        <td>{{ receive.talkTime }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div style="float: right; margin-top: 5px;">
            <svg xmlns="http://www.w3.org/2000/svg" style="width: 30px;" class="ionicon" viewBox="0 0 512 512"><rect x="48" y="96" width="416" height="320" rx="40" ry="40" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"/><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M112 160l144 112 144-112"/></svg>
            <a href="javascript:void(0)" @click="sendMessage_DialogVisble = true;" style="color: #0000CC; float: right; margin-left: 5px; margin-top: 2px;">Send message</a>
        </div>
        <el-dialog v-model="sendMessage_DialogVisble" width="360" center style="margin-top: 150px;">
            <div style="font-weight: 600; text-align: center; font-size: 20px;">
                Send message
            </div>
            <div style="text-align: center; margin-top: 10px;">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To<input v-model="To" type="text" style="width: 230px; margin-left: 30px;">
            </div>
            <div style="text-align: center; margin-top: 10px;">
                Content&nbsp;&nbsp;&nbsp;<input v-model="Content" type="text" style="width: 230px;">
            </div>
            <el-button @click="send_message" style="justify-content: center; margin-top: 10px; margin-left: 120px;" color="#626AEF">Submit</el-button>
        </el-dialog>
    </div>
</template>

<script>
import { onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import $ from 'jquery';
import router from '../router/index';

export default {
    setup() {
        let idx = ref("Messages I sent out");
        let jwt_token = ref(localStorage.getItem("wplcode_jwt_token"));
        const store = useStore();
        let username = ref("");

        let sendouts = ref([]); 
        let receives = ref([]);

        let sendMessage_DialogVisble = ref(false);

        let To = ref("");
        let Content = ref("");

        const send_message = () => {
            if (jwt_token.value === null) {
                alert("Please login first!");
                router.push({
                    name: "login_index",
                });
                return;
            }
            setTimeout(() => {
                $.ajax({
                    url: "http://localhost:3020/sendMessage/",
                    type: "post",
                    data: {
                        "To": To.value,
                        "Content": Content.value,
                    },
                    headers: {
                        Authorization: "Bearer " + jwt_token.value,
                    },
                    success(resp) {
                        if (resp.error_message === 'success') {
                            alert("Send message successfully!");
                            refresh_sendout();
                            sendMessage_DialogVisble.value = false;
                        } else {
                            alert(resp.error_message);
                        }
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
                        store.commit("updateUser", resp.user);
                        username.value = resp.user.username;
                    }
                });
            }
        });

        const refresh_sendout = () => {
            $.ajax({
                url: "http://localhost:3020/getSendOut/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + jwt_token.value,
                },
                success(resp) {
                    sendouts.value = resp.sendouts;
                }
            });
        };

        refresh_sendout();

        const refresh_receives = () => {
            $.ajax({
                url: "http://localhost:3020/getReceive/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + jwt_token.value,
                },
                success(resp) {
                    receives.value = resp.receives;
                }
            });
        };

        refresh_receives();

        return {
            idx,
            username,
            jwt_token,
            sendouts,
            receives,
            sendMessage_DialogVisble,
            To,
            Content,
            send_message,
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