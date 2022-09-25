<template>
  <v-navigation-drawer v-model="drawer" app>
    <v-list nav dense>
      <v-list-item-group
        v-model="group"
        active-class="deep-purple--text text--accent-4"
      >
        <v-list-item :to="{ name: 'ADMIN' }">
          <v-list-item-icon>
            <v-icon>groups</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Student</v-list-item-title>
        </v-list-item>

        <v-list-item :to="{ name: 'admin.host' }">
          <v-list-item-icon>
            <v-icon>supervisor_account</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Host</v-list-item-title>
        </v-list-item>
        <v-list-item :to="{ name: 'admin.account' }">
          <v-list-item-icon>
            <v-icon>mdi-account</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Account</v-list-item-title>
        </v-list-item>
        <v-list-item :to="{ name: 'admin.challenge' }">
          <v-list-item-icon>
            <v-icon>mdi-account</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Challenge</v-list-item-title>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  data: () => ({
    drawer: false,
    group: null,
  }),
  created() {
    this.$eventBus.$on("drawer", () => {
      this.drawer = !this.drawer;
    });
  },
};
</script>

<style>
</style>