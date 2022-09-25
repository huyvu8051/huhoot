<template>
  <v-progress-linear :value="process" color="#262766" height="25" style="color: white">
    <template v-slot:default>
      <strong>{{ parseInt(process) }}</strong>
    </template>
  </v-progress-linear>
</template>
<script>
export default {
  props: {
    finish: {
      type: Function,
      default: () => console.log("process bar finish"),
    },
    timeLimit: {
      type: Number,
      default: 2000,
    },
    refreshRate: {
      type: Number,
      default: 100,
    },
  },
  data() {
    return {
      interval: null,
      process: 0,
    };
  },

  created() {
    clearInterval(this.interval);

    this.interval = setInterval(() => {
      if (this.process >= 100) {
        this.finish();
        clearInterval(this.interval);
      }

      this.process += (this.refreshRate / this.timeLimit) * 100;
    }, this.refreshRate);
  },

  beforeDestroy() {
    clearInterval(this.interval);
  },
};
</script>