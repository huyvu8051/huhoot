<template>
  <div class="organizer">
    <router-view v-if="connected"></router-view>
  </div>
</template>

<script>
import io from "socket.io-client"
import {useRoute} from "vue-router"

import {useAuthStore} from "@/stores/Auth";
import router from "@/router";
import {useOrganizerStore} from "@/stores/Organizer";
import {ref} from "vue";

export default {
  setup() {

    const authStore = useAuthStore();
    const organizerStore = useOrganizerStore();
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
      organizerStore.update(msg);
      connected.value = true;
    });
    socket.on("showCorrectAnswer", organizerStore.update);


    socket.on("publishQuestion", async msg => {
      await useOrganizerStore().update(msg);
      await router.push({name: "organizer.preview", params: route.params})
    })



    return {
      connected
    }

  }
}

</script>

<style scoped>
.organizer {
  height: 97vh;
  /*background-color: rgba(44, 62, 80, 0.38);*/
}

.control-button {
  height: 5%;
  width: calc(50% - 1vmin);
  /*background-color: rgba(62, 148, 37, 0.76);*/
  border: 1px solid black;
  border-radius: 5px;
  margin: 0.5vmin;
}
</style>