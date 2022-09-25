<template>
  <div>
    <StudentCard v-model="student" />
    <ListChallenge
      title="Danh sách cuộc thi tham gia"
      :options.sync="options"
      :desserts="desserts"
      :totalDesserts="totalDesserts"
    >
    </ListChallenge>
  </div>
</template>

<script>
import StudentCard from "@/components/StudentCard";
import ListChallenge from "@/components/ListChallenge";

import DetailsService from "@/services/DetailsService";
export default {
  components: {
    ListChallenge,
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
        },
        this.options
      );

      DetailsService.getStudentDetails(body).then((res) => {
        this.desserts = res.data.listChallenge.list;
        this.totalDesserts = res.data.listChallenge.totalElements;
        this.student = res.data.studentDetails;
      });
    },
  },
};
</script>

<style>
</style>