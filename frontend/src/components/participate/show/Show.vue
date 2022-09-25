<template>
  <AnswerResult />
</template>

<script>
import AnswerResult from "@/components/answerResult/AnswerResult2";

export default {
  components: {
    AnswerResult,
  },
  data() {
    return {
      afterShow: {},
    };
  },

  created() {
    var pointsReceived = this.$store.state.pointsReceived;
    var currCombo = this.$store.state.currCombo;

    switch (pointsReceived) {
      case null:
        this.timeout();
        break;

      case 0:
        this.incorrect();
        break;

      default:
        this.correct(pointsReceived, currCombo);
        break;
    }

    this.afterShow = setTimeout(() => {
      this.$store.state.publishNextQuestion(this.$route.query.challengeId);
    }, 5000);
  },
  beforeDestroy() {
    clearTimeout(this.afterShow);
  },
  methods: {
    correct(point, currCombo) {
      this.$swal({
        icon: "success",
        title: "Good job!",
        text:
          "Your answer is correct! \n Points received +" +
          parseInt(point) +
          " combo: " +
          currCombo,
        timer: 3000,
      });
    },
    incorrect() {
      this.$swal({
        icon: "error",
        title: "Oops...",
        text: "Incorrect answer!",
        timer: 3000,
      });
    },
    timeout() {
      this.$swal({
        icon: "warning",
        title: "Timeout",
        text: "Better luck next time!",
        timer: 3000,
      });
    },
  },
};
</script>

<style>
</style>