<template>
  <v-btn color="green" class="btn-bot btn-right"  :disabled="unselectable" @click="submit">
    <b class="btn-text"> Submit </b>
  </v-btn>
</template>

<script>
import StudentPlayService from "@/services/StudentPlayService";
import { mapState } from "vuex";
export default {
  computed: mapState({
    answers: (state) => state.answers,
    unselectable(state) {
      return (
        state.studentSubmited || state.question.timeout < new Date().getTime()
      );
    },
  }),
  methods: {
    submit() {
      var selectedIds = this.$store.state.selectedAnswerIds;

      console.log(selectedIds);

      if (selectedIds.length == 0) {
        this.$eventBus.$emit("nofication", {
          message: "Select answer!",
          status: "error",
        });
        return;
      }

      StudentPlayService.sentAnswer({
        answerIds: selectedIds,
        questionId: this.$route.query.questionId,
        adminSocketId: this.$store.state.adminSocketId,
        comboToken: this.$store.state.comboToken,
        questionToken: this.$store.state.questionToken,
      }).then((response) => {
        this.$store.commit("saveStudentAnswerResponse", response.data);
      });

      this.$store.commit("studentSubmited");
    },
  },
};
</script>

<style >
</style>