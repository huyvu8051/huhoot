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
import Header from "@/components/student/Header";
import Navigation from "@/components/student/Navigation";
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