<template>
  <v-icon big class="mr-2" :color="condition ? 'green' : 'red'">
    {{ condition ? "mdi-checkbox-marked-circle" : "mdi-close-circle" }}
  </v-icon>
</template>

<script>
export default {
  props: {
    condition: Boolean,
  },
};
</script>

<style>
</style>