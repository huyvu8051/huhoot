<template>
  <v-navigation-drawer v-model="drawer" app>
    <v-list nav dense>
      <v-list-item-group
        v-model="group"
        active-class="indigo darken-4 text--accent-4"
        color="white"
      >
        <v-list-item :to="{ name: 'HOST' }">
          <v-list-item-icon>
            <v-icon>mdi-home</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Trang chủ</v-list-item-title>
        </v-list-item>

        <v-list-item :to="{ name: 'host.account' }">
          <v-list-item-icon>
            <v-icon>mdi-account</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Tài Khoản</v-list-item-title>
        </v-list-item>

        <v-list-item @click="logout">
          <v-list-item-icon>
            <v-icon>mdi-logout</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Đăng Xuất</v-list-item-title>
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
  methods: {
    logout() {
      this.$router.push("/");
    },
  },
};
</script>

<style>
</style>