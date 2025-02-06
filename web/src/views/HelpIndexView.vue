<template>
    <!--<div class="card" style="width: 100%; margin-top: 20px; border: 1px solid black;">-->
        <div style="text-align: left; margin-left: 15px; margin-top: 10px; font-size: 20px; font-weight: 600; color: #3B5998;">
            Frequently Asked Questions
        </div>
        <div style="font-size: 14px; text-align: left; margin-left: 15px; margin-top: 10px;">
            This is the list of frequently asked questions concerning Codeforces work and answers to them.
        </div>
        <div class="flex-container" style="font-size: 14px; margin-top: 5px;">
            <div style="text-align: left; margin-left: 30px;">
                1.&nbsp;
            </div>
            <a :style="q_map.get('q1_style')" @mouseenter="mouseenter(1)" @mouseleave="mouseleave(1)" href="#q1">
                What is WPLCode? What kind of a site/resource is it?
            </a>
        </div>
        <div class="flex-container" style="font-size: 14px; margin-top: 5px;">
            <div style="text-align: left; margin-left: 30px;">
                2.&nbsp;
            </div>
            <a :style="q_map.get('q2_style')" @mouseenter="mouseenter(2)" @mouseleave="mouseleave(2)" href="#q2">
                What should I do to participate in contests? Is preliminary registration required?
            </a>
        </div>
        <div class="flex-container" style="font-size: 14px; margin-top: 5px;">
            <div style="text-align: left; margin-left: 30px;">
                3.&nbsp;
            </div>
            <a :style="q_map.get('q3_style')" @mouseenter="mouseenter(3)" @mouseleave="mouseleave(3)" href="#q3">
                What are the instructions and rules of the contests?
            </a>
        </div>
        
        <div style="margin-top: 10px;">
            <h4 style="text-align: left; margin-left: 15px;">Questions and answers:</h4>
        </div>
        <div class="flex-container" style="font-size: 14px; margin-top: 5px;">
            <div style="text-align: left; margin-left: 30px;">
                1.&nbsp;
            </div>
            <a :style="q_map.get('q1_style_answer')" @mouseenter="mouseenter_answer(1)" @mouseleave="mouseleave_answer(1)" name="q1">
                <b>
                    What is WPLCode? What kind of a site/resource is it?
                </b>
            </a>
        </div>
        <div style="text-align: left; margin-left: 30px; font-size: 14px;">
            WPLCode is a website dedicated to providing just an online evaluation system for computer programming enthusiasts. This website supports users to practice questions, participate in real-time competitions, and communicate with each other, aiming to provide users with a very good and comfortable programming environment and services.
        </div>
        <div class="flex-container" style="font-size: 14px; margin-top: 5px;">
            <div style="text-align: left; margin-left: 30px;">
                2.&nbsp;
            </div>
            <a :style="q_map.get('q2_style_answer')" @mouseenter="mouseenter_answer(2)" @mouseleave="mouseleave_answer(2)" name="q2">
                <b>
                    What should I do to participate in contests? Is preliminary registration required?
                </b>
            </a>
        </div>
        <div style="text-align: left; margin-left: 30px; font-size: 14px;">
            WPLCode regularly holds competitions. Participating in these competitions/activities is of course free, but the prerequisite is that users must first complete registration and login before registering and participating in the competitions.
        </div>
        <div class="flex-container" style="font-size: 14px; margin-top: 5px;">
            <div style="text-align: left; margin-left: 30px;">
                3.&nbsp;
            </div>
            <a :style="q_map.get('q3_style_answer')" @mouseenter="mouseenter_answer(3)" @mouseleave="mouseleave_answer(3)" name="q3">
                <b>
                    What are the instructions and rules of the contests?
                </b>
            </a>
        </div>
        <div style="text-align: left; margin-left: 30px; font-size: 14px;">
            WPLCode differs from other similar platforms such as Codeforces, Niuke.com, and Luogu. We will not hold a massive number of competitions. To ensure the quality of the competition questions, we will choose to hold only 1-2 competitions per month. The default competition format is ACM-ICPC. In the official competition, users can view their currently submitted code and running results, but cannot view other people's code. After each game, the system will reward and punish users with different Rating points based on their ranking. The higher the ranking, the more Rating points will be added, while the lower the ranking, a portion of Rating points may be deducted. Of course, all users can view the code of other users in all completed matches.
        </div>
        <div style="visibility: hidden; font-size: 10px;">
            &nbsp;
        </div>
        <div class="flex-container">
            <img src="../assets/icon4.png" style="text-align: left; float: left; margin-left: 15px;">
            <div style="text-align: left; font-size: 24px; font-weight: 600; color: #3B5998;">
                Comments
            </div>
            <a v-if="is_writing_comments === false" href="javascript:void(0)" @click="is_writing_comments = true;" style="color: #0000CC; margin-left: 398px; text-decoration: none;">
                Write comment?
            </a>
            <a v-else-if="is_writing_comments === true" href="javascript:void(0)" @click="is_writing_comments = false;" style="color: #0000CC; margin-left: 327px; text-decoration: none;">
                Give up Writing comment
            </a>
        </div>
        <div v-if="is_writing_comments === true" class="container">
            <div class="row">
                <div class="col-4">
                    <img :src="$store.state.user.photo" style="width: 80px; margin-top: 10px; margin-left: -15px;">
                    <div style="margin-left: -15px;">
                        {{ $store.state.user.username }}
                    </div>
                </div>
                <div class="col-8">
                    <!--<el-input
                        v-model="textarea"
                        maxlength="300"
                        style="width: 450px;"
                        placeholder="Please input"
                        show-word-limit
                        type="textarea"
                    />-->
                    <textarea class="form-control" v-model="content" aria-label="With textarea" placeholder="Leave a friendly comment" maxlength="300" show-word-limit style="height: 120px; width: 480px;"></textarea>
                    <button @click="post_comment" style="font-size: 12px; border: 1px solid black; margin-top: 10px; margin-left: 320px;">Post</button>
                    <button @click="content = '';" style="font-size: 12px; border: 1px solid black; margin-top: 10px; margin-left: 10px;">Clear</button>
                    <button @click="is_writing_comments = false;" style="font-size: 12px; border: 1px solid black; margin-top: 10px; margin-left: 10px;">Cancel</button>
                </div>
            </div>
        </div>
        <div class="container">
            <div v-for="comment in comments" :key="comment.id" class="row">
                <div class="col-4">
                    <img :src="comment.creatorPhoto" style="width: 80px; margin-top: 30px; margin-left: 100px;">
                    <div v-if="comment.creatorRating >= 3000" style="margin-left: 100px; font-size: 12px; color: #FF0000;">
                        {{ comment.creator }}
                    </div>
                    <div v-else-if="comment.creatorRating >= 2400 && comment.creatorRating < 3000" style="margin-left: 100px; font-size: 12px; color: #FF8C00;">
                        {{ comment.creator }}
                    </div>
                    <div v-else-if="comment.creatorRating >= 2000 && comment.creatorRating < 2400" style="margin-left: 100px; font-size: 12px; color: #FFD700;">
                        {{ comment.creator }}
                    </div>
                    <div v-else-if="comment.creatorRating >= 1700 && comment.creatorRating < 2000" style="margin-left: 100px; font-size: 12px; color: #25BB9B;">
                        {{ comment.creator }}
                    </div>
                    <div v-else-if="comment.creatorRating >= 1300 && comment.creatorRating < 1700" style="margin-left: 100px; font-size: 12px; color: #5EA1F4;">
                        {{ comment.creator }}
                    </div>
                    <div v-else-if="comment.creatorRating < 1300" style="margin-left: 100px; font-size: 12px; color: #C177E7;">
                        {{ comment.creator }}
                    </div>
                </div>
                <div class="col-6">
                    <div style="color: #B9B9B9; font-size: 12px; text-align: left; margin-top: 30px;">
                        {{ comment.createTime }}
                    </div>
                    <div style="margin-top: 10px; font-size: 12px; text-align: left;">
                        {{ comment.content }}
                    </div>
                </div>
                <div class="col-2" style="margin-top: 30px; text-align: right; font-size: 12px;">
                    <img src="../assets/icon5.png" style="width: 16px;">{{ comment.likes }}
                    <img src="../assets/icon5.png" style="width: 16px; transform: scaleY(-1);">{{ comment.dislikes }}
                </div>
            </div>
        </div>
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
        <div style="visibility: hidden; font-size: 10px;">
            &nbsp;
        </div>
    <!--</div>-->
