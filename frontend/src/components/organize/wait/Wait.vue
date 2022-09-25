<template>
  <v-main style="padding: 0">
    <WaitingRoomHeader :NumOfStudent="students.length" :challenge="challenge" />
    <v-container fluid class="wait-bg">
      <!-- <div class="wait-img">let's play a challenge</div> -->
      <h-image-wrapper :src="$store.state.challenge.coverImage" class="wait-img" height="50vh"/>
      <h1 style="text-align:center">{{$store.state.challenge.title}}</h1>
      <v-layout align-center justify-center>
        <v-row>
          <v-col>
            <StudentInWaitingRoom
              :student="item"
              v-for="(item, index) in students"
              :key="index"
            />
          </v-col>
        </v-row>
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import WaitingRoomHeader from "@/components/organize/wait/WaitingRoomHeader";
import StudentInWaitingRoom from "@/components/organize/wait/StudentInWaitingRoom";

import HostOrganizeService from "@/services/HostOrganizeService";

export default {
  components: {
    WaitingRoomHeader,
    StudentInWaitingRoom,
  },
  props: {
    challenge: Object,
  },
  data: () => {
    return {
      students: [],
      updateStudentsLoginInterval: {},
    };
  },
  created() {
    this.updateStudentsLogin();
    this.registEventBus();
  },
  beforeDestroy() {
    clearInterval(this.updateStudentsLoginInterval);
    this.$eventBus.$off("kickStudent");
  },
  methods: {
    registEventBus() {
      this.$eventBus.$on("kickStudent", (data) => {
        HostOrganizeService.kickStudent({
          studentIds: data,
          challengeId: this.$route.query.challengeId,
        });

        this.students = this.students.filter(
          (e) => !data.includes(e.studentId)
        );

        // console.log(this.students);
      });
    },

    updateStudentsLogin() {
      this.getStudentsLogin(); // run first time

      this.updateStudentsLoginInterval = setInterval(
        this.getStudentsLogin,
        5000
      );
    },
    getStudentsLogin() {
      HostOrganizeService.getStudentsOnline({
        challengeId: this.$route.query.challengeId,
      }).then((response) => {
        //console.log(response);
        this.students = response.data;
      });
    },
  },
};
</script>

<style>
@font-face {
  font-family: "Smooch";
  src: url("../../../font/Smooch-Regular.ttf");
}
.wait-bg {
  padding: 0;
}
/* .wait-img {
  font-family: "Smooch", cursive;
  max-width: 100%;
  height: 100px;
  font-size: 4rem;
} */

@media screen and (min-width: 1092px) {
  .wait-img {
    height: 70vh;
  }
}
</style>