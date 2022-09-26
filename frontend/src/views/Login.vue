<template>
  <h1>Login</h1>
  <form @submit.prevent="onSubmit">
    <label for="username"> Username </label>
    <input v-model="state.username" type="text" name="username" id="username">
    <label for="password">Password</label>
    <input v-model="state.password" type="text" name="password" id="password">
    <input type="submit" value="login">
  </form>
</template>

<script setup lang="ts">

import {reactive} from "vue";
import Api from "@/services/Api";


const state = reactive({
  username: "",
  password: ""
});


const onSubmit = () => {
  Api().post("/api/authentication", {username: state.username, password: state.password}).then(resp => {
    console.log(resp.data);
  }).catch(err => {
    console.log(err.response)
  })
}


</script>

<style scoped>

</style>