<template>
    <div class="card" style="width: 32rem; margin-left: 150px; margin-top: 60px;">
        <ul class="list-group list-group-flush">
            <li class="list-group-item" style="border: 1px solid black; border-bottom: none;">
                <div style="color: #3B5998; font-weight: 600;">
                    Register in WPLCode
                </div>
            </li>
            <li class="list-group-item" style="border: 1px solid black;">
                <div style="font-weight: 500; margin-top: 15px;">
                    Username <input v-model="username" type="text" style="height: 20px; margin-left: 10px;">
                </div>
                <div style="font-weight: 500; margin-left: 6px; margin-top: 20px;">
                    Password <input v-model="password" type="password" style="height: 20px; margin-left: 10px;">
                </div>
                <div style="font-weight: 500; margin-left: -60px; margin-top: 20px;">
                    Confrim password <input v-model="confirmedPassword" type="password" style="height: 20px; margin-left: 10px;">
                </div>
                <div style="font-weight: 500; margin-left: 37px; margin-top: 20px;">
                    Email <input v-model="email" type="text" style="height: 20px; margin-left: 10px;">
                </div>
                <div style="font-weight: 500; margin-left: -55px; margin-top: 20px;">
                    Verification code <input v-model="code" type="text" style="height: 20px; margin-left: 10px; width: 80px;">
                    <button v-if="has_gotVCode === false" @click="get_vCode(email)" style="border: 1px solid black; font-size: 14px; font-weight: 350; margin-left: 8px; height: 24px;">Obtain code</button>
                    <button v-else-if="has_gotVCode === true" style="border: 1px solid black; font-size: 14px; font-weight: 350; margin-left: 8px; height: 24px; pointer-events: none; width: 96.23px;">{{ left_time }}s</button>
                </div>
                <button @click="register" style="border: 1px solid black; font-size: 14px; font-weight: 350; width: 85px; margin-top: 20px;">Register</button>
                <div style="visibility: hidden; font-size: 5px;">
                    &nbsp;
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import { ref } from 'vue'
import $ from 'jquery';
import router from '../router/index'

export default {
    setup() {
        let username = ref("");
        let password = ref("");
        let confirmedPassword = ref("");
        let email = ref("");
        let code = ref("");

        let has_gotVCode = ref(false);
        let left_time = ref(600); // 10min

        const register = () => {
            $.ajax({
                url: "http://localhost:3020/register/",
                type: "post",
                dataType: "json",
                contentType:"application/json;charset=utf-8",
                data: JSON.stringify({
                    "username": username.value,
                    "password": password.value,
                    "confirmedPassword": confirmedPassword.value,
                    "email": email.value,
                    "code": code.value,
                }),
                success(resp) {
                    if (resp.error_message === "success") {
                        alert("Register successfully!");
                        router.push({ name: 'login_index' });
                    } else {
                        alert(resp.error_message);
                    }
                }
            });
        };

        // 获取验证码
        const get_vCode = (email) => {
            $.ajax({
                url: "http://localhost:3020/sendVCode/",
                type: "post",
                data: {
                    email,
                },
                success() {
                    has_gotVCode.value = true;
                    console.log("send message successfully!");
                    let intervalId = setInterval(() => {
                        if (left_time.value > 0) {
                            left_time.value -= 1;
                        } else {
                            has_gotVCode.value = false;
                            left_time.value = 600;
                            clearInterval(intervalId);
                        }
                    }, 1000);
                },
                error() {
                    alert("Please enter the correct email address first!");
                }
            });
        };

        return {
            username,
            password,
            confirmedPassword,
            email,
            code,
            register,
            get_vCode,
            left_time,
            has_gotVCode,
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