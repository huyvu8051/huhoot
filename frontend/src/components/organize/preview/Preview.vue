<template>
  <h-flex-layout>
    <h1>
      {{ question.questionContent }}
    </h1>
    <h-process-bar :finish="doFinish" />
  </h-flex-layout>
</template>

<script>
export default {
  data: () => {
    return {
      timeout: {},
      question: {},
    };
  },
  created() {
    this.question = this.$store.state.question;

    if (this.question.theLastQuestion) {
      this.text = "The last question";
    }
  },
  beforeDestroy() {
    clearTimeout(this.timeout);
  },
  methods: {
    doFinish() {
      this.$router.push({
        name: "organize.ask",
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