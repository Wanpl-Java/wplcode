<template>
    <table class="table table-bordered" style="border: 1px solid black; border-collapse: collapse; margin-top: 20px;">
        <tbody style="font-size: 12px; font-weight: 600; text-align: center; align-items: center;">
            <tr>
                <th class="left" style="width: 50px; text-align: center; background-color: rgb(192, 192, 192);">Rank</th>
                <th style="width: 360px; text-align: center; background-color: rgb(192, 192, 192);">Name</th>
                <th style="text-align: center; background-color: rgb(192, 192, 192);">Pass</th>
                <th style="text-align: center; background-color: rgb(192, 192, 192);">Penalty</th>
                <th v-if="topic_len >= 1" style="text-align: center; background-color: rgb(192, 192, 192);">A</th>
                <th v-if="topic_len >= 2" style="text-align: center; background-color: rgb(192, 192, 192);">B</th>
                <th v-if="topic_len >= 3" style="text-align: center; background-color: rgb(192, 192, 192);">C</th>
                <th v-if="topic_len >= 4" style="text-align: center; background-color: rgb(192, 192, 192);">D</th>
                <th v-if="topic_len >= 5" style="text-align: center; background-color: rgb(192, 192, 192);">E</th>
            </tr>
            <tr v-for="result in results" :key="result.id">
                <td style="text-align: center;">{{ result.rank }}</td>
                <td @click="to_profile(result.username);" v-if="result.rating >= 3000" style="color: #FF0000; cursor: pointer;">{{ result.username }}</td>
                <td @click="to_profile(result.username);" v-else-if="result.rating >= 2400 && result.rating < 3000" style="color: #FF8C00; cursor: pointer;">{{ result.username }}</td>
                <td @click="to_profile(result.username);" v-else-if="result.rating >= 2000 && result.rating < 2400" style="color: #FFD700; cursor: pointer;">{{ result.username }}</td>
                <td @click="to_profile(result.username);" v-else-if="result.rating >= 1700 && result.rating < 2000" style="color: #25BB9B; cursor: pointer;">{{ result.username }}</td>
                <td @click="to_profile(result.username);" v-else-if="result.rating >= 1300 && result.rating < 1700" style="color: #5EA1F4; cursor: pointer;">{{ result.username }}</td>
                <td @click="to_profile(result.username);" v-else-if="result.rating < 1300" style="color: #C177E7; cursor: pointer;">{{ result.username }}</td>
                <td style="text-align: center;">
                    {{ result.passCounts }}
                    <img v-if="result.passCounts === topic_len" src="../assets/icon12.png" style="width: 18px; margin-bottom: 2px;">
                </td>
                <td style="text-align: center;">{{ result.penalty }}</td>
                <td v-if="topic_len >= 1 && result.results.length < 1">

                </td>
                <td v-else-if="topic_len >= 1 && result.username === quickests[0]" style="text-align: center; background-color: #BFE6DE;">
                    <div v-if="result.results[0] !== -1">
                        {{ result.results[0] }}
                        <a v-if="result.failedResults[0] > 0">
                            (-{{ result.failedResults[0] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[0] === -1">
                        (-{{ result.failedResults[0] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 1 && result.results[0] !== -1" style="text-align: center; background-color: #EFF9F7;">
                    <div v-if="result.results[0] !== -1">
                        {{ result.results[0] }}
                        <a v-if="result.failedResults[0] > 0">
                            (-{{ result.failedResults[0] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[0] === -1">
                        (-{{ result.failedResults[0] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 1 && result.results[0] === -1" style="text-align: center; background-color: #FFECEC;">
                    <div v-if="result.results[0] !== -1">
                        {{ result.results[0] }}
                        <a v-if="result.failedResults[0] > 0">
                            (-{{ result.failedResults[0] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[0] === -1">
                        (-{{ result.failedResults[0] }})
                    </div>
                </td>
                <td v-if="topic_len >= 2 && result.results.length < 2">

                </td>
                <td v-else-if="topic_len >= 2 && result.username === quickests[1]" style="text-align: center; background-color: #BFE6DE;">
                    <div v-if="result.results[1] !== -1">
                        {{ result.results[1] }}
                        <a v-if="result.failedResults[1] > 0">
                            (-{{ result.failedResults[1] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[1] === -1">
                        (-{{ result.failedResults[1] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 2 && result.results[1] !== -1" style="text-align: center; background-color: #EFF9F7;">
                    <div v-if="result.results[1] !== -1">
                        {{ result.results[1] }}
                        <a v-if="result.failedResults[1] > 0">
                            (-{{ result.failedResults[1] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[1] === -1">
                        (-{{ result.failedResults[1] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 2 && result.results[1] === -1" style="text-align: center; background-color: #FFECEC;">
                    <div v-if="result.results[1] !== -1">
                        {{ result.results[1] }}
                        <a v-if="result.failedResults[1] > 0">
                            (-{{ result.failedResults[1] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[1] === -1">
                        (-{{ result.failedResults[1] }})
                    </div>
                </td>
                <td v-if="topic_len >= 3 && result.results.length < 3">

                </td>
                <td v-else-if="topic_len >= 3 && result.username === quickests[2]" style="text-align: center; background-color: #BFE6DE;">
                    <div v-if="result.results[2] !== -1">
                        {{ result.results[2] }}
                        <a v-if="result.failedResults[2] > 0">
                            (-{{ result.failedResults[2] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[2] === -1">
                        (-{{ result.failedResults[2] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 3 && result.results[2] !== -1" style="text-align: center; background-color: #EFF9F7;">
                    <div v-if="result.results[2] !== -1">
                        {{ result.results[2] }}
                        <a v-if="result.failedResults[2] > 0">
                            (-{{ result.failedResults[2] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[2] === -1">
                        (-{{ result.failedResults[2] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 3 && result.results[2] === -1" style="text-align: center; background-color: #FFECEC;">
                    <div v-if="result.results[2] !== -1">
                        {{ result.results[2] }}
                        <a v-if="result.failedResults[2] > 0">
                            (-{{ result.failedResults[2] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[2] === -1">
                        (-{{ result.failedResults[2] }})
                    </div>
                </td>
                <td v-if="topic_len >= 4 && result.results.length < 4">

                </td>
                <td v-else-if="topic_len >= 4 && result.username === quickests[3]" style="text-align: center; background-color: #BFE6DE;">
                    <div v-if="result.results[3] !== -1">
                        {{ result.results[3] }}
                        <a v-if="result.failedResults[3] > 0">
                            (-{{ result.failedResults[3] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[3] === -1">
                        (-{{ result.failedResults[3] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 4 && result.results[3] !== -1" style="text-align: center; background-color: #EFF9F7;">
                    <div v-if="result.results[3] !== -1">
                        {{ result.results[3] }}
                        <a v-if="result.failedResults[3] > 0">
                            (-{{ result.failedResults[3] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[3] === -1">
                        (-{{ result.failedResults[3] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 4 && result.results[3] === -1" style="text-align: center; background-color: #FFECEC;">
                    <div v-if="result.results[3] !== -1">
                        {{ result.results[3] }}
                        <a v-if="result.failedResults[3] > 0">
                            (-{{ result.failedResults[3] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[3] === -1">
                        (-{{ result.failedResults[3] }})
                    </div>
                </td>
                <td v-if="topic_len >= 5 && result.results.length < 5">

                </td>
                <td v-else-if="topic_len >= 5 && result.username === quickests[4]" style="text-align: center; background-color: #BFE6DE;">
                    <div v-if="result.results[4] !== -1">
                        {{ result.results[4] }}
                        <a v-if="result.failedResults[4] > 0">
                            (-{{ result.failedResults[4] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[4] === -1">
                        (-{{ result.failedResults[4] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 5 && result.results[4] !== -1" style="text-align: center; background-color: #EFF9F7;">
                    <div v-if="result.results[4] !== -1">
                        {{ result.results[4] }}
                        <a v-if="result.failedResults[4] > 0">
                            (-{{ result.failedResults[4] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[4] === -1">
                        (-{{ result.failedResults[4] }})
                    </div>
                </td>
                <td v-else-if="topic_len >= 5 && result.results[4] === -1" style="text-align: center; background-color: #FFECEC;">
                    <div v-if="result.results[4] !== -1">
                        {{ result.results[4] }}
                        <a v-if="result.failedResults[4] > 0">
                            (-{{ result.failedResults[4] }})
                        </a>
                    </div>
                    <div v-else-if="result.results[4] === -1">
                        (-{{ result.failedResults[4] }})
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script>
import { onMounted, ref } from 'vue';
import $ from 'jquery'
import router from '../router/index';
import { useStore } from 'vuex';

export default {
    setup() {
        let contestId = "";
        let results = ref([]);
        // 当前比赛题目数量
        let topic_len = ref(0);
        // 每道题最快解题选手
        let quickests = ref(["", "", "", "", ""]);

        const store = useStore();

        const to_profile = username => {
            store.commit("updateProfileUsername", username);
            setTimeout(() => {
                router.push({ 
                    name: 'profile_index',
                    params: {
                        username,
                    }
                });
            }, 20);
        };

        onMounted(() => {
            for (let i = 0; i < window.location.pathname.length; i ++ ) {
                if (window.location.pathname[i] >= '0' && window.location.pathname[i] <= '9') {
                    contestId += window.location.pathname[i];
                }
            }
            contestId = parseInt(contestId);
            refresh_results();
        });

        const refresh_results = () => {
            $.ajax({
                url: "http://localhost:3020/getContestResult/",
                type: "get",
                data: {
                    contestId,
                },
                success(resp) {
                    results.value = resp.contestResult;
                    quickests.value = resp.quickest;
                    if (resp.contestResult[0].results !== null) {
                        topic_len.value = resp.contestResult[0].results.length;
                    }
                    // console.log(resp);
                    // console.log(results.value);
                    // console.log(quickests.value);
                }
            });
        };

        return {
            contestId,
            results,
            topic_len,
            quickests,
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
</style>