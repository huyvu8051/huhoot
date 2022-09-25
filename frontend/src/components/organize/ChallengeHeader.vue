<template>
  <v-app-bar app flat hide-on-scroll class="cyan">
    <v-toolbar-title class="black--text">
      <span>{{ studentAnswered }} student answered</span>
    </v-toolbar-title>
    <v-spacer> </v-spacer>
    <v-btn class="deep-purple white--text" text @click="skipQuestion">
      Skip
    </v-btn>
  </v-app-bar>
</template>

<script>
export default {
  props: {
    studentAnswered: Number,
  },
  methods: {
    skipQuestion() {
       this.$eventBus.$emit("skipQuestion");
    },
  },
};
</script>

<style></style>
