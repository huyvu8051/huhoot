<template>
  <v-app-bar
    app
    flat
    hide-on-scroll
    style="background-color: #262766; color: #fff"
  >
    <b>Số lượng tham gia: {{ NumOfStudent }}</b>

    <v-spacer>
    </v-spacer>

    <v-btn
      style="background-color: #fff; color: #262766"
      class="mr-2"
      :to="{
        name: 'HOST',
      }"
    >
      <v-icon>mdi-keyboard-backspace</v-icon>
    </v-btn>
    <!-- <v-btn class="red white--text mr-2" @click="lockChallenge"> {{lockText}} </v-btn> -->

    <v-btn class="green white--text" @click="startChallenge">
      <v-icon>mdi-play</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
import HostOrganizeService from "@/services/HostOrganizeService";
export default {
  props: {
    NumOfStudent: Number,
  },
  data: () => ({
    lockText: "Lock",
  }),
  created() {
    this.$eventBus.$on("updateScore", (data) => {
      this.score = data;
    });
  },
  components: {},
  methods: {
    startChallenge() {
      HostOrganizeService.startChallenge({
        challengeId: this.$route.query.challengeId,
      });
    },
    lockChallenge() {
      console.log("lock room");
    },
  },
};
</script>

<style>
</style>
