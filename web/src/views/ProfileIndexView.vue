<template>
    <div class="card" style="width: 100%; margin-top: 20px; border: 1px solid black;">
        <div class="container">
            <div class="row">
                <div class="col-7">
                    <img src="../assets/China.jpg" style="width: 50px; text-align: left; float: left; margin-top: 15px; margin-left: 10px;">
                    <div class="flex-container" style="margin-top: 12px;">
                        <div v-if="profile_user.rating >= 3000" style="text-align: left; font-size: 24px; font-weight: 600; color: #FF0000;">
                            &nbsp;{{ profile_user.username }}
                        </div>
                        <div v-else-if="profile_user.rating >= 2400 && profile_user.rating < 3000" style="text-align: left; font-size: 24px; font-weight: 600; color: #FF8C00;">
                            &nbsp;{{ profile_user.username }}
                        </div>
                        <div v-else-if="profile_user.rating >= 2000 && profile_user.rating < 2400" style="text-align: left; font-size: 24px; font-weight: 600; color: #FFD700;">
                            &nbsp;{{ profile_user.username }}
                        </div>
                        <div v-else-if="profile_user.rating >= 1700 && profile_user.rating < 2000" style="text-align: left; font-size: 24px; font-weight: 600; color: #25BB9B;">
                            &nbsp;{{ profile_user.username }}
                        </div>
                        <div v-else-if="profile_user.rating >= 1300 && profile_user.rating < 1700" style="text-align: left; font-size: 24px; font-weight: 600; color: #5EA1F4;">
                            &nbsp;{{ profile_user.username }}
                        </div>
                        <div v-else-if="profile_user.rating < 1300" style="text-align: left; font-size: 24px; font-weight: 600; color: #C177E7;">
                            &nbsp;{{ profile_user.username }}
                        </div>
                        <div style="font-size: 16px; margin-left: 10px;">
                            from&nbsp;{{ profile_user.province }}&nbsp;{{ profile_user.city }}
                        </div>
                        <img v-if="is_friend === false" @click="add_friend();" src="../assets/icon8.png" style="width: 28px; height: 23.5px; cursor: pointer; margin-left: 1px; margin-top: 2px;">
                        <img v-else-if="is_friend === true" @click="remove_friend();" src="../assets/icon2.png" style="width: 30px; height: 26px; cursor: pointer; margin-bottom: 1px;">
                    </div>
                    <div class="flex-container" style="margin-top: 18px;">
                        <img src="../assets/icon9.png" style="width: 35px;">
                        <div style="margin-left: 5px;">
                            Contest rating:
                        </div>
                        <div style="color: #FF0000; margin-left: 5px; font-weight: 600;">
                            {{ profile_user.rating }}
                        </div>
                    </div>
                    <div class="flex-container" style="margin-top: 18px;">
                        <img src="../assets/icon2.png" style="width: 35px;">
                        <div style="margin-left: 5px;">
                            Friend of:
                        </div>
                        <div style="margin-left: 5px; font-weight: 600;">
                            {{ profile_user.friends }}&nbsp;users
                        </div>
                    </div>
                    <div class="flex-container" style="margin-top: 18px;">
                        <img src="../assets/icon10.png" style="width: 35px;">
                        <div style="margin-left: 5px;">
                            Visitors:
                        </div>
                        <div style="margin-left: 5px; font-weight: 600;">
                            {{ visitors }}&nbsp;people
                        </div>
                    </div>
                    <div class="flex-container" style="margin-top: 18px;">
                        <img src="../assets/icon11.png" style="width: 35px;">
                        <div style="margin-left: 5px;">
                            Blogs:
                        </div>
                        <a href="javascript:void(0)" style="margin-left: 5px; font-weight: 600; color: #0000CC;">
                            BLOGS
                        </a>
                    </div>
                </div>
                <div class="col-5">
                    <img :src="profile_user.photo" style="width: 260px; height: 250px; border: 1px solid black; margin-top: 12px; float: right;">
                </div> 
                <div class="col-12">
                    <div ref="echartsContainer" style="width: 600px; height: 400px; text-align: center;">
                    </div>
                </div>
            </div>
        </div>
        <div style="visibility: hidden; font-size: 5px;">
            &nbsp;
        </div>
    </div>
</template>

<script>
import * as echarts from 'echarts';
import { onMounted, onUnmounted, ref, watch } from 'vue';
import { useStore } from 'vuex'
import $ from 'jquery'
import router from '../router/index';

