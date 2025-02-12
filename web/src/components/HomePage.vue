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
                    <a href="javascript:void(0)" @click="to_profile(username);" style="color: #0000CC;">{{ username }}</a>
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
                                    <a v-if="route_name === 'myhome_index'" class="nav-link active" aria-current="page" href="javascript:void(0)" style="text-decoration: underline;" @click="to_myhome">HOME</a>
                                    <a v-else-if="route_name !== 'myhome_index'" class="nav-link active" aria-current="page" href="javascript:void(0)" @click="to_myhome">HOME</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'contests_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_contests">CONTESTS</a>
                                    <a v-else-if="route_name !== 'contests_index'" class="nav-link" href="javascript:void(0)" @click="to_contests">CONTESTS</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'gym_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_gym">GYM</a>
                                    <a v-else-if="route_name !== 'gym_index'" class="nav-link" href="javascript:void(0)" @click="to_gym">GYM</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'rating_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_rating">RATING</a>
                                    <a v-else-if="route_name !== 'rating_index'" class="nav-link" href="javascript:void(0)" @click="to_rating">RATING</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'help_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_help">HELP</a>
                                    <a v-else-if="route_name !== 'help_index'" class="nav-link" href="javascript:void(0)" @click="to_help">HELP</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'game_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_game">GAME</a>
                                    <a v-else-if="route_name !== 'game_index'" class="nav-link" href="javascript:void(0)" @click="to_game">GAME</a>
                                </li>
                            </ul>
                            <input class="search" name="query" value="" style="margin-left: 480px;"> 
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
                <div v-if="route_name === 'myhome_index'" >
                    <div class="card" style="width: 100%; margin-top: 20px; border: 1px solid black;">
                        <div style="float: left; text-align: left; margin-left: 15px; margin-top: 10px; font-size: 20px; font-weight: 600;">
                            Welcome to WPLCode!
                        </div>
                        <div style="text-align: left; margin-left: 15px;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hello everyone! As the founder of WPLCode website, I believe it is very meaningful and necessary to briefly introduce myself and this website.
                        </div>
                        <div style="text-align: left; margin-left: 15px;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Firstly, regarding who am I?I am currently a third year undergraduate student at the Wuxi University and hope to work as a Java engineer. In terms of algorithms, I'm the silver medalist of the 2024 JSCPC, the third place winner of the 15th Blue Bridge Cup C++Group, and just the third place winner of the Sky Ladder Team. In engineering, I am familiar with and proficient in Java language, C, C++, Python, JavaScript and other languages, as well as more than twenty technology stacks such as Redis, RabbitMq, Jmeter, SpringBoot, SpringCloud, etc.
                        </div>
                        <div style="text-align: left; margin-left: 15px;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Secondly, regarding this WPLCode? WPLCode is a website dedicated to providing just an online evaluation system for computer programming enthusiasts. This website supports users to practice questions, participate in real-time competitions, and communicate with each other, aiming to provide users with a good and comfortable programming environment and services.
                        </div>
                        <div style="text-align: left; margin-left: 15px;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Finally, thank you for visiting and using our website (product). With your support, so our development team will have more motivation to innovate and maintain our website!
                        </div>
                        <div style="text-align: left; margin-left: 15px;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;By WPLCode_Creator------Word2vec
                        </div>
                        <div style="visibility: hidden; font-size: 5px;">
                            &nbsp;
                        </div>
                    </div>
                </div>
                <div v-else-if="route_name === 'contests_index'">
                    <ContestsIndexView />
                </div>
                <div v-else-if="route_name === 'gym_index'">
                    <GymIndexView />
                </div>
                <div v-else-if="route_name === 'rating_index'">
                    <RatingIndexView />
                </div>
                <div v-else-if="route_name === 'help_index'">
                    <HelpIndexView />
                </div>
                <div v-else-if="route_name === 'game_index'">
                    <GameIndexView />
                </div>
                <div v-else-if="route_name === 'provinceRating_index'">
                    <ProvinceRatingIndexView />
                </div>
                <div v-else-if="route_name === 'cityRating_index'">
                    <CityRatingIndexView />
                </div>
                <div v-else-if="route_name === 'settings_index'">
                    <SettingsIndexView />
                </div>
                <div v-else-if="route_name === 'profile_index'">
                    <ProfileIndexView />
                </div>
            </div>
            <div v-if="route_name === 'myhome_index' || route_name === 'contests_index' || route_name === 'gym_index' || route_name === 'rating_index' || route_name === 'help_index' || route_name === 'game_index' ||
                       route_name === 'provinceRating_index' || route_name === 'cityRating_index' || route_name === 'settings_index' || route_name === 'profile_index'" class="col-3">
                <MyhomeIndexView />
            </div>
            <div v-else-if="route_name === 'standings_index'" class="col-3">

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
            <div v-else-if="route_name === 'standings_index'" class="col-8">
                <StandingsIndexView />
            </div>
            <div class="col-2">

            </div>
            <div class="col-12" style="visibility: hidden;">
                &nbsp;
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex'
import router from '../router/index'
import MyhomeIndexView from '../views/MyhomeIndexView.vue'
import RegisterIndexView from '../views/RegisterIndexView.vue'
import LoginIndexView from '../views/LoginIndexView.vue'
import PasswordRecoveryIndexView from '../views/PasswordRecoveryIndexView.vue'
import ContestsIndexView from '../views/ContestsIndexView.vue'
import GymIndexView from '../views/GymIndexView.vue'
import RatingIndexView from '../views/RatingIndexView.vue'
import HelpIndexView from '../views/HelpIndexView.vue'
import GameIndexView from '../views/GameIndexView.vue'
import ProvinceRatingIndexView from '../views/ProvinceRatingIndexView.vue'
import CityRatingIndexView from '../views/CityRatingIndexView.vue'
import SettingsIndexView from '../views/SettingsIndexView.vue'
import ProfileIndexView from '../views/ProfileIndexView.vue'
import StandingsIndexView from '../views/StandingsIndexView.vue'

export default {
    components: {
        MyhomeIndexView,
        RegisterIndexView,
        LoginIndexView,
        PasswordRecoveryIndexView,
        ContestsIndexView,
        GymIndexView,
        RatingIndexView,
        HelpIndexView,
        GameIndexView,
        ProvinceRatingIndexView,
        CityRatingIndexView,
        SettingsIndexView,
        ProfileIndexView,
        StandingsIndexView,
    },
    setup() {
        let jwt_token = ref(localStorage.getItem("wplcode_jwt_token"));
        let username = ref("");

        const route = useRoute();
        const store = useStore();

        let route_name = computed(() => route.name);

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

        const to_myhome = () => {
            router.push({name: 'myhome_index'});
        };

        const to_register = () => {
            router.push({ name: 'register_index' });
        };

        const to_login = () => {
            router.push({ name: 'login_index' });
        };

        const to_contests = () => {
            router.push({ name: 'contests_index' });
        }

        const to_gym = () => {
            router.push({ name: 'gym_index' });
        };

        const to_rating = () => {
            router.push({ name: 'rating_index' });
        };

        const to_help = () => {
            router.push({ name: 'help_index' });
        };

        const to_game = () => {
            router.push({ name: 'game_index' });
        };

        const logout = () => {
            localStorage.removeItem("wplcode_jwt_token");
            location.reload();
        };

        onMounted(() => {
            if (jwt_token.value !== null) {
                store.commit("updateToken", jwt_token.value);
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

        return {
            jwt_token,
            username,
            logout,
            route_name,
            to_myhome,
            to_register,
            to_login,
            to_contests,
            to_gym,
            to_rating,
            to_help,
            to_game,
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