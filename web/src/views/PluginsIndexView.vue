<template>
    <div style="display: flex; justify-content: center; align-items: center; margin-top: 20px;">
        <p>WPLCode Commercialization Plan</p>
    </div>
    <div style="margin-top: 0px; font-size: 18px; color: white;">
        WPLCode Business Edition - an enterprise level OJ community platform built on an open source foundation. In addition to providing rich features, it also implements excellent English to Chinese translation plugins based on deep learning and natural language processing, aiming to provide Chinese users with a better product experience. The commercial version provides long-term technical support, secondary invoice support, etc. (can be invoiced)
    </div>
    <div class="container" style="margin-top: 20px;">
        <div class="row">
            <div class="col-6">
                <div class="card" style="color: white; border-radius: 5px; border: 1px solid white; background-color: black;">
                    <div style="text-align: left; margin-left: 12px; font-size: 24px; font-weight: 600; margin-top: 5px;">
                        Open source version
                    </div>
                    <div style="margin-top: 10px; font-size: 18px; text-align: left; margin-left: 12px;">
                        The version in Github open source repository is available
                    </div>
                    <div style="font-size: 18px; text-align: left; margin-left: 12px;">
                        for personal learning and use. The open source version
                    </div>
                    <div style="font-size: 18px; text-align: left; margin-left: 12px;">
                        retains basic and perfect version
                    </div>
                    <div style="font-size: 24px; font-weight: 600; text-align: center; margin-top: 5px;">
                        Current price: 0$<img src="../assets/icon24.png" style="width: 50px; margin-bottom: 10px;">
                    </div>
                    <div style="justify-content: center; margin-top: 10px;">
                        <el-button @click="start_to_use_open_source_version();" plain style="width: 95%; font-weight: 600; font-size: 18px;">Start to use</el-button>
                        <el-dialog v-model="open_source_version_DialogVisble" width="360" center style="margin-top: 200px;">
                            <div style="font-weight: 600; text-align: center; font-size: 22px;">
                                English→Chinese
                            </div>
                            <div style="text-align: center; margin-top: 10px; font-size: 20px; font-family: '隶书';">
                                English
                            </div>
                            <div class="flex-container" style="justify-content: center; margin-top: 5px;">
                                <el-input v-model="userInput" style="width: 300px" placeholder="Please input(English)..." />
                            </div>
                            <hr>
                            <div style="text-align: center; font-size: 20px; font-family: '隶书';">
                                Chinese
                            </div>
                            <div class="flex-container" style="justify-content: center; margin-top: 5px;">
                                <el-input v-model="chinese_output" style="width: 300px" />
                            </div>
                            <div class="flex-container" style="justify-content: center; margin-top: 15px;">
                                <el-button @click="open_source_version_translate();" type="primary">{{ translate_btn_content }}</el-button>
                            </div>
                        </el-dialog>
                    </div>
                    <hr>
                    <div class="flex-container" style="justify-content: left; margin-left: 20px;">
                        <img src="../assets/icon26.png" style="width: 20px;">
                        <div style="color: white; margin-left: 5px;">
                            Basic translation skills
                        </div>
                    </div>
                    <div class="flex-container" style="justify-content: left; margin-left: 20px;">
                        <img src="../assets/icon26.png" style="width: 20px;">
                        <div style="color: white; margin-left: 5px;">
                            2.x Translation speed
                        </div>
                    </div>
                    <div class="flex-container" style="justify-content: left; margin-left: 20px;">
                        <img src="../assets/icon26.png" style="width: 20px;">
                        <div style="color: white; margin-left: 5px;">
                            Tool plugin capability
                        </div>
                    </div>
                    <div class="flex-container" style="justify-content: left; margin-left: 20px;">
                        <img src="../assets/icon27.png" style="width: 20px;">
                        <div style="color: white; margin-left: 5px;">
                            Long term stability and accuracy
                        </div>
                    </div>
                    <div style="font-size: 10px;">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div class="col-6">
                <div class="card" style="color: white; border-radius: 5px; border: 1px solid white; background-color: black;">
                    <div style="text-align: left; margin-left: 12px; font-size: 24px; font-weight: 600; margin-top: 5px;">
                        WPLCode commercial version
                    </div>
                    <div style="margin-top: 10px; font-size: 18px; text-align: left; margin-left: 12px;">
                        Design and optimization of the advanced AI algorithms
                    </div>
                    <div style="font-size: 18px; text-align: left; margin-left: 12px;">
                        based on the Deep Learning and the Natural Language
                    </div>
                    <div style="font-size: 18px; text-align: left; margin-left: 12px;">
                        Processing.Improved the accuracy of translation results
                    </div>
                    <div style="font-size: 24px; font-weight: 600; text-align: center; margin-top: 5px;">
                        Current price: 20$<img src="../assets/icon24.png" style="width: 50px; margin-bottom: 10px;">
                    </div>
                    <div style="justify-content: center; margin-top: 10px;">
                        <el-button @click="start_to_use_wplcode_commercial_version();" plain style="width: 95%; font-weight: 600; font-size: 18px;">Start to use</el-button>
                    </div>
                    <hr>
                    <div class="flex-container" style="justify-content: left; margin-left: 20px;">
                        <img src="../assets/icon26.png" style="width: 20px;">
                        <div style="color: white; margin-left: 5px;">
                            Advanced translation skills
                        </div>
                    </div>
                    <div class="flex-container" style="justify-content: left; margin-left: 20px;">
                        <img src="../assets/icon26.png" style="width: 20px;">
                        <div style="color: white; margin-left: 5px;">
                            5.x Translation speed
                        </div>
                    </div>
                    <div class="flex-container" style="justify-content: left; margin-left: 20px;">
                        <img src="../assets/icon26.png" style="width: 20px;">
                        <div style="color: white; margin-left: 5px;">
                            Support tool customization and expansion
                        </div>
                    </div>
                    <div class="flex-container" style="justify-content: left; margin-left: 20px;">
                        <img src="../assets/icon26.png" style="width: 20px;">
                        <div style="color: white; margin-left: 5px;">
                            Long term stability and accuracy
                        </div>
                    </div>
                    <div style="font-size: 10px;">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div class="col-12" style="margin-top: 20px;">
                <div class="card" style="color: white; border-radius: 5px; border: 1px solid white; background-color: black;">
                    <div class="container">
                        <div class="row">
                            <div class="col-4">
                                <p style="font-size: 24px; margin-top: 50px;">Enterprise</p>
                                <p style="font-size: 24px;">customization</p>
                            </div>
                            <div class="col-2">
                                <img src="../assets/icon28.png" style="width: 160px; margin-top: 15px; border: 1px solid white;">
                            </div>
                            <div class="col-6">
                                <p style="font-size: 24px; margin-top: 50px;">
                                    Product consultation hotline:
                                </p>
                                <div style="font-size: 24px; color: white; font-family: '隶书'; font-weight: 600;">
                                    18052502782
                                </div>
                            </div>
                            <div style="font-size: 10px;">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--<div class="col-4">
                <div class="card" style="color: white; border-radius: 5px; border: 1px solid white; background-color: black;">
                    <div style="text-align: left; margin-left: 12px; font-size: 12px; font-weight: 600; margin-top: 5px;">
                        Open source version
                    </div>
                    <div style="margin-top: 10px; font-size: 12px; text-align: left; margin-left: 12px;">
                        The version in Github open source
                    </div>
                    <div style="font-size: 12px; text-align: left; margin-left: 12px;">
                        repository is available for personal
                    </div>
                    <div style="font-size: 12px; text-align: left; margin-left: 12px;">
                        learning and use. The open source
                    </div>
                    <div style="font-size: 12px; text-align: left; margin-left: 12px;">
                        version retains basic and thorough version
                    </div>
                    <div style="font-size: 16px; font-weight: 600; text-align: left; margin-left: 12px; margin-top: 5px;">
                        Current price: 20$<img src="../assets/icon24.png" style="width: 30px; margin-bottom: 10px;">
                    </div>
                    <div style="justify-content: center; margin-top: 5px;">
                        <el-button plain style="width: 90%; font-weight: 600;">Start to use</el-button>
                    </div>
                    <div style="font-size: 10px;">
                        &nbsp;
                    </div>
                </div>
            </div>
            <div class="col-4">

            </div>
            <div class="col-4">

            </div>-->
            <div class="col-12" style="font-size: 100px;">
                &nbsp;
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios"
import { ref } from 'vue'

