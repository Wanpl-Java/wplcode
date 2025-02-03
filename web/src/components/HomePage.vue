<template>
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <div class="container">
        <div class="row">
            <div class="col-8">
                <div class="flex-container" style="margin-top: 10px;">
                    <img src="../assets/logo.png" style="width: 80px; margin-left: 95px; margin-top: 10px;">
                    <div style="font-family: '黑体'; font-size: 32px; margin-top: 10px; font-weight: 600;">
                        W
                    </div>
                    <div style="font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600;">
                        P
                    </div>
                    <div style="font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600;">
                        L
                    </div>
                    <div style="font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600; color: #6666FF;">
                        C
                    </div>
                    <div style="font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600; color: #6666FF;">
                        o
                    </div>
                    <div style="font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600; color: #6666FF;">
                        d
                    </div>
                    <div style="font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600; color: #6666FF;">
                        e
                    </div>
                </div>
            </div>
            <div class="col-1">
            </div>
            <div class="col-2">
                <div style="margin-left: 140px;">
                    <div class="flex-container">
                        <img src="../assets/China.jpg" style="width: 25px; margin-top: 40px;">
                        <img src="../assets/America.jpg" style="width: 25px; margin-top: 40px; margin-left: 6px;">
                    </div>
                </div>
                <div v-if="jwt_token === null" class="flex-container" style="margin-top: 15px; float: right;">
                    <a href="javascript:void(0)" @click="to_login" style="color: #0000CC;">Enter</a>
                    <div style="color: #0000CC;">&nbsp;|&nbsp;</div>
                    <a href="javascript:void(0)" @click="to_register" style="color: #0000CC;">Register</a>
                </div>
                <div v-else-if="jwt_token !== null" class="flex-container" style="margin-top: 15px; float: right;">
                    <a href="javascript:void(0)" style="color: #0000CC;">{{ username }}</a>
                    <div style="color: #0000CC;">&nbsp;|&nbsp;</div>
                    <a href="javascript:void(0)" @click="logout" style="color: #0000CC;">Logout</a>
                </div>
            </div>
            <div class="col-1">
            </div>
        </div>
    </div>
    <hr>
    <div class="container" style="text-align: center; margin-top: 10px;">
        <div class="row">
            <div class="col-1">

            </div>
            <div class="col-10">
                <nav class="navbar navbar-expand-lg bg-body-tertiary" style="border: 1px solid black; border-radius: 10px;">
                    <div class="container-fluid">
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="javascript:void(0)" @click="to_myhome">HOME</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">CONTESTS</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">GYM</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">RATING</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">HELP</a>
                                </li>
                            </ul>
                            <input class="search" name="query" value=""> 
                        </div>
                    </div>
                </nav>
            </div>
            <div class="col-1">
                
            </div>
            <!--********************************************************************************************************-->
            <div class="col-1">

            </div>
            <div class="col-7">

            </div>
            <div v-if="route_name === 'myhome_index'" class="col-3">
                <MyhomeIndexView />
            </div>
            <div class="col-1">

            </div>
            <div class="col-2">

            </div>
           <div v-if="route_name === 'register_index'" class="col-8">
                <RegisterIndexView />
            </div>
            <div v-else-if="route_name === 'login_index'" class="col-8">
                <LoginIndexView />
            </div>
            <div v-else-if="route_name === 'passwordRecovery_index'" class="col-8">
                <PasswordRecoveryIndexView />
            </div>
            <div class="col-2">

            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import router from '../router/index'
import MyhomeIndexView from '../views/MyhomeIndexView.vue'
import RegisterIndexView from '../views/RegisterIndexView.vue'
import LoginIndexView from '../views/LoginIndexView.vue'
import PasswordRecoveryIndexView from '../views/PasswordRecoveryIndexView.vue'

export default {
    components: {
        MyhomeIndexView,
        RegisterIndexView,
        LoginIndexView,
        PasswordRecoveryIndexView,
    },
    setup() {
        let jwt_token = ref(localStorage.getItem("wplcode_jwt_token"));
        let username = ref("");

        const route = useRoute();

        let route_name = computed(() => route.name);

        const to_myhome = () => {
            router.push({name: 'myhome_index'});
        };

        const to_register = () => {
            router.push({name: 'register_index'});
        };

        const to_login = () => {
            router.push({name: 'login_index'});
        };

        const logout = () => {
            localStorage.removeItem("wplcode_jwt_token");
            location.reload();
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
                        username.value = resp.user.username;
                    }
                });
            }
        });

        return {
            jwt_token,
            username,
            logout,
            route_name,
            to_myhome,
            to_register,
            to_login,
        }
    }
}
</script>

<style scoped>
.flex-container {
  display: flex;
  align-items: center; 
}
.search {
    border: 1px solid black;
    color: #666;
    font-size: 1.0rem;
    height: 20px;
    width: 9em;
    line-height: 16px;
    float: right;
    padding: 1px 1px 1px 20px;
    margin: 4px 1em 0 0;
    margin-bottom: 6px;
    margin-left: 550px;
    background: #f4f4f4 url(../assets/search-16x16\ .png) 2px 2px no-repeat;
}
tr, td, th {
    border: 1px solid black;
}
</style>