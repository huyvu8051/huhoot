<template>
  <router-view></router-view>
  <button>click</button>
</template>

<script>
import io from "socket.io-client"
import {useRoute} from "vue-router"

import {useCustomerAuthInfoStore} from "@/stores/CustomerAuthInfo";

export default {
  setup() {

    const route = useRoute()

    console.log(route.params.id,useCustomerAuthInfoStore().jwt)


    const socket = io("http://localhost:8082")
    socket
        .on("connected", (e) => {
          // console.log("connected");
        })
        .emit("registerHostSocket", {
          challengeId: route.params.id,
          token: "Bearer " + useCustomerAuthInfoStore().jwt,
        });
    socket.on("registerSuccess", (data) => {
      console.log("regist success")
    });

  }
}
</script>

<style scoped>

</style>