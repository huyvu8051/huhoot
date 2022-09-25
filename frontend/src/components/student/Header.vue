<template>
  <v-app-bar app flat hide-on-scroll>
    <v-app-bar-nav-icon @click="drawer"></v-app-bar-nav-icon>
    <router-link :to="{ name: 'STUDENT' }">
      <v-img
        class="mx-2 background-color:white"
        :src="require('@/assets/img/logo_ithoot.png')"
        max-height="100"
        max-width="100"
        contain
      >
      </v-img>
    </router-link>
    <v-spacer></v-spacer>

    <div>
      Xin chào {{ $store.state.username }}
    </div>
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
