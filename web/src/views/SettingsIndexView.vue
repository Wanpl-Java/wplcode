<template>
    <div class="card" style="width: 100%; margin-top: 20px; border: 1px solid black;">
        <div style="float: left; text-align: left; margin-left: 15px; margin-top: 10px; font-size: 20px; font-weight: 600;">
            {{ $store.state.user.username }}
        </div>
        <div class="container" style="margin-top: 10px;">
            <div class="row">
                <div class="col-6">
                    <div style="text-align: left; margin-left: 60px;">
                        Username<input v-model="username" type="text" style="height: 30px; margin-left: 20px; text-indent: 7px;">
                    </div>
                    <div style="text-align: left; margin-left: 65px; margin-top: 20px;">
                        Password<input type="password" disabled placeholder="************" style="height: 30px; margin-left: 20px; text-indent: 7px;">
                    </div>
                    <a v-if="is_entering_password === false" href="/passwordRecovery/" @mouseenter="is_entering_password = true;" style="color: #0000CC; font-size: 12px; margin-left: 230px; text-decoration: none;">Update password?</a>
                    <a v-else-if="is_entering_password === true" href="/passwordRecovery/" @mouseleave="is_entering_password = false;" style="color: brown; font-size: 12px; margin-left: 230px; text-decoration: none;">Update password?</a>
                    <div class="flex-container" style="text-align: left; margin-left: 70px;">
                        <div style="margin-top: 10px;">
                            Province
                        </div>
                        <el-select
                            v-model="province"
                            style="height: 20px; width: 184.8px; margin-left: 20px;">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </div>
                    <div style="text-align: left; margin-left: 105px; margin-top: 25px;">
                        City<input v-model="city" type="text" style="height: 30px; margin-left: 20px; text-indent: 7px;">
                    </div>
                </div>
                <div class="col-6">
                    <img :src="$store.state.user.photo" style="width: 160px; height: 160px; border: 1px solid black;">
                    <div style="margin-top: 10px;">
                        <a href="javascript:void(0)" style="color: #0000CC; font-size: 12px; margin-top: 20px; text-decoration: none;">Change photo</a>
                    </div>
                </div>
                <button type="button" @click="save_changes" style="width: 125px; height: 26px; font-size: 12px; border: 1px solid black; margin-top: 25px; margin-left: 300px;">Save changes</button>
            </div>
        </div>
        <div style="visibility: hidden; font-size: 10px;">
            &nbsp;
        </div>
    </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'

export default {
    setup() {
        const store = useStore();
        let username = ref("");
        let province = ref("");
        let city = ref("");
        let is_entering_password = ref(false);

        const save_changes = () => {
            // console.log("username: " + username.value);
            // console.log("province: " + province.value);
            // console.log("city: " + city.value);
            $.ajax({
                url: "http://localhost:3020/updateSettings/",
                type: "put",
                data: {
                    "username": username.value,
                    "province": province.value,
                    "city": city.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        alert("Save changes successfully!");
                        location.reload();
                    } else {
                        alert(resp.error_message);
                    }
                }
            });
        };

        const options = [
            {
                value: '江苏',
                label: '江苏',
            },
            {
                value: '湖南',
                label: '湖南',
            },
            {
                value: '四川',
                label: '四川',
            },
            {
                value: '重庆',
                label: '重庆',
            },
            {
                value: '山东',
                label: '山东',
            },
            {
                value: '山西',
                label: '山西',
            },
            {
                value: '广东',
                label: '广东',
            },
            {
                value: '广西',
                label: '广西',
            },
            {
                value: '云南',
                label: '云南',
            },
            {
                value: '江西',
                label: '江西',
            },
            {
                value: '北京',
                label: '北京',
            },
            {
                value: '天津',
                label: '天津',
            },
            {
                value: '上海',
                label: '上海',
            },
            {
                value: '内蒙古',
                label: '内蒙古',
            },
            {
                value: '湖北',
                label: '湖北',
            },
            {
                value: '河北',
                label: '河北',
            },
            {
                value: '河南',
                label: '河南',
            },
            {
                value: '陕西',
                label: '陕西',
            },
            {
                value: '宁夏',
                label: '宁夏',
            },
            {
                value: '甘肃',
                label: '甘肃',
            },
            {
                value: '福建',
                label: '福建',
            },
            {
                value: '浙江',
                label: '浙江',
            },
            {
                value: '青海',
                label: '青海',
            },
            {
                value: '西藏',
                label: '西藏',
            },
            {
                value: '新疆',
                label: '新疆',
            },
            {
                value: '台湾',
                label: '台湾',
            },
            {
                value: '香港',
                label: '香港',
            },
            {
                value: '澳门',
                label: '澳门',
            },
            {
                value: '辽宁',
                label: '辽宁',
            },
            {
                value: '吉林',
                label: '吉林',
            },
            {
                value: '黑龙江',
                label: '黑龙江',
            },
            {
                value: '安徽',
                label: '安徽',
            },
            {
                value: '海南',
                label: '海南',
            },
            {
                value: '贵州',
                label: '贵州',
            },
        ]

        onMounted(() => {
            setTimeout(() => {
                username.value = store.state.user.username;
                province.value = store.state.user.province;
                city.value = store.state.user.city;
            }, 20);
        });

        return {
            username,
            province,
            city,
            is_entering_password,
            options,
            save_changes,
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