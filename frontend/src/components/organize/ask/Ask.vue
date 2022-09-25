<template>
  <div>
    <Question style="height: 43vh" />
    <Skip />
    <TimeCountDown :finish="doFinish" style="height: 5vh" />
    <Answers disable style="height: 41vh" />
  </div>
</template>

<script>
import Question from "@/components/Question";
import Answers from "@/components/Answers";
import Skip from "@/components/organize/ask/Skip";
import TimeCountDown from "@/components/TimeCountDown";

import { mapState } from "vuex";

export default {
  components: {
    Question,
    TimeCountDown,
    Skip,
    Answers,
  },

  data() {
    return {
      autoOrgTimeout: null,
    };
  },
  computed: {
    ...mapState({
      question: (state) => state.question,
      timeout: (state) => state.question.timeout,
    }),
  },
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
          this.$store.state.getStatisticPage(this.$route.query.challengeId);
        }, 4000);
      }
    },
    doFinish() {
     
    },
  },
};
</script>

<style>
</style>