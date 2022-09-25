<template>
  <v-btn color="cyan" @click="showTopStudent">
    <b> Rank Table </b>
  </v-btn>
</template>

<script>
import HostOrganizeService from "@/services/HostOrganizeService";
export default {
  data() {
    return {
      question: {},
    };
  },
  created() {
    this.question = this.$store.state.question;
  },

  methods: {
    showTopStudent() {
      if (this.question.theLastQuestion) {
        HostOrganizeService.endChallenge({
          challengeId: this.$route.query.challengeId,
        });
      } else {
        this.$router.push({
          name: "organize.rank",
          query: {
            challengeId: this.$route.query.challengeId,
          },
        });
      }
    },
  },
};
</script>

<style>
</style>