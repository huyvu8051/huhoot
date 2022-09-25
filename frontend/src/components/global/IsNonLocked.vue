<template>
  <v-chip v-if="value" class="mr-2" color="primary"> Không khoá </v-chip>
  <v-chip v-else class="ma-2" color="red"> Khoá </v-chip>
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