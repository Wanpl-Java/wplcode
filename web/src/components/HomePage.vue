<template>
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <div class="container">
        <div class="row">
            <div class="col-8">
                <div class="flex-container" style="margin-top: 10px;">
                    <img src="../assets/icon25.png" style="width: 80px; margin-left: 95px; margin-top: 10px;">
                    <div v-if="route_name !== 'plugins_index'" style="font-family: '黑体'; font-size: 32px; margin-top: 10px; font-weight: 600; margin-left: 8px;">
                        W
                    </div>
                    <div v-else-if="route_name === 'plugins_index'" style="color: white; font-family: '黑体'; font-size: 32px; margin-top: 10px; font-weight: 600; margin-left: 8px;">
                        W
                    </div>
                    <div v-if="route_name !== 'plugins_index'" style="font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600;">
                        P
                    </div>
                    <div v-else-if="route_name === 'plugins_index'" style="color: white; font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600;">
                        P
                    </div>
                    <div v-if="route_name !== 'plugins_index'" style="font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600;">
                        L
                    </div>
                    <div v-else-if="route_name === 'plugins_index'" style="color: white; font-family: '黑体'; font-size: 32px; margin-top: 10px; margin-left: 5px; font-weight: 600;">
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
                    <a v-if="route_name !== 'plugins_index'" href="javascript:void(0)" @click="to_login" style="color: #0000CC;">Enter</a>
                    <a v-else-if="route_name === 'plugins_index'" href="javascript:void(0)" @click="to_login" style="color: white;">Enter</a>
                    <div v-if="route_name !== 'plugins_index'" style="color: #0000CC;">&nbsp;|&nbsp;</div>
                    <div v-else-if="route_name === 'plugins_index'" style="color: white;">&nbsp;|&nbsp;</div>
                    <a v-if="route_name !== 'plugins_index'" href="javascript:void(0)" @click="to_register" style="color: #0000CC;">Register</a>
                    <a v-else-if="route_name === 'plugins_index'" href="javascript:void(0)" @click="to_register" style="color: white;">Register</a>
                </div>
                <div v-else-if="jwt_token !== null" class="flex-container" style="margin-top: 15px; float: right;">
                    <a v-if="route_name !== 'plugins_index'" href="javascript:void(0)" @click="to_profile(username);" style="color: #0000CC;">{{ username }}</a>
                    <a v-else-if="route_name === 'plugins_index'" href="javascript:void(0)" @click="to_profile(username);" style="color: white;">{{ username }}</a>
                    <div v-if="route_name !== 'plugins_index'" style="color: #0000CC;">&nbsp;|&nbsp;</div>
                    <div v-else-if="route_name === 'plugins_index'" style="color: white;">&nbsp;|&nbsp;</div>
                    <a v-if="route_name !== 'plugins_index'" href="javascript:void(0)" @click="logout" style="color: #0000CC;">Logout</a>
                    <a v-else-if="route_name === 'plugins_index'" href="javascript:void(0)" @click="logout" style="color: white;">Logout</a>
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
                <nav v-if="route_name !== 'plugins_index'" class="navbar navbar-expand-lg bg-body-tertiary" style="border: 1px solid black; border-radius: 10px;">
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
                                    <a v-if="route_name === 'allSubmissions_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_all_submissions">SUBMISSIONS</a>
                                    <a v-else-if="route_name !== 'allSubmissions_index'" class="nav-link" href="javascript:void(0)" @click="to_all_submissions">SUBMISSIONS</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'help_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_help">HELP</a>
                                    <a v-else-if="route_name !== 'help_index'" class="nav-link" href="javascript:void(0)" @click="to_help">HELP</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'game_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_game">GAME</a>
                                    <a v-else-if="route_name !== 'game_index'" class="nav-link" href="javascript:void(0)" @click="to_game">GAME</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'ai_square_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_ai_square();">AI</a>
                                    <a v-else-if="route_name !== 'ai_square_index'" class="nav-link" href="javascript:void(0)" @click="to_ai_square();">AI</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'plugins_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline;" @click="to_plugins();">PLUGINS</a>
                                    <a v-else-if="route_name !== 'plugins_index'" class="nav-link" href="javascript:void(0)" @click="to_plugins();">PLUGINS</a>
                                </li>
                            </ul>
                            <input class="search" name="query" value="" style="margin-left: 220px;"> 
                        </div>
                    </div>
                </nav>
                <nav v-else-if="route_name === 'plugins_index'" class="navbar navbar-expand-lg bg-dark" style="border: 1px solid white; border-radius: 10px;">
                    <div class="container-fluid">
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a v-if="route_name === 'myhome_index'" class="nav-link active" aria-current="page" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_myhome">HOME</a>
                                    <a v-else-if="route_name !== 'myhome_index'" class="nav-link active" aria-current="page" href="javascript:void(0)" style="color: white;" @click="to_myhome">HOME</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'contests_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_contests">CONTESTS</a>
                                    <a v-else-if="route_name !== 'contests_index'" class="nav-link" href="javascript:void(0)" style="color: white;" @click="to_contests">CONTESTS</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'gym_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_gym">GYM</a>
                                    <a v-else-if="route_name !== 'gym_index'" class="nav-link" href="javascript:void(0)" style="color: white;" @click="to_gym">GYM</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'rating_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_rating">RATING</a>
                                    <a v-else-if="route_name !== 'rating_index'" class="nav-link" href="javascript:void(0)" style="color: white;" @click="to_rating">RATING</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'allSubmissions_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_all_submissions">SUBMISSIONS</a>
                                    <a v-else-if="route_name !== 'allSubmissions_index'" class="nav-link" href="javascript:void(0)" style="color: white;" @click="to_all_submissions">SUBMISSIONS</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'help_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_help">HELP</a>
                                    <a v-else-if="route_name !== 'help_index'" class="nav-link" href="javascript:void(0)" style="color: white;" @click="to_help">HELP</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'game_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_game">GAME</a>
                                    <a v-else-if="route_name !== 'game_index'" class="nav-link" href="javascript:void(0)" style="color: white;" @click="to_game">GAME</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'ai_square_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_ai_square();">AI</a>
                                    <a v-else-if="route_name !== 'ai_square_index'" class="nav-link" href="javascript:void(0)" style="color: white;" @click="to_ai_square();">AI</a>
                                </li>
                                <li class="nav-item">
                                    <a v-if="route_name === 'plugins_index'" class="nav-link" href="javascript:void(0)" style="text-decoration: underline; color: white;" @click="to_plugins();">PLUGINS</a>
                                    <a v-else-if="route_name !== 'plugins_index'" class="nav-link" href="javascript:void(0)" style="color: white;" @click="to_plugins();">PLUGINS</a>
                                </li>
                            </ul>
                            <input class="search" name="query" value="" style="margin-left: 220px;"> 
                        </div>
                    </div>
                </nav>
            </div>
            <div class="col-1">
                
            </div>
            <div v-if="route_name === 'officialContest_index'" class="col-12">
                <OfficialContestIndexView />
            </div>
            <div v-else-if="route_name === 'allSubmissions_index'" class="col-12">
                <AllSubmissionsIndexView />
            </div>
            <!--********************************************************************************************************-->
            <div class="col-1">

            </div>
            <div v-if="route_name === 'plugins_index'" class="col-10">
                <PluginsIndexView />
            </div>
            <div v-if="route_name !== 'plugins_index'" class="col-7">
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
                    <div style="font-size: 32px; margin-top: 30px; font-weight: 600;">
                        Visualized data display for different provinces
                    </div>
                    <div id="china_map_box">
                        <div id="china_map"></div>
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
                <div v-else-if="route_name === 'talks_index'">
                    <TalksIndexView />
                </div>
                <div v-else-if="route_name === 'ai_square_index'">
                    <AiSquareIndexView />
                </div>
            </div>
            <div v-if="route_name === 'myhome_index' || route_name === 'contests_index' || route_name === 'gym_index' || route_name === 'rating_index' || route_name === 'help_index' || route_name === 'game_index' ||
                       route_name === 'provinceRating_index' || route_name === 'cityRating_index' || route_name === 'settings_index' || route_name === 'profile_index' || route_name === 'talks_index' ||
                       route_name === 'ai_square_index'" class="col-3">
                <MyhomeIndexView />
            </div>
            <div v-else-if="route_name === 'standings_index' || 'contest_index'" class="col-3">

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
            <div v-else-if="route_name === 'contest_index'" class="col-8">
                <ContestIndexView />
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
import echarts from "echarts";
import 'echarts/map/js/china.js';
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
import ContestIndexView from '../views/ContestIndexView.vue'
import OfficialContestIndexView from '../views/OfficialContestIndexView.vue'
import AllSubmissionsIndexView from '../views/AllSubmissionsIndexView.vue'
import TalksIndexView from '../views/TalksIndexView.vue'
import AiSquareIndexView from '../views/AiSquareIndexView.vue'
import PluginsIndexView from '../views/PluginsIndexView.vue'

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
        ContestIndexView,
        OfficialContestIndexView,
        AllSubmissionsIndexView,
        TalksIndexView,
        AiSquareIndexView,
        PluginsIndexView,
    },
    setup() {
        let jwt_token = ref(localStorage.getItem("wplcode_jwt_token"));
        let username = ref("");

        const route = useRoute();
        const store = useStore();

        let echarts_info_count = ref(new Map());
        let echarts_info_avg_rating = ref(new Map());

        const init_echarts_info = () => {
            echarts_info_count.value.set("北京", 0);
            echarts_info_count.value.set("天津", 0);
            echarts_info_count.value.set("上海", 0);
            echarts_info_count.value.set("重庆", 0);
            echarts_info_count.value.set("河北", 0);
            echarts_info_count.value.set("河南", 0);
            echarts_info_count.value.set("云南", 0);
            echarts_info_count.value.set("辽宁", 0);
            echarts_info_count.value.set("黑龙江", 0);
            echarts_info_count.value.set("湖南", 0);
            echarts_info_count.value.set("安徽", 0);
            echarts_info_count.value.set("山东", 0);
            echarts_info_count.value.set("新疆", 0);
            echarts_info_count.value.set("江苏", 0);
            echarts_info_count.value.set("浙江", 0);
            echarts_info_count.value.set("江西", 0);
            echarts_info_count.value.set("湖北", 0);
            echarts_info_count.value.set("广西", 0);
            echarts_info_count.value.set("甘肃", 0);
            echarts_info_count.value.set("山西", 0);
            echarts_info_count.value.set("内蒙古", 0);
            echarts_info_count.value.set("陕西", 0);
            echarts_info_count.value.set("吉林", 0);
            echarts_info_count.value.set("福建", 0);
            echarts_info_count.value.set("贵州", 0);
            echarts_info_count.value.set("广东", 0);
            echarts_info_count.value.set("青海", 0);
            echarts_info_count.value.set("西藏", 0);
            echarts_info_count.value.set("四川", 0);
            echarts_info_count.value.set("宁夏", 0);
            echarts_info_count.value.set("海南", 0);
            echarts_info_count.value.set("台湾", 0);
            echarts_info_count.value.set("香港", 0);
            echarts_info_count.value.set("澳门", 0);
            
            echarts_info_avg_rating.value.set("北京", 0);
            echarts_info_avg_rating.value.set("天津", 0);
            echarts_info_avg_rating.value.set("上海", 0);
            echarts_info_avg_rating.value.set("重庆", 0);
            echarts_info_avg_rating.value.set("河北", 0);
            echarts_info_avg_rating.value.set("河南", 0);
            echarts_info_avg_rating.value.set("云南", 0);
            echarts_info_avg_rating.value.set("辽宁", 0);
            echarts_info_avg_rating.value.set("黑龙江", 0);
            echarts_info_avg_rating.value.set("湖南", 0);
            echarts_info_avg_rating.value.set("安徽", 0);
            echarts_info_avg_rating.value.set("山东", 0);
            echarts_info_avg_rating.value.set("新疆", 0);
            echarts_info_avg_rating.value.set("江苏", 0);
            echarts_info_avg_rating.value.set("浙江", 0);
            echarts_info_avg_rating.value.set("江西", 0);
            echarts_info_avg_rating.value.set("湖北", 0);
            echarts_info_avg_rating.value.set("广西", 0);
            echarts_info_avg_rating.value.set("甘肃", 0);
            echarts_info_avg_rating.value.set("山西", 0);
            echarts_info_avg_rating.value.set("内蒙古", 0);
            echarts_info_avg_rating.value.set("陕西", 0);
            echarts_info_avg_rating.value.set("吉林", 0);
            echarts_info_avg_rating.value.set("福建", 0);
            echarts_info_avg_rating.value.set("贵州", 0);
            echarts_info_avg_rating.value.set("广东", 0);
            echarts_info_avg_rating.value.set("青海", 0);
            echarts_info_avg_rating.value.set("西藏", 0);
            echarts_info_avg_rating.value.set("四川", 0);
            echarts_info_avg_rating.value.set("宁夏", 0);
            echarts_info_avg_rating.value.set("海南", 0);
            echarts_info_avg_rating.value.set("台湾", 0);
            echarts_info_avg_rating.value.set("香港", 0);
            echarts_info_avg_rating.value.set("澳门", 0);
        };

        init_echarts_info();

        let province_count = ref([
            0, // 北京
            0, // 天津
            0, // 上海
            0, // 重庆
            0, // 河北
            0, // 河南
            0, // 云南
            0, // 辽宁
            0, // 黑龙江
            0, // 湖南
            0, // 安徽
            0, // 山东
            0, // 新疆
            0, // 江苏
            0, // 浙江
            0, // 江西
            0, // 湖北
            0, // 广西
            0, // 甘肃
            0, // 山西
            0, // 内蒙古
            0, // 陕西
            0, // 吉林
            0, // 福建
            0, // 贵州
            0, // 广东
            0, // 青海
            0, // 西藏
            0, // 四川
            0, // 宁夏
            0, // 海南
            0, // 台湾
            0, // 香港
            0, // 澳门
        ]);

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

        const to_myhome = () => {
            /*localStorage.setItem("reload_info", true);
            setTimeout(() => {
                router.push({name: 'myhome_index'});
            }, 20);*/
            router.push({ name: 'myhome_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const to_register = () => {
            /*localStorage.setItem("reload_info", true);
            setTimeout(() => {
                router.push({ name: 'register_index' });
            }, 20);*/
            router.push({ name: 'register_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const to_login = () => {
            /*localStorage.setItem("reload_info", true);
            setTimeout(() => {
                router.push({ name: 'login_index' });
            }, 20);*/
            router.push({ name: 'login_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const to_contests = () => {
            /*localStorage.setItem("reload_info", true);
            setTimeout(() => {
                router.push({ name: 'contests_index' });
            }, 20);*/
            router.push({ name: 'contests_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        }

        const to_gym = () => {
            /*localStorage.setItem("reload_info", true);
            setTimeout(() => {
                router.push({ name: 'gym_index' });
            }, 20);*/
            router.push({ name: 'gym_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const to_rating = () => {
            /*localStorage.setItem("reload_info", true);
            setTimeout(() => {
                router.push({ name: 'rating_index' });
            }, 20);*/
            router.push({ name: 'rating_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const to_help = () => {
            /*localStorage.setItem("reload_info", true);
            setTimeout(() => {
                router.push({ name: 'help_index' });
            }, 20);*/
            router.push({ name: 'help_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const to_game = () => {
            /*localStorage.setItem("reload_info", true);
            setTimeout(() => {
                router.push({ name: 'game_index' });
            }, 20);*/
            router.push({ name: 'game_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const to_ai_square = () => {
            router.push({ name: 'ai_square_index' });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const to_plugins = () => {
            router.push({
                name: 'plugins_index',
            });
            setTimeout(() => {
                location.reload();
            }, 10);
        };

        const logout = () => {
            localStorage.removeItem("wplcode_jwt_token");
            location.reload();
        };

        onMounted(() => {
            refresh_echarts_province();
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

        const refresh_echarts_province = () => {
            $.ajax({
                url: "http://localhost:3020/echartsProvince/",
                type: "get",
                success(resp) {
                    let obj = resp.info;
                    for (let key in obj) {
                        echarts_info_count.value.set(key, obj[key]["count"]);
                        echarts_info_avg_rating.value.set(key, obj[key]["averageRating"]);
                    }
                    // console.log(resp.info);
                    // echarts_info = resp.info;
                    // province_count.value[0] = echarts_info["江苏"].count;
                    // console.log(province_count.value[0]);
                    // console.log(resp);
                }
            });
        };

        const options = {
            tooltip: {
                triggerOn: "mousemove",   //mousemove、click
                padding: 8,
                borderWidth: 1,
                borderColor: '#409eff',
                backgroundColor: 'rgba(255,255,255,0.7)',
                textStyle:{
                    color:'#000000',
                    fontSize: 13
                },
                formatter: function(e) {
                    let data = e.data;
                    let context = `
                        <div>
                            <p><b style="font-size:15px;">${data.name}</b></p>
                            <p class="tooltip_style"><span class="tooltip_left">用户数</span><span class="tooltip_right">${data.value}</span></p>
                            <p class="tooltip_style"><span class="tooltip_left">平均Rating分</span><span class="tooltip_right">${data.avgRating}</span></p>
                        </div>
                    `
                    return context;
                }
            },
            visualMap: {
                show: true,
                left: 26,
                bottom: 40,
                showLabel: true,
                pieces: [
                    {
                        gte: 1000,
                        label: ">=1000",
                        color: "#1f307b"
                    },
                    {
                        gte: 100,
                        lte: 999,
                        label: "100 - 999",
                        color: "#3c57ce"
                    },
                    {
                        gte: 10,
                        lte: 99,
                        label: "10 - 99",
                        color: "#6f83db"
                    },
                    {
                        gte: 1,
                        lte: 9,
                        label: "1 - 9",
                        color: "#9face7"
                    },
                    {
                        lt: 1,
                        label:'<1',
                        color: "#bcc5ee"
                    }
                ]
            },
            geo: {
                map: "china",
                scaleLimit: {
                    min: 1,
                    max: 2
                },
                zoom: 1,
                top: 120,
                label: {
                    normal: {
                        show:true,
                        fontSize: "14",
                        color: "rgba(0,0,0,0.7)"
                    }
                },
                itemStyle: {
                    normal: {
                        //shadowBlur: 50,
                        //shadowColor: 'rgba(0, 0, 0, 0.2)',
                        borderColor: "rgba(0, 0, 0, 0.2)"
                    },
                    emphasis: {
                        areaColor: "#f2d5ad",
                        shadowOffsetX: 0,
                        shadowOffsetY: 0,
                        borderWidth: 0
                    }
                }
            },
            series: [
                {
                    name: "用户分析",
                    type: "map",
                    geoIndex: 0,
                    data:[]
                }
            ]
        };

        const dataList = ref([]);

        setTimeout(() => {
            dataList.value =  [
            {
                name: "南海诸岛",
                value: 0,
                avgRating: 0,
            },
            {
                name: "北京",
                value: 16,
                avgRating: echarts_info_avg_rating.value.get("北京"),
            },
            {
                name: "天津",
                value: echarts_info_count.value.get("天津"),
                avgRating: echarts_info_avg_rating.value.get("天津"),
            },
            {
                name: "上海",
                value: echarts_info_count.value.get("上海"),
                avgRating: echarts_info_avg_rating.value.get("上海"),
            },
            {
                name: "重庆",
                value: 32,
                avgRating: echarts_info_avg_rating.value.get("重庆"),
            },
            {
                name: "河北",
                value: 11,
                avgRating: echarts_info_avg_rating.value.get("河北"),
            },
            {
                name: "河南",
                value: 121,
                avgRating: echarts_info_avg_rating.value.get("河南"),
            },
            {
                name: "云南",
                value: echarts_info_count.value.get("云南"),
                avgRating: echarts_info_avg_rating.value.get("云南"),
            },
            {
                name: "辽宁",
                value: echarts_info_count.value.get("辽宁"),
                avgRating: echarts_info_avg_rating.value.get("辽宁"),
            },
            {
                name: "黑龙江",
                value: echarts_info_count.value.get("黑龙江"),
                avgRating: echarts_info_avg_rating.value.get("黑龙江"),
            },
            {
                name: "湖南",
                value: echarts_info_count.value.get("湖南"),
                avgRating: echarts_info_avg_rating.value.get("湖南"),
            },
            {
                name: "安徽",
                value: echarts_info_count.value.get("安徽"),
                avgRating: echarts_info_avg_rating.value.get("安徽"),
            },
            {
                name: "山东",
                value: echarts_info_count.value.get("山东"),
                avgRating: echarts_info_avg_rating.value.get("山东"),
            },
            {
                name: "新疆",
                value: echarts_info_count.value.get("新疆"),
                avgRating: echarts_info_avg_rating.value.get("新疆"),
            },
            {
                name: "江苏",
                value: echarts_info_count.value.get("江苏"),
                avgRating: echarts_info_avg_rating.value.get("江苏"),
            },
            {
                name: "浙江",
                value: echarts_info_count.value.get("浙江"),
                avgRating: echarts_info_avg_rating.value.get("浙江"),
            },
            {
                name: "江西",
                value: echarts_info_count.value.get("江西"),
                avgRating: echarts_info_avg_rating.value.get("江西"),
            },
            {
                name: "湖北",
                value: echarts_info_count.value.get("湖北"),
                avgRating: echarts_info_avg_rating.value.get("湖北"),
            },
            {
                name: "广西",
                value: echarts_info_count.value.get("广西"),
                avgRating: echarts_info_avg_rating.value.get("广西"),
            },
            {
                name: "甘肃",
                value: echarts_info_count.value.get("甘肃"),
                avgRating: echarts_info_avg_rating.value.get("甘肃"),
            },
            {
                name: "山西",
                value: echarts_info_count.value.get("山西"),
                avgRating: echarts_info_avg_rating.value.get("山西"),
            },
            {
                name: "内蒙古",
                value: echarts_info_count.value.get("内蒙古"),
                avgRating: echarts_info_avg_rating.value.get("内蒙古"),
            },
            {
                name: "陕西",
                value: echarts_info_count.value.get("陕西"),
                avgRating: echarts_info_avg_rating.value.get("陕西"),
            },
            {
                name: "吉林",
                value: echarts_info_count.value.get("吉林"),
                avgRating: echarts_info_avg_rating.value.get("吉林"),
            },
            {
                name: "福建",
                value: echarts_info_count.value.get("福建"),
                avgRating: echarts_info_avg_rating.value.get("福建"),
            },
            {
                name: "贵州",
                value: echarts_info_count.value.get("贵州"),
                avgRating: echarts_info_avg_rating.value.get("贵州"),
            },
            {
                name: "广东",
                value: echarts_info_count.value.get("广东"),
                avgRating: echarts_info_avg_rating.value.get("广东"),
            },
            {
                name: "青海",
                value: 101,
                avgRating: echarts_info_avg_rating.value.get("青海"),
            },
            {
                name: "西藏",
                value: echarts_info_count.value.get("西藏"),
                avgRating: echarts_info_avg_rating.value.get("西藏"),
            },
            {
                name: "四川",
                value: echarts_info_count.value.get("四川"),
                avgRating: echarts_info_avg_rating.value.get("四川"),
            },
            {
                name: "宁夏",
                value: echarts_info_count.value.get("宁夏"),
                avgRating: echarts_info_avg_rating.value.get("宁夏"),
            },
            {
                name: "海南",
                value: echarts_info_count.value.get("海南"),
                avgRating: echarts_info_avg_rating.value.get("海南"),
            },
            {
                name: "台湾",
                value: echarts_info_count.value.get("台湾"),
                avgRating: echarts_info_avg_rating.value.get("台湾"),
            },
            {
                name: "香港",
                value: echarts_info_count.value.get("香港"),
                avgRating: echarts_info_avg_rating.value.get("香港"),
            },
            {
                name: "澳门",
                value: echarts_info_count.value.get("澳门"),
                avgRating: echarts_info_avg_rating.value.get("澳门"),
            }
        ];
        }, 30);
        

        return {
            province_count,
            echarts_info_count,
            echarts_info_avg_rating,
            options,
            dataList,
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
            to_all_submissions,
            to_ai_square,
            to_plugins,
        }
    },
    methods: {
        //初始化中国地图
        initEchartMap() {
            setTimeout(() => {
                let mapDiv = document.getElementById('china_map');
                if (mapDiv) {
                    let myChart = echarts.init(mapDiv);
                    myChart.setOption(this.options);
                }
            }, 30);
        },
        //修改echart配制
        setEchartOption(){
            this.options.series[0]['data'] = this.dataList;
        }
    },
    created() {
        setTimeout(() => {
            this.setEchartOption();
        }, 30) ;
    },
    mounted() {
        this.$nextTick(() => {
            this.initEchartMap();
        })
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
#china_map_box {
    height: 0px;
    position: relative;
}
#china_map_box #china_map{
    height: 620px;
}
#china_map_box .china_map_logo{
    position: absolute;
    top: 0;
    left: 0;
    width: 45px;
}
</style>
<style>
#china_map .tooltip_style{
    line-height: 1.7;
    overflow: hidden;
}
#china_map .tooltip_left{
    float: left;
}
#china_map .tooltip_right{
    float: right;
}
</style>