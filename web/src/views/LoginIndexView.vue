<template>
    <div class="card" style="width: 32rem; margin-left: 400px; margin-top: 20px;">
        <ul class="list-group list-group-flush">
            <li class="list-group-item" style="border: 1px solid black; border-bottom: none;">
                <div style="color: #3B5998; font-weight: 600;">
                    Login into WPLCode
                </div>
            </li>
            <li class="list-group-item" style="border: 1px solid black;">
                <div style="font-weight: 500; margin-top: 15px; margin-left: -50px;">
                    Username/Email <input v-model="username_or_email" type="text" style="height: 20px; margin-left: 10px;">
                </div>
                <div style="font-weight: 500; margin-left: 6px; margin-top: 20px;">
                    Password <input v-model="password" type="password" style="height: 20px; margin-left: 10px;">
                </div>
                <div>
                    <a href="javascript:void(0)" @click="to_passwordRecovery" style="color: #0000CC; font-size: 12px; margin-top: 10px; margin-left: 100px;">Forgot your password?</a>
                </div>
                <button @click="login" style="border: 1px solid black; font-size: 14px; font-weight: 350; width: 75px; margin-top: 10px;">Login</button>
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

export default {
    setup() {
        let username_or_email = ref("");
        let password = ref("");

        const login = () => {
            $.ajax({
                url: "http://localhost:3020/login/",
                type: "post",
                data: {
                    "username_or_email": username_or_email.value,
                    "password": password.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        localStorage.setItem("wplcode_jwt_token", resp.wplcode_jwt_token);
                        router.push({ name: 'myhome_index' });
                        setTimeout(() => {
                            location.reload();
                        }, 5);
                    } else {
                        alert(resp.error_message);
                    }
                }
            });
        };

        const to_passwordRecovery = () => {
            router.push({ name: "passwordRecovery_index" });
        };

        return {
            username_or_email,
            password,
            login,
            to_passwordRecovery,
        }
    }
}
</script>

<style scoped>
</style>