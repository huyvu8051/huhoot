<template>
  <v-chip v-if="value" class="mr-2" color="green" dark> Phát ngẫu nhiên đề thi </v-chip>
  <v-chip v-else class="ma-2" color="primary"> Phát đề thi theo thứ tự </v-chip>
</template>

<script>
export default {
  props: {
    value: Boolean,
  },
  data() {
    return {};
  },
};
</script>

<style>
</style>