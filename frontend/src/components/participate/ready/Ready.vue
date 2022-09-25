<template>
  <h-flex-layout>
    <h1>{{ text }}</h1>
    <h5>{{ question.answerOption }} | {{ question.point }}</h5>
    <h-timeout :finish="doFinish" :time-limit="2500" />
  </h-flex-layout>
</template>

<script>
export default {
  data: () => {
    return {
      text: "Ready",
      question: {},
    };
  },

  created() {
    this.question = this.$store.state.question;

    this.text = "Question " + this.question.questionOrder;

    if (this.question.theLastQuestion) {
      this.text = "The last question";
    }
  },
  methods: {
    doFinish() {
      this.$router.push({
        name: "participate.preview",
        query: {
          challengeId: this.$route.query.challengeId,
          questionId: this.$route.query.questionId,
        },
      });
    },
  },
};
</script>

<style>
</style>
