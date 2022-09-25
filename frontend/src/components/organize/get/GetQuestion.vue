<template>
  <h-flex-layout>
    <h1>Get question...</h1>
  </h-flex-layout>
</template>

<script>
import HostOrganizeService from "@/services/HostOrganizeService";

export default {
  props: {
    socket: Object,
  },
  created() {
    if (this.socket.connected) {
      HostOrganizeService.publishNextQuestion({
        challengeId: this.$route.query.challengeId,
      });
    } else {
      console.log("socket not connected!");
      this.$router
        .push({
          name: "organize.start",
          query: {
            challengeId: this.$route.query.challengeId,
          },
        })
        .catch((err) => err);
    }
  },
};
</script>

<style>
</style>
