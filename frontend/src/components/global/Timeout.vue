<template>
</template>

<script>
export default {
  props: {
    finish: {
      type: Function,
      default: () => console.log("timeout finish"),
    },
    timeLimit: {
      type: Number,
      default: 3000,
    },
  },
  data() {
    return {
      timeout: null,
    };
  },
  created() {
    this.timeout = setTimeout(() => {
      this.finish();
    }, this.timeLimit);
  },
  beforeDestroy() {
    clearTimeout(this.timeout);
  },
};
</script>

<style>
</style>