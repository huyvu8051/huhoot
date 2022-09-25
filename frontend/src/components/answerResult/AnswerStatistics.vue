<template>
  <v-progress-linear
    :value="percent"
    color="blue"
    height="40"
    class="my-5"
    rounded
  >
    <template v-slot:default="{ value }">
      <span class="d-inline-block text-truncate pl-3" style="max-width: 400px" >
        {{ answers.content }}
      </span>
      <v-spacer></v-spacer>
      <strong class="pr-3">
        {{ Math.ceil(value) }}%|
        {{ answers.numberOfStudentSelect }} students
      </strong>
    </template>
  </v-progress-linear>
</template>

<script>
export default {
  props: {
    totalStudentAnswer: Number,
    answers: Object,
  },
  data: () => ({}),
  created(){
    
  },
  computed: {
    percent() {
      return (
        (this.answers.numberOfStudentSelect / this.totalStudentAnswer) *
        100
      );
    },
  },
};
</script>

<style>
</style>