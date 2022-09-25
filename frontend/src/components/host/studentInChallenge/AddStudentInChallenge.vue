<template>
  <v-dialog v-model="dialog" max-width="550px">
    <template v-slot:activator="{ on, attrs }">
      <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
        Thêm tất cả 
      </v-btn>
    </template>
    <v-card>
      <v-card-title class="text-h5">
       Bạn có chắc thêm hết tất cả không?
      </v-card-title>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
        <v-btn color="blue darken-1" text @click="save"> OK </v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
//

import HostManageService from "@/services/HostManageService";
export default {
  components: {},
  props: {
    selected: Array,
  },
  data: () => ({
    dialog: false,
  }),
  methods: {
    close() {
      this.dialog = false;
    },
    save() {
      // console.log(this.selected);

      if (this.selected.length <= 0) {
        this.$eventBus.$emit("nofication", {
          message: "Please select as least 1 student",
          status: "warning",
        });
        this.dialog = false;
        return;
      }

      // console.log(this.selected);

      const ids = this.selected.map((x) => x.id);

      HostManageService.addStudentInChallenge({
        challengeId: this.$route.query.challengeId,
        studentIds: ids,
      })
        .then(this.$eventBus.$emit("refreshListStudentInChallenge"))
        .catch(console.log)
        .finally((this.dialog = false));
    },
  },
};
</script>

<style>
</style>