<template>
    <div class="card" style="width: 100%; margin-top: 20px; border: 1px solid black;">
        <div class="container">
            <div class="row">
                <div class="col-6">
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
                        <img src="../assets/icon8.png" style="width: 30px;">
                    </div>
                    <div class="flex-container" style="margin-top: 10px;">
                        <img src="../assets/icon9.png" style="width: 35px;">
                        <div style="margin-left: 5px;">
                            Contest rating:
                        </div>
                        <div style="color: #FF0000; margin-left: 5px; font-weight: 600;">
                            {{ profile_user.rating }}
                        </div>
                    </div>
                    <div class="flex-container" style="margin-top: 10px;">
                        <img src="../assets/icon2.png" style="width: 35px;">
                        <div style="margin-left: 5px;">
                            Friend of:
                        </div>
                        <div style="margin-left: 5px; font-weight: 600;">
                            {{ profile_user.friends }}&nbsp;users
                        </div>
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
import { onMounted, onUnmounted, ref, watch } from 'vue';
import { useStore } from 'vuex'
import $ from 'jquery'

export default {
    setup() {
        const store = useStore();
        let profile_user = ref(new Object());
        let pathName = ref("");

        watch(() => store.state.user.profile_username, (newVal) => {
            // localStorage.setItem("profile_username", newVal);
            specify_info(newVal);
        });

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

        onMounted(() => {
            for (let i = 9; i < window.location.pathname.length - 1; i ++ ) {
                pathName.value += window.location.pathname[i];
            }
            // let username = localStorage.getItem("profile_username");
            setTimeout(() => {
                specify_info(pathName.value);
            }, 20);
        });

        onUnmounted(() => {
            store.commit("updateProfileUsername", "");
            // localStorage.removeItem("profile_username");
        });

        return {
            profile_user,
            pathName,
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