<template>
  <v-chip class="mr-2" :v-if="condition" color="green" style="color: white">
    {{ content }}</v-chip
  >
</template>

<script>
export default {
  props: {
    condition: Boolean,
    content: String,
  },
};
</script>

<style>
</style>