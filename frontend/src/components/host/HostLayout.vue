<template>
  <v-main>
    <Navigation />
    <Header />
    <h-flex-layout>
      <router-view />
    </h-flex-layout>
    <!-- <Footer /> -->
  </v-main>
</template>

<script>
import Footer from "@/components/Footer";
import Header from "@/components/host/Header";
import Navigation from "@/components/host/Navigation";

export default {
  components: {
    Navigation,
    Header,
    Footer,
  },

  created() {
    //console.log(process.env.VUE_APP_BACKEND_URL);
  },
};
</script>

<style>
</style>