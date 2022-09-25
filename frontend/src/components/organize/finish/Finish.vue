<template>
  <h-flex-layout>
      <h1>Challenge finish</h1>
      <ChallengeRank>
        <template v-slot:nextQuestion>
          <v-btn
            :to="{
              name: 'HOST',
            }"
            color="green"
            class="white--text"
          >
            <b>Return home</b>
          </v-btn>
        </template>
      </ChallengeRank>
   </h-flex-layout>
</template>

<script>
import ChallengeRank from "@/components/challengeRank/ChallengeRank";
export default {
  components: {
    ChallengeRank,
  },
};
</script>

<style>
</style>