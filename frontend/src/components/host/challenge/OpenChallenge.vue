<template>
  <v-dialog v-model="dialog" max-width="550px">
    <template v-slot:activator="{ on, attrs }">
      <v-icon medium class="mr-2" v-bind="attrs" v-on="on"> mdi-reload </v-icon>
    </template>
    <v-card>
      <v-card-title class="text-h5">
        Bạn có muốn mở cuộc thi này không
      </v-card-title>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="dialog = false">
          Đóng
        </v-btn>
        <v-btn color="blue darken-1" text @click="openChallenge()"> OK </v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import HostManageService from "@/services/HostManageService";
export default {
  props: {
    challengeId: Number,
  },
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    openChallenge() {
      HostManageService.openChallenge({
        challengeId: this.challengeId,
      })
        .then(
          this.$eventBus.$emit("nofication", {
            message: "Success!",
          })
        )
        .catch(console.log)
        .finally((this.dialog = false));
    },
  },
};
</script>

<style>
</style>