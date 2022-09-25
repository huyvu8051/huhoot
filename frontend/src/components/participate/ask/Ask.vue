<template>
  <div>
    <Question style="height: 43vh" />
    <Answer />
    <TimeCountDown style="height: 5vh" />
    <Answers style="height: 41vh" />
  </div>
</template>

<script>
import Answers from "@/components/Answers";
import Answer from "@/components/participate/ask/Answer";
import Question from "@/components/Question";
import TimeCountDown from "@/components/TimeCountDown";

import { mapState } from "vuex";

export default {
  components: {
    Question,
    TimeCountDown,
    Answers,
    Answer,
  },
  data() {
    return {
      autoOrgTimeout: null,
    };
  },
  computed: mapState({
    question: (state) => state.question,
    timeout: (state) => state.question.timeout,
  }),
  watch: {
    timeout() {
      this.setAutoOrgTimeout();
    },
  },
  mounted() {
    this.setAutoOrgTimeout();
  },
  beforeDestroy() {
    clearTimeout(this.autoOrgTimeout);
  },
  methods: {
    setAutoOrgTimeout() {
      clearTimeout(this.autoOrgTimeout);

      if (this.$store.state.question.timeout < new Date().getTime()) {
        this.autoOrgTimeout = setTimeout(() => {
          this.$store.state.publishNextQuestion(this.$route.query.challengeId);
        }, 4000);
      }
    },
  },
};
</script>

<style scoped>
</style>