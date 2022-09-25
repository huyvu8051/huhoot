<template>
  <div>
    <v-btn dark depressed color="cyan" class="btn-bot btn-left">
      <b class="btn-text">
        {{ $store.state.username }} : {{ parseInt($store.state.totalPoints) }}
      </b>
    </v-btn>

    <!-- <v-speed-dial
      class="btn-bot btn-left"
      v-model="fab"
      direction="top"
      open-on-hover
      transition="slide-y-reverse-transition"
    >
      <template v-slot:activator>
        <v-btn v-model="fab" color="blue darken-2" dark>
          <v-icon v-if="fab"> mdi-close </v-icon>
          <v-icon v-else> mdi-account-circle </v-icon>
        </v-btn>
      </template>
      <v-btn fab dark small color="green">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-btn fab dark small color="indigo">
        <v-icon>mdi-plus</v-icon>
      </v-btn>
      <v-btn fab dark small color="red">
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </v-speed-dial>
    {{ $store.state.username }} : {{ parseInt($store.state.totalPoints) }} -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      fab: false,
    };
  },
};
</script>
<style >
.v-btn--floating {
  position: relative;
}
</style>

