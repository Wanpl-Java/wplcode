<template>
    <table class="table table-striped table-bordered" style="border: 1px solid black; border-collapse: collapse; margin-top: 20px;">
        <thead>
            <tr style="font-size: 14px;">
                <th scope="col">#</th>
                <th scope="col">When</th>
                <th scope="col">Contest id</th>
                <th scope="col">Problem id</th>
                <th scope="col">Lang</th>
                <th scope="col">Verdict</th>
                <th scope="col">Time</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="submission in submissions" :key="submission.id" style="font-size: 14px;">
                <th scope="row">{{ submission.id }}</th>
                <td>{{ submission.submitTime }}</td>
                <td>{{ submission.contestId }}</td>
                <td>{{ submission.topicId }}</td>
                <td>Java</td>
                <td v-if="submission.result === 'Accept'" style="color: #25BB9B;">{{ submission.result }}</td>
                <td v-else-if="submission.result !== 'Accept'" style="color: #FF0000;">{{ submission.result }}</td>
                <td>{{ submission.runTime }}</td>
            </tr>
        </tbody>
    </table>
    <nav aria-label="Page navigation example">
        <ul class="pagination pagination-sm" style="float: right;">
            <li class="page-item" @click="click_page(-2)">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number" @click="click_page(page.number)">
                <a class="page-link" href="#">{{ page.number }}</a>
            </li>
            <li class="page-item" @click="click_page(-1)">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex';
import $ from 'jquery';

export default {
    setup() {
        const store = useStore();
        console.log(store.state.user.token);
        let submissions = ref([]);

        // submissions分页
        let current_page = 1;
        let pages = ref([]);
        let total_submissions = 0;

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_submissions / 15));
            if (page >= 1 && page <= max_pages) {
                refresh_submissions(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_submissions / 15));
            let new_pages = [];
            for (let i = current_page - 2; i <= current_page + 2; i ++ ) {
                if (i >= 1 && i <= max_pages) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        };

        const refresh_submissions = page => {
            current_page = page;
            $.ajax({
                url: "http://localhost:3020/getAllSubmissions/",
                type: "get",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    submissions.value = resp.submissions;
                    total_submissions = resp.submission_counts;
                    update_pages();
                }
            });
        };

        refresh_submissions(current_page);

        return {
            current_page,
            pages,
            click_page,
            submissions,
        }
    }
}
</script>

<style scoped>
</style>