export default {
    mounted() {
        const store = useStore();
        watch(() => store.state.user.profile_username, (newVal) => {
            now_prfile_name.value = newVal;
            setTimeout(() => {
                $.ajax({
                    url: "http://localhost:3020/getRTList/",
                    type: "get",
                    data: {
                        username: now_prfile_name.value,
                    },
                    success(resp) {
                        rating_list.value = resp.rating_list;
                        time_list.value = resp.time_list;
                    }
                });
            }, 20);
            setTimeout(() => {
                var myChart = echarts.init(this.$refs.echartsContainer);
                myChart.setOption({
                    xAxis: {
                        type: 'category',
                        data: time_list.value,
                    },
                    yAxis: {
                        type: 'value',
                        axisLine: {
                            show: true,
                            symbol: ['none', 'none'], // 轴线两端箭头，两个值，none表示没有箭头，arrow表示有箭头
                            symbolSize: [10, 10],
                        },
                    },
                    series: [
                        {
                            data: rating_list.value,
                            type: 'line',
                            label: {
                                show: true,
                            }
                        }
                    ]
                });
            }, 50);
        });
        let now_prfile_name = ref("");
        // time_list与rating_list相对应,一个日期对应一个rating分
        let time_list = ref(["2025-02-05", "2025-02-06", "2025-02-07"]);
        let rating_list = ref(["1200", "1300", "1270"]);
        for (let i = 9; i < window.location.pathname.length - 1; i ++ ) {
            now_prfile_name.value += window.location.pathname[i];
        }
        setTimeout(() => {
            $.ajax({
                url: "http://localhost:3020/getRTList/",
                type: "get",
                data: {
                    username: now_prfile_name.value,
                },
                success(resp) {
                    rating_list.value = resp.rating_list;
                    time_list.value = resp.time_list;
                }
            });
        }, 20);

        setTimeout(() => {
            var myChart = echarts.init(this.$refs.echartsContainer);
            myChart.setOption({
                xAxis: {
                    type: 'category',
                    data: time_list.value,
                },
                yAxis: {
                    type: 'value',
                    axisLine: {
                        show: true,
                        symbol: ['none', 'none'], // 轴线两端箭头，两个值，none表示没有箭头，arrow表示有箭头
                        symbolSize: [10, 10],
                    },
                },
                series: [
                    {
                        data: rating_list.value,
                        type: 'line',
                        label: {
                            show: true,
                        }
                    }
                ]
            });
        }, 100);
    },
    setup() {
        const store = useStore();
        let profile_user = ref(new Object());
        let pathName = ref("");
        let visitors = ref(0);

        let is_friend = ref(false); // 判断此人是否为自己的friend

        watch(() => store.state.user.profile_username, (newVal) => {
            // localStorage.setItem("profile_username", newVal);
            add_pv(newVal);
            setTimeout(() => {
                get_pv(newVal);
                specify_info(newVal);
                check_is_friend();
                location.reload();
            }, 20);
        });

        const add_friend = () => {
            if (store.state.user.token === null || store.state.user.token === '') {
                alert("Please login first!");
                router.push({
                    name: 'login_index',
                });
                return;
            }
            setTimeout(() => {
                $.ajax({
                    url: "http://localhost:3020/addFriend/",
                    type: "put",
                    data: {
                        "username": pathName.value,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success() {
                        is_friend.value = true;
                    },
                });
            }, 10);
        };

        const remove_friend = () => {
            if (store.state.user.token === null || store.state.user.token === '') {
                alert("Please login first!");
                router.push({
                    name: 'login_index',
                });
                return;
            }
            setTimeout(() => {
                $.ajax({
                    url: "http://localhost:3020/removeFriend/",
                    type: "put",
                    data: {
                        "username": pathName.value,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success() {
                        is_friend.value = false;
                    },
                });
            }, 10);
        };

        const check_is_friend = () => {
            if (store.state.user.token !== '') {
                $.ajax({
                    url: "http://localhost:3020/checkIsFriend/",
                    type: "get",
                    data: {
                        "username": pathName.value,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        if (resp === true) {
                            is_friend.value = true;
                        }
                    },
                });
            }
        };

        const specify_info = profile_name => {
            $.ajax({
                url: "http://localhost:3020/specifyInfo/",
                type: "get",
                data: {
                    "username": profile_name,
                },
                success(resp) {
                    console.log(resp);
                    profile_user.value = resp;
                }
            });
        };

        const add_pv = username => {
            if (username == store.state.user.username) {
                return;
            }
            $.ajax({
                url: "http://localhost:3020/addPV/",
                type: "put",
                data: {
                    "username": username,
                },
                success() {

                }
            });
        };

        const get_pv = username => {
            $.ajax({
                url: "http://localhost:3020/getPV/",
                type: "get",
                data: {
                    "username": username,
                },
                success(resp) {
                    visitors.value = resp;
                }
            });
        };

        onMounted(() => {
            for (let i = 9; i < window.location.pathname.length - 1; i ++ ) {
                pathName.value += window.location.pathname[i];
            }
            // let username = localStorage.getItem("profile_username");
            setTimeout(() => {
                add_pv(pathName.value);
                setTimeout(() => {
                    get_pv(pathName.value);
                    specify_info(pathName.value);
                    check_is_friend();
                }, 20);
            }, 20);
        });

        onUnmounted(() => {
            store.commit("updateProfileUsername", "");
            // localStorage.removeItem("profile_username");
        });

        return {
            profile_user,
            pathName,
            visitors,
            add_friend,
            is_friend,
            remove_friend,
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