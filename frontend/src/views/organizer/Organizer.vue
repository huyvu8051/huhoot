<template>
  <router-view></router-view>
  <button @click="click">click</button>
</template>

<script>
import io from "socket.io-client"
import {useRoute} from "vue-router"

import {useCustomerAuthInfoStore} from "@/stores/CustomerAuthInfo";

export default {
  setup() {

    const store = useCustomerAuthInfoStore();

    const route = useRoute()

   // console.log(route.params.id, useCustomerAuthInfoStore().jwt)


    const socket = io("http://localhost:8082", {
      query: {
        challengeId: route.params.id
      },
      transportOptions: {
        polling: {
          extraHeaders: {
            'Authorization': 'Bearer ' + store.jwt
          }
        }
      }
    })
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

    socket.on("abc", (data) => {
      console.log("Socket:", data)
    })


    function click() {
      socket.emit("messageEvent", "Chung ta cua hien tai")
    }

    return {
      click
    }

  }
}

</script>

<style scoped>

</style>