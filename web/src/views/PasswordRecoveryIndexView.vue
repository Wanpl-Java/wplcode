<template>
    <div class="card" style="width: 32rem; margin-left: 150px; margin-top: 60px;">
        <ul class="list-group list-group-flush">
            <li class="list-group-item" style="border: 1px solid black; border-bottom: none;">
                <div style="color: #3B5998; font-weight: 600;">
                    Password recovery
                </div>
            </li>
            <li class="list-group-item" style="border: 1px solid black;">
                <div style="font-weight: 500; margin-top: 15px; margin-left: 0px;">
                    Email <input v-model="email" type="text" style="height: 20px; margin-left: 10px;">
                </div>
                <span style="font-size: 12px; color: #888888;">Enter your email to recover the password</span>
                <div style="font-weight: 500; margin-left: -87px; margin-top: 5px;">
                    Verification code <input v-model="code" type="text" style="height: 20px; margin-left: 10px; width: 80px;">
                    <button v-if="has_gotVCode === false" @click="get_vCode(email)" style="border: 1px solid black; font-size: 14px; font-weight: 350; margin-left: 8px; height: 24px;">Obtain code</button>
                    <button v-else-if="has_gotVCode === true" style="border: 1px solid black; font-size: 14px; font-weight: 350; margin-left: 8px; height: 24px; pointer-events: none; width: 96.23px;">{{ left_time }}s</button>
                </div>
                <div style="font-weight: 500; margin-top: 15px; margin-left: -70px;">
                    New password <input v-model="newPassword" type="password" style="height: 20px; margin-left: 10px;">
                </div>
                <div style="font-weight: 500; margin-top: 15px; margin-left: -97px;">
                    Confirm password <input v-model="confirmedPassword" type="password" style="height: 20px; margin-left: 10px;">
                </div>
                <div>
                    <button @click="passwordRecover" style="border: 1px solid black; font-size: 14px; font-weight: 350; width: 85px; margin-top: 20px;">Recover</button>
                </div>
                <div style="visibility: hidden; font-size: 5px;">
                    &nbsp;
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import { ref } from 'vue'
import $ from 'jquery'
import router from '../router/index';
import { useRoute } from 'vue-router'
import { computed } from 'vue'

export default {
    setup() {
        let email = ref("");
        let newPassword = ref("");
        let confirmedPassword = ref("");
        let code = ref("");

        let has_gotVCode = ref(false);
        let left_time = ref(600); // 10min

        const route = useRoute();

        let route_name = computed(() => route.name);

        document.addEventListener("keydown", function(event) {
            // 判断按下的键是否是回车键（键码为13）
            if(event.key === 'Enter' && route_name.value === 'passwordRecovery_index') {
                // 执行相应的操作，例如提交表单或执行搜索功能
                passwordRecover();
                return;
            }
        });

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
                    let intervalId =  setInterval(() => {
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

        const passwordRecover = () => {
            $.ajax({
                url: "http://localhost:3020/passwordRecover/",
                type: "put",
                dataType: "json",
                contentType:"application/json;charset=utf-8",
                data: JSON.stringify({
                    "username": "",
                    "password": newPassword.value,
                    "confirmedPassword": confirmedPassword.value,
                    "email": email.value,
                    "code": code.value,
                }),
                success(resp) {
                    if (resp.error_message === "success") {
                        alert("Recover password successfully!");
                        router.push({ name: 'login_index' });
                    } else {
                        alert(resp.error_message);
                    }
                }
            });
        };

        return {
            email,
            newPassword,
            confirmedPassword,
            code,
            has_gotVCode,
            left_time,
            get_vCode,
            passwordRecover,
       }
    }
}
</script>

<style scoped>
</style>