<template>
  <v-main v-if="socket && socket.connected" app class="pa-1">
    <Header />
    <router-view :socket="socket" style="height: 90vh" />
  </v-main>
</template>

<script>
import Header from "@/components/participate/ChallengeHeader";

import AutoOrganizeService from "@/services/AutoOrganizeService";
import io from "socket.io-client";

export default {
  components: {
    Header,
  },
  data() {
    return {
      socket: null,
    };
  },
  watch: {
    $route() {
      this.refreshRoute();
    },
  },
  created() {
    this.socket = this.connectSocket();
    this.registerEvent(this.socket);

    this.$store.commit("disableAutoOrganize");
  },
  beforeDestroy() {
    this.removeSocketListener(this.socket);
    this.socket.disconnect();
  },
  methods: {
    refreshRoute() {
      var askRoute = [
        "participate.ready",
        "participate.preview",
        "participate.ask",
      ];

      if (
        this.$store.state.question &&
        this.$store.state.question.timeout > new Date().getTime() &&
        !askRoute.includes(this.$route.name)
      ) {
        this.$router
          .push({
            name: "participate.preview",
            query: {
              challengeId: this.$route.query.challengeId,
              questionId: this.$store.state.question.id,
            },
          })
          .catch((err) => err);
      }
    },
    connectSocket() {
      var socket = io.connect(process.env.BACKEND_SOCKET_URL);

      socket
        .on("connected", () => null)
        .emit("clientConnectRequest", {
          challengeId: this.$route.query.challengeId,
          token: this.$store.state.token,
        });
      socket.on("registerSuccess", (data) => {
        this.$store.commit("saveChallengeData", data.currentExam);
        this.$store.commit("setTotalPoints", data.totalPoints);
        this.refreshRoute();
      });

      socket.on("joinError", () => {
        this.$error("Cann't connect to room!!!");

        this.$router
          .push({
            name: "STUDENT",
          })
          .catch((err) => err);
      });
      return socket;
    },
    registerEvent(socket) {
      socket.on("startChallenge", () => {
        this.$router
          .push({
            name: "participate.start",
            query: {
              challengeId: this.$route.query.challengeId,
            },
          })
          .catch((err) => err);
      });

      socket.on("publishQuestion", (data) => {
        this.$store.commit("publishExam", data);
        this.$router
          .push({
            name: "participate.ready",
            query: {
              challengeId: this.$route.query.challengeId,
              questionId: this.$store.state.question.id,
            },
          })
          .catch((err) => err);
      });

      // =============== show correct answer =================
      socket.on("showCorrectAnswer", (data) => {
        this.$store.commit("showCorrectAnswer", data);
        this.$store.commit("calculatePointReceived", data);
        this.$router
          .push({
            name: "participate.ask",
            query: {
              challengeId: this.$route.query.challengeId,
              questionId: this.$store.state.question.id,
            },
          })
          .catch((err) => err);
      });

      socket.on("endChallenge", () => {
        this.$router
          .push({
            name: "participate.finish",
            query: {
              challengeId: this.$route.query.challengeId,
            },
          })
          .catch((err) => err);
      });

      socket.on("kickStudent", (data) => {
        this.$error("You're out!!!");
        this.$router
          .push({
            name: "STUDENT",
          })
          .catch((err) => err);
      });

      socket.on("enableAutoOrganize", (data) => {
        this.$success("enableAutoOrganize");
        this.$store.commit("enableAutoOrganize", data);
        this.refreshRoute();
        if (
          this.$store.state.question &&
          this.$store.state.question.timeout < new Date().getTime()
        ) {
          this.$store.commit("pnq");
        }
      });
      socket.on("disableAutoOrganize", (data) => {
        this.$success("disableAutoOrganize");
        this.$store.commit("disableAutoOrganize", data);
      });
    },
    removeSocketListener(socket) {
      socket.off("connected");
      socket.off("registerSuccess");
      socket.off("joinError");
      socket.off("startChallenge");
      socket.off("publishQuestion");
      socket.off("showCorrectAnswer");
      socket.off("endChallenge");
      socket.off("kickStudent");
      socket.off("enableAutoOrganize");
      socket.off("disableAutoOrganize");
    },
  },
};
</script>

<style>
</style>