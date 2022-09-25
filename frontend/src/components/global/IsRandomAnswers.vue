<template>
  <v-chip v-if="value" class="mr-2" color="green" dark> Trộn đáp án </v-chip>
  <v-chip v-else class="ma-2" color="primary"> Đáp án sắp xếp theo thứ tự </v-chip>
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