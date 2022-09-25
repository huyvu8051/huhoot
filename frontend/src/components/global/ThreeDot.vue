<template>
  <div><slot :text="text" /></div>
</template>

<script>
export default {
  props: {
    input: {
      type: String,
      default: "Three dot",
    },
  },

  data: () => {
    return {
      interval: {},
      text: "",
      counter: 0,
    };
  },
  created() {
    this.interval = setInterval(() => {
      this.text = this.input;
      this.counter = (this.counter + 1) % 4;
      for (var i = 0; i < this.counter; i++) {
        this.text += ".";
      }
    }, 500);
  },
  beforeDestroy() {
    clearInterval(this.interval);
  },
};
</script>

<style>
</style>