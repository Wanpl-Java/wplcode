<template>
  <div v-if="route_name === 'plugins_index'" style="background-color: black;">
    <HomePage />
  </div>
  <div v-else>
    <HomePage />
  </div>
  <!--<TestIndexView />-->
</template>

<script>
import HomePage from './components/HomePage.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import { useRoute } from 'vue-router'
import { computed } from 'vue'

// import TestIndexView from './utils/TestIndexView.vue'

export default {
  components: {
    HomePage,
    // TestIndexView,
  },
  setup() {
    const route = useRoute();
    let route_name = computed(() => route.name);

    return {
      route_name,
    }
  }
}
</script>

<style scoped>
</style>
