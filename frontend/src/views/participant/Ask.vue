<template>
  <ExamContent></ExamContent>
  <button @click="confirm">Confirm</button>
</template>

<script>

import {useChallengeStore} from "@/stores/ChallengeStore";
import Api from "@/services/Api";
import ExamContent from "@/components/challenge/ExamContent.vue";

export default {
  name: "Ask",
  components: {
    ExamContent
  },
  setup() {

    const challengeStore = useChallengeStore();

    function confirm() {

      const selected = challengeStore.answers.filter(e => e.isSelected);

      Api().post("/api/participant/", {
        questionId: challengeStore.question.id,
        selectedIds: selected
      })
      challengeStore.submit();
    }

    return {
      confirm,
      challengeStore
    }

  }
}
</script>
