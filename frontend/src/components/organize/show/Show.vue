<template>
  <AnswerResult>
    <template v-slot:topLeft>
      <StatisticBtn />
    </template>
  </AnswerResult>
</template>

<script>
import AnswerResult from "@/components/answerResult/AnswerResult2";
import ShowTopStudent from "@/components/organize/show/ShowTopStudent";

import StatisticBtn from "@/components/organize/show/StatisticBtn";
export default {
  components: {
    AnswerResult,
    ShowTopStudent,

    StatisticBtn,
  },
  data() {
    return {
      afterShow: {},
    };
  },
  created() {
    this.$swal({
      icon: "success",
      title: "Timeup!",
      text: "There no time at all",
      timer: 3000,
    });

    this.afterShow = setTimeout(() => {
      this.$store.state.publishNextQuestion(this.$route.query.challengeId);
    }, 5000);
  },
  beforeDestroy() {
    clearTimeout(this.afterShow);
  },
};
</script>

<style>
</style>