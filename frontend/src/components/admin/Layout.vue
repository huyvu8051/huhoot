<template>
  <v-main>
    <Navigation />
    <Header />
    <router-view />
    <!-- <Footer /> -->
  </v-main>
</template>

<script>
import Footer from "@/components/Footer";
import Header from "@/components/admin/Header";
import Navigation from "@/components/admin/Navigation";
export default {
  components: {
    Navigation,
    Header,
    Footer,
  },
};
</script>

<style>
</style>