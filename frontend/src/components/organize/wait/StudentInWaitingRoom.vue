<template>
  <v-btn
    class="ma-1"
    :class="{ 'text-decoration-line-through': onHover }"
    style="background: #262766; color: #fff"
  >
    <b
      @mouseover="onHover = true"
      @mouseleave="onHover = false"
      @click="kickStudent"
    >
      {{ student.studentId + " " + student.studentFullName }}
    </b>
  </v-btn>
</template>

<script>
import HostOrganizeService from "@/services/HostOrganizeService";

export default {
  props: {
    student: Object,
  },
  data: () => {
    return {
      onHover: false,
    };
  },
  methods: {
    kickStudent() {
      console.log(
        "kick student id: ",
        this.student.studentId,
        this.$route.query.challengeId
      );
      this.$eventBus.$emit("kickStudent", [this.student.studentId]);
    },
  },
};
</script>

<style>
</style>