export default {
    watch: {
        open_source_version_DialogVisble(New) {
            if (New === false) {
                setTimeout(() => {
                    this.userInput = "";
                    this.chinese_output = "";                    
                }, 50);
            }
        }
    },
    setup() {
        let django_info = 'hello';
        let userInput = ref(""); // 接收用户的英文输入
        let chinese_output = ref(""); // 返回翻译后的中文

        let open_source_version_DialogVisble = ref(false);
        let wplcode_commercial_version_DialogVisble = ref(false);

        let translate_btn_content = ref("Translate");

        const open_source_version_translate = () => {
            translate_btn_content.value = "is thinking...";
            let whole_url = 'http://127.0.0.1:3050/translate/' + userInput.value;
            axios.get(whole_url)
            .then(res => {
                chinese_output.value = res.data;
                translate_btn_content.value = "Translate";
                // console.log(res);
            })
            .catch(err => {
                alert("ERROR!");
                console.log(err); 
                translate_btn_content.value = "Translate";
            })
        };

        const wplcode_commercial_version_translate = () => {

        };

        // 使用开源版本
        const start_to_use_open_source_version = () => {
            open_source_version_DialogVisble.value = true;
            // test_django_info();
            // alert("start to use open source version!");
        };

        // 使用WPLCode商业版
        const start_to_use_wplcode_commercial_version = () => {
            alert("start_to_use_wplcode_commercial_version");
        };

        return {
            django_info,
            open_source_version_translate,
            wplcode_commercial_version_translate,
            start_to_use_open_source_version,
            start_to_use_wplcode_commercial_version,
            open_source_version_DialogVisble,
            wplcode_commercial_version_DialogVisble,
            userInput,
            chinese_output,
            translate_btn_content,
        }
    }
}
</script>

