<template>
  <v-app-bar app flat hide-on-scroll>
    <v-app-bar-nav-icon @click="drawer"></v-app-bar-nav-icon>
    <router-link :to="{ name: 'ADMIN' }">
      <v-img
        class="mx-2 background-color:white"
        :src="require('@/assets/img/huhoot-logo-text.png')"
        max-height="150"
        max-width="150"
        contain
      ></v-img
    ></router-link>
    <v-spacer></v-spacer>

    <v-btn elevation="2" small class="primary white--text" :to="{name:'login'}">
      {{ $store.state.username }}
    </v-btn>
  </v-app-bar>
</template>

<script>
import AccountPanel from "@/components/host/AccountPanel";
export default {
  components: {
    AccountPanel: AccountPanel,
  },
  methods: {
    drawer() {
      this.$eventBus.$emit("drawer");
    },
  },
};
</script>

<style></style>
