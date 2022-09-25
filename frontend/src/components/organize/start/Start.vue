<template>
  <h-flex-layout>
    <h1>{{ timeleft }}</h1>
  </h-flex-layout>
</template>

<script>
export default {
  data: () => {
    return {
      timeleft: "Chuẩn bị bắt đầu",
      timeleftCountDown: null,
    };
  },
  created() {
    this.countdown();
  },
  destroyed() {
    clearInterval(this.timeleftCountDown);
  },
  methods: {
    startChallenge() {
     // console.log("start challenge");
      this.$router.push({
        name: "organize.get",
        query: {
          challengeId: this.$route.query.challengeId,
        },
      });
    },
    countdown() {
      var timeleft = 3;

      var that = this;
      this.timeleftCountDown = setInterval(function () {
        if (timeleft <= 0) {
          that.startChallenge();
          clearInterval(that.timeleftCountDown);
        }
        that.timeleft = timeleft;
        timeleft--;
      }, 1000);
    },
  },
};
</script>

<style scoped>
</style>