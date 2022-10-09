<template>
  <div class="organizer">
    <router-view v-if="connected"></router-view>
  </div>
</template>

<script>
import {useRoute} from "vue-router"
import connect from "@/services/Socket";
import {ref} from "vue";
import router from "@/router";

export default {
  setup() {

    const route = useRoute();
    const socket = connect(route.params.challengeId);
    const connected = ref(false);

    socket.on("connected", () => {
      connected.value = true;
    })

    socket.on("publishQuestion", () => {
      router.push({name: "organizer.preview", params: route.params})
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