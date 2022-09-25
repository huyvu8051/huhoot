<template>
  <h-flex-layout>
    <h1>{{ text }}</h1>
    <h5>{{ question.answerOption }} | {{  vietSubQuesPoint(question.point) }}</h5>
  </h-flex-layout>
</template>

<script>
import { vietSubQuesPoint } from '@/services/VietsubServices';


export default {
  components: {},
  data: () => {
    return {
      vietSubQuesPoint: vietSubQuesPoint,
      text: "Ready",
      timeout: {},
      question: {},
    };
  },
  created() {
    this.question = this.$store.state.question;

    this.text = "CÂU HỎI " + this.question.questionOrder;
    if (this.question.theLastQuestion) {
      this.text = "CÂU CUỐI CÙNG";
    }

    this.timeout = setTimeout(() => {
      //console.log("preview");
      this.$router.push({
        name: "organize.preview",
        query: {
          challengeId: this.$route.query.challengeId,
          questionId: this.$route.query.questionId,
        },
      });
    }, 2500);
  },
  beforeDestroy() {
    clearTimeout(this.timeout);
  },
};
</script>

<style>
</style>
