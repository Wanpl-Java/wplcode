<template>
    <table class="table table-striped table-bordered" style="border: 1px solid black; border-collapse: collapse; margin-top: 20px;">
        <tbody style="font-size: 12px; font-weight: 600; text-align: left;">
            <tr>
                <th class="left" style="width: 50px; text-align: center;">Rank</th>
                <th class="" style="width: 550px; text-align: center;">&nbsp;Name</th>
                <th class="" style="text-align: center;">Member count</th>
            </tr>
            <tr v-for="city_count in city_counts" :key="city_count.rank">
                <td style="text-align: center;">{{ city_count.rank }}</td>
                <td style="text-align: center;">{{ city_count.cityName }}</td>
                <td style="text-align: center;">{{ city_count.count }}</td>
            </tr>
        </tbody>
    </table>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'

export default {
    setup() {
        const store = useStore();
        let city_counts = ref([]);

        const refresh_city_counts = () => {
            $.ajax({
                url: "http://localhost:3020/analyseCity/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    city_counts.value = resp;
                }
            });
        };

        refresh_city_counts();

        return {
            city_counts,
        }
    }
}
</script>

<style scoped>
</style>