<template>
  <ChallengeRank>
    <template v-slot:nextQuestion>
      <NextQuestion />
    </template>
  </ChallengeRank>
</template>

<script>
import ChallengeRank from "@/components/challengeRank/ChallengeRank";

import NextQuestion from "@/components/challengeRank/NextQuestion";
export default {
  components: {
    ChallengeRank,
    NextQuestion,
  },
  data() {
    return {
      autoOrgTimeout: null,
    };
  },
  beforeDestroy() {
    clearTimeout(this.autoOrgTimeout);
  },
  mounted() {
    this.setAutoOrgTimeout();
  },
  methods: {
    setAutoOrgTimeout() {
      clearTimeout(this.autoOrgTimeout);

      this.autoOrgTimeout = setTimeout(() => {
        this.$store.state.getNextQuestionPage(this.$route.query.challengeId);
      }, 4000);
    },
  },
};
</script>

<style>
</style>