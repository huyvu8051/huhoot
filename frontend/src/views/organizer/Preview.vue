<template>
  <PreviewExam @timeout="onTimeout"/>
</template>

<script>

import PreviewExam from "@/components/challenge/PreviewExam.vue"

import {useRoute} from "vue-router";
import router from "@/router";

export default {
  name: "Preview",
  components: {PreviewExam},
  setup() {

    const route = useRoute();

    const onTimeout = () => {
      router.push({name: "organizer.ask", params: route.params})
    }

    return {
      onTimeout
    }
  }
}
</script>
