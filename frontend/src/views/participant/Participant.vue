<template>
  <router-view></router-view>
</template>

<script>
import io from "socket.io-client"
import {useRoute} from "vue-router"

import {useAuthStore} from "@/stores/Auth";
import router from "@/router";
import {useParticipantStore} from "@/stores/Participant";
import {ref} from "vue";

export default {
  setup() {

    const authStore = useAuthStore();
    const participantStore = useParticipantStore();
    const route = useRoute()

    const socket = io("http://localhost:8082", {
      query: {
        challengeId: route.params.challengeId
      },
      transportOptions: {
        polling: {
          extraHeaders: {
            'Authorization': 'Bearer ' + authStore.jwt
          }
        }
      }
    });

    const connected = ref(false)

    socket.on("connected", msg => {
      participantStore.connected(msg);
      connected.value = true;
    });
    socket.on("showCorrectAnswer", participantStore.showCorrectAnswer);


    socket.on("publishQuestion", async msg => {
      await participantStore.publishQuestion(msg);
      await router.push({name: "participant.preview", params: route.params})
    })


    return {
      connected
    }

  }
}

</script>

<style scoped>

</style>