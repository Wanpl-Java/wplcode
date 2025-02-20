<template>
    <div style="margin-top: 10px; font-size: 24px; font-weight: 600;">
        Gobang AI PK
    </div>
    <div ref="parent" class="gobanggamemap">
        <canvas ref="canvas" tabindex="0"></canvas>
    </div>
</template>

<script>
import { onMounted, ref } from 'vue'
import { GobangGameMap } from '@/assets/scripts/GobangGameMap';

export default {
    setup() {
        let parent = ref(null);
        let canvas = ref(null);

        onMounted(() => {
            new GobangGameMap(canvas.value.getContext('2d'), parent.value);
        });

        return {
            parent,
            canvas,
        }

    }
}
</script>

<style scoped>
div.gobanggamemap {
    text-align: center;
    width: 75vw;
    height: 85vh;
    margin: 10px auto;
    display: flex;
    justify-content: left;
    align-items: left;
    margin-left: 75px;
}
</style>