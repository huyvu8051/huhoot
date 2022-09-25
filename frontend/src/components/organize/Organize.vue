<template>
  <v-main v-if="socket && socket.connected" app class="pa-1">
    <router-view :socket="socket" style="height: 90vh" />
    <v-switch
      v-model="autoOrganize"
      style="width: 20px"
      class="autoOrgBtn"
      @change="changeChallengeAutoOrganize"
    ></v-switch>
  </v-main>
</template>

<script>
import io from "socket.io-client";
import AutoOrganizeService from "@/services/AutoOrganizeService";

import HostOrganizeService from "@/services/HostOrganizeService";

import { mapState } from "vuex";

export default {
  components: {},
  data() {
    return {
      socket: null,
      value: "recent",
    };
  },
  computed: {
    autoOrganize: {
      get() {
        return this.$store.state.challenge.autoOrganize;
      },
      set(value) {},
    },
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
    changeChallengeAutoOrganize(value) {
      if (value) {
        AutoOrganizeService.enableAutoOrganize(this.$route.query.challengeId);
      } else {
        AutoOrganizeService.disableAutoOrganize(this.$route.query.challengeId);
      }

      this.$store.commit("setAutoOrganize", value);
    },
    refreshRoute() {
      var askRoute = ["organize.ready", "organize.preview", "organize.ask"];

      if (
        this.$store.state.question &&
        this.$store.state.question.timeout > new Date().getTime() &&
        !askRoute.includes(this.$route.name)
      ) {
        this.$router
          .push({
            name: "organize.preview",
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
        .on("connected", (e) => {
         // console.log("connected");
        })
        .emit("registerHostSocket", {
          challengeId: this.$route.query.challengeId,
          token: this.$store.state.token,
        });

      socket.on("registerSuccess", (data) => {
        this.$store.commit("saveChallengeData", data.currentExam);
        this.refreshRoute();
      });
      socket.on("updateChallengeStatus", (data) => {
        console.log("update challenge status");
        this.$store.commit("saveChallengeData", data.currentExam);
        this.refreshRoute();
      });

      socket.on("joinError", (data) => {
        this.$eventBus.$emit("nofication", {
          message: "Cann't connect to room!!!",
          status: "error",
        });
        this.$router
          .push({
            name: "HOST",
          })
          .catch((err) => err);
      });

      return socket;
    },
    registerEvent(socket) {
      socket.on("startChallenge", () => {
        this.$router
          .push({
            name: "organize.start",
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
            name: "organize.ready",
            query: {
              challengeId: this.$route.query.challengeId,
              questionId: this.$store.state.question.id,
            },
          })
          .catch((err) => err);
      });

      socket.on("showCorrectAnswer", (data) => {
        this.$store.commit("showCorrectAnswer", data);
        this.$swal({
          icon: "info",
          title: "Hết giờ!",
          text: "Hết thời gian trả lời!",
          timer: 3000,
        });
        this.$router
          .push({
            name: "organize.ask",
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
            name: "organize.finish",
            query: {
              challengeId: this.$route.query.challengeId,
            },
          })
          .catch((err) => err);
      });
      socket.on("enableAutoOrganize", (data) => {
        this.$success("enableAutoOrganize");
        this.$store.commit("enableAutoOrganize", data);
        this.$store.commit("disableOrganizeGetCorrectAnswer");
        this.refreshRoute();
        if (!this.$store.state.question) {
          HostOrganizeService.startChallenge({
            challengeId: this.$route.query.challengeId,
          });
        }
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
        this.$store.commit("enableOrganizeGetCorrectAnswer");
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
      socket.off("enableAutoOrganize");
      socket.off("disableAutoOrganize");
    },
  },
};
</script>

<style>
.autoOrgBtn{
  position: absolute;
  bottom: 0px;
  left: 20px;
}
</style>