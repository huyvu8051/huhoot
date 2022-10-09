<template>
  <router-view v-if="connected"></router-view>
</template>

<script>
import {useRoute} from "vue-router"

import router from "@/router";
import {useParticipantStore} from "@/stores/ParticipantStore";
import {ref} from "vue";
import connect from "@/services/Socket";

export default {
  setup() {

    const participantStore = useParticipantStore();
    const route = useRoute()

    const socket = connect(route.params.challengeId);

    const connected = ref(false)

    socket.on("connected", msg => {
      connected.value = true;
    });
    socket.on("showCorrectAnswer", participantStore.showCorrectAnswer);


    socket.on("publishQuestion", () => {
      router.push({name: "participant.preview", params: route.params})
    })


    return {
      connected
    }

  }
}

</script>

<style scoped>

</style>