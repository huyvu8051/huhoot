<template>
  <v-btn color="cyan" @click="nextQuestion"><b>Next question</b></v-btn>
</template>

<script>
export default {
  methods: {
    nextQuestion() {
      this.$router.push({
        name: "organize.get",
        query: {
          challengeId: this.$route.query.challengeId,
        },
      });
    },
  },
};
</script>

<style>
</style>