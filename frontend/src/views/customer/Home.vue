<template>
  <h1>
    Organizer home
  </h1>
  <h2>Challenges</h2>
  <table>
    <tr>
      <th>id</th>
      <th>title</th>
      <th>actions</th>
    </tr>
    <tr v-for="(challenge, index) in challenges" key="index">
      <td>{{ challenge.id }}</td>
      <td>{{ challenge.title }}</td>
      <td>
        <button @click="host(challenge.id)">Host</button>
      </td>
    </tr>
  </table>

  <h2>Participates</h2>
  <table>
    <tr>
      <th>id</th>
      <th>title</th>
      <th>actions</th>
    </tr>
    <tr v-for="(challenge, index) in participates" key="index">
      <td>{{ challenge.id }}</td>
      <td>{{ challenge.title }}</td>
      <td>
        <button @click="join(challenge.id)">Join</button>
      </td>
    </tr>
  </table>
</template>

<script setup >
import Api from "@/services/Api"
import {ref} from "vue";
import router from "@/router";


const challenges = ref([])

const participates = ref([])

Api().get("/api/organizer/challenge").then(resp => {
  challenges.value = resp.data.list
})

Api().get("/api/organizer/participate").then(resp => {
  console.log(resp.data)
  participates.value = resp.data.list
})

function host(challengeId) {
  router.push({name: "organizer.lobby", params: {challengeId: challengeId}})
}
function join(challengeId) {
  router.push({name: "participant.lobby", params: {challengeId: challengeId}})
}


</script>

<style scoped>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>