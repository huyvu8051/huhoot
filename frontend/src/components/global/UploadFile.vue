<template>
  <v-file-input
    name="image"
    accept="image/*"
    label="Tải ảnh lên"
    @change="uploadFile"
    show-size
    outlined
  >
  </v-file-input>
</template>

<script>

import HostManageService from "@/services/HostManageService";
export default {
  props: {
    value: String,
  },
  methods: {
    // =========================
    uploadFile(file) {
      if (file === null || file === undefined) return;

      var formData = new FormData();
      formData.append("file", file);

      HostManageService.upload(formData)
        .then((response) => {
          //console.log(response.data);
          this.$emit("input", response.data);
        })
        .catch(console.log);
    },
  },
};
</script>

<style>
</style>