<style scoped>
.flex-container {
  display: flex;
  align-items: center; 
}
/*p {
    color: white;
    font-size: 32px;
    font-weight: 600;
    font-family: '隶书';
    background-image: 
    linear-gradient(90deg,
    #673ab7,
    #e91e63);
    background-clip: text;
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-image: 
    linear-gradient(90deg,
    rgb(255, 167, 69),
    rgb(254, 134, 159),
    rgb(239, 122, 200),
    rgb(160, 131, 237),
    rgb(67, 174, 255),
    rgb(160, 131, 237),
    rgb(239, 122, 200),
    rgb(254, 134, 159),
    rgb(255, 167, 69));
    background-size: 200%;
}

p:hover {
    animation: streamer 2s linear infinite;
}

@keyframes streamer {
    0% {
        background-position: 0%;
    }
    100% {
        background-position: 200%;
    }
}*/

p{
  font-size: 36px;
  font-weight: 700;
  font-family: '思源黑体 CN';
  background-image:
    linear-gradient(90deg,
    #673ab7,
    #e91e63);
  background-clip: text;
  -webkit-background-clip: text;
  /* 这次的这句和以往不一样 */
  /* 以往是考虑低版本浏览器 */
  /* 这次就连最新版Chrome和Safari都需要 */
  /* 注意是background-clip: text;需要 */
  /* 如果是： content-box啊之类的就没这么苛刻的 */
  /* -webkit-text-fill-color: transparent; */
  color: transparent;
  /* 这个属性的-webkit-前缀也比较特殊 */
  /* 寻常的-webkit-表示的是Chrome和Safari的私有属性 */
  /* 但是它却声明了所有的（除了那位浏览器） */
  background-image:
  linear-gradient(90deg, 
    rgb(255, 167, 69),
    rgb(254, 134, 159),
    rgb(239, 122, 200),
    rgb(160, 131, 237),
    rgb(67, 174, 255),
    rgb(160, 131, 237),
    rgb(239, 122, 200),
    rgb(254, 134, 159),
    rgb(255, 167, 69));
  background-size: 200%;
}

p{
  -webkit-animation: streamer 2s linear infinite;
          animation: streamer 2s linear infinite;
}

@-webkit-keyframes streamer {
  0%{
    background-position: 0%;
  }
  100%{
    background-position: 200%;
  }
}

@keyframes streamer {
  0%{
    background-position: 0%;
  }
  100%{
    background-position: 200%;
  }
}
</style>