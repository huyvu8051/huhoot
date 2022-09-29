<template>
  <h1>Login</h1>
  <form @submit.prevent="submit">
    <label for="username"> Username </label>
    <input v-model="authenticationReqDTO.username" type="text" name="username" id="username">
    <label for="password">Password</label>
    <input v-model="authenticationReqDTO.password" type="text" name="password" id="password">
    <input type="submit" value="login">
  </form>
</template>

<script setup lang="ts">
import {authenticate} from "@/services/Authentication";

const {submit, authenticationReqDTO} = authenticate((resp: any) => {
  console.log("dispatched: ", resp);
});


</script>
