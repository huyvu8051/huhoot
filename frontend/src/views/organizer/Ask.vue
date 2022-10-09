<template>
  <ExamContent @timeout="onTimeout"></ExamContent>
  <button @click="result">Result</button>
</template>

<script>
import Api from "@/services/Api";
import {useRoute} from "vue-router";
import router from "@/router";
import ExamContent from "@/components/challenge/ExamContent.vue";
import {useChallengeStore} from "@/stores/ChallengeStore";

export default {
  name: "Ask",
  components: {
    ExamContent
  },
  setup() {

    const route = useRoute();
    const challengeStore = useChallengeStore();


    function onTimeout() {
      Api().get("/api/organizer/showCorrectAnswer", {params: route.params}).then(resp => challengeStore.saveParticipantsRank(resp.data))
    }


    function result() {
      router.push({name: "organizer.result", params: route.params})
    }

    return {
      onTimeout,
      result
    }

  }
}
</script>
