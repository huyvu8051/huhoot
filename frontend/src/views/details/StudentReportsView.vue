<template>
  <div>
    <StudentCard v-model="student" />
    <StudentReports
      title="Thống kê đáp án của sinh viên"
      :options.sync="options"
      :desserts="desserts"
      :totalDesserts="totalDesserts"
      :totalPoint="totalPoint"
    />
  </div>
</template>

<script>
import StudentCard from "@/components/StudentCard";
import StudentReports from "@/components/StudentReports.vue";

import DetailsService from "@/services/DetailsService";

export default {
  components: {
    StudentReports,
    StudentCard,
  },
  data() {
    return {
      options: {},
      student: {
        createdBy: "0",
        createdDate: 0,
        fullName: "0",
        id: 0,
        isNonLocked: true,
        modifiedBy: "0",
        modifiedDate: 0,
        username: "0",
      },
      desserts: [],
      totalDesserts: 0,
      totalPoint: 0,
    };
  },
  watch: {
    options: {
      handler() {
        this.getDataFromApi();
      },
      deep: true,
    },
  },

  methods: {
    getDataFromApi() {
      var body = Object.assign(
        {
          username: this.$route.params.username,
          challengeId: this.$route.params.challengeId,
        },
        this.options
      );

      DetailsService.getStudentReports(body).then((res) => {
        var quests = res.data.questions.list;
        var studentAnswers = res.data.studentAnswerResults;

        this.desserts = quests.map((e) => {
          e.answers = studentAnswers.filter((a) => a.questionId === e.id);
          e.pointReceived = e.answers.reduce((a, b) => a + b.score, 0);
          e.isCorrect = e.answers.some((a) => a.isCorrect);
         var ans =  e.answers.find(a=>a.answerDate);
         if(ans){
           e.answerTime = (ans.answerDate - e.askDate) / 1000
         }else{
           e.answerTime = null;
         }
          return e;
        });

        this.totalDesserts = res.data.questions.totalElements;
        this.student = res.data.student;
        this.totalPoint = res.data.finalScore;

        console.log(this.desserts);
      });
    },
  },
};
</script>

<style>
</style>