</template>

<script>
import { onMounted, ref } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'

export default {
    setup() {
        let q_map = ref(new Map());
        const store = useStore();
        // 是否正在编辑
        let is_writing_comments = ref(false);
        // 正在编辑的评论内容
        let content = ref("");

        // 分页
        let comments = ref([]);
        let current_page = 1;
        let pages = ref([]);
        let total_comments = 0;

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_comments / 10));

            if (page >= 1 && page <= max_pages) {
                refresh_comments(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_comments / 10));
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


        onMounted(() => {
            for (let i = 1; i <= 20; i ++ ) {
                let str1 = "q_" + i.toString();
                let str2 = "q" + i.toString() + "_style";
                let str3 =  "q" + i.toString() + "_style_answer";
                q_map.value.set(str1, false);
                q_map.value.set(str2, "color: #4D87C7; text-decoration: none;");
                q_map.value.set(str3, "color: #4D87C7; text-decoration: none; font-weight: 600;");
            }
            // console.log(q_map.value);
        });

        const mouseenter = (qid) => {
            let str1 = "q_" + qid.toString();
            let str2 = "q" + qid.toString() + "_style";
            q_map.value.set(str1, true);
            q_map.value.set(str2, "color: white; text-decoration: none; background-color: rgb(162, 6, 6);");
        };

        const mouseleave = (qid) => {
            let str1 = "q_" + qid.toString();
            let str2 = "q" + qid.toString() + "_style";
            q_map.value.set(str1, false);
            q_map.value.set(str2, "color: #4D87C7; text-decoration: none;");
        };

        const mouseenter_answer = (qid) => {
            let str3 = "q" + qid.toString() + "_style_answer";
            q_map.value.set(str3, "color: white; text-decoration: none; font-weight: 600; background-color: rgb(162, 6, 6);");
        };

        const mouseleave_answer = (qid) => {
            let str3 = "q" + qid.toString() + "_style_answer";
            q_map.value.set(str3, "color: #4D87C7; text-decoration: none; font-weight: 600;");
        };

        const post_comment = () => {
            content.value = content.value.trim();
            if (content.value == "") {
                alert("Comment content cannot be empty!");
                return;
            }
            setTimeout(() => {
                $.ajax({
                    url: "http://localhost:3020/addHelpComment/",
                    type: "post",
                    data: {
                        "content": content.value,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success() {
                        content.value = "";
                        refresh_comments(current_page);
                        alert("Post a comment successfully!");
                    }
                })
            }, 20);
        };

        const refresh_comments = page => {
            current_page = page;
            $.ajax({
                url: "http://localhost:3020/getHelpComments/",
                type: "get",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    comments.value = resp.comments;
                    total_comments = resp.comment_counts;
                    console.log(comments.value);
                    update_pages();
                }
            })
        };

        refresh_comments(current_page);

        return {
            mouseenter,
            mouseleave,
            mouseenter_answer,
            mouseleave_answer,
            q_map,
            is_writing_comments,
            content,
            post_comment,
            comments,
            current_page,
            pages,
            click_page,
        }
    }
}
</script>

<style scoped>
.flex-container {
  display: flex;
  align-items: center; 
}
/*a:hover{
    background-color: rgb(162, 6, 6);
}*/
</style>