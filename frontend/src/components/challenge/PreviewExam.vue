<template>
  <h1>Preview</h1>
  <h2>Content: {{ question.content }}</h2>
  <h3>Order: {{ question.questionOrder }}</h3>
</template>

<script>
import {useChallengeStore} from "@/stores/ChallengeStore";
import {onUnmounted} from "vue";

export default {
  emits:['timeout'],
  setup(props, ctx) {

    const question = useChallengeStore().question;

    const timeout = setTimeout(()=>ctx.emit("timeout"), 2000);

    onUnmounted(() => {
      clearTimeout(timeout)
    })

    return {
      question
    }
  }
}
</script>
