<template>
  <router-view></router-view>
</template>

<script setup lang="ts">
import {io} from "socket.io-client";

const socket = io("http://localhost:8082")
socket.on("connected", (message) => {
  socket.send("messageEvent")
})

socket.on("abc", (msg) => {
  console.log(msg);
})
</script>

<style scoped>

</style>