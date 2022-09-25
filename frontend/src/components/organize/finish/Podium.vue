<template>
  <div> 
    <v-row>
      <v-col class="pa-5"> </v-col>
      <v-col class="pa-5"> top 1 </v-col>
      <v-col class="pa-5"> </v-col>
    </v-row>
    <v-row>
      <v-col class="pa-5"> top 2 </v-col>
      <v-col class="pa-5 red"> </v-col>
      <v-col class="pa-5"> </v-col>
    </v-row>
    <v-row>
      <v-col class="pa-5 yellow"> </v-col>
      <v-col class="pa-5 red"> </v-col>
      <v-col class="pa-5"> top 3 </v-col>
    </v-row>
    <v-row>
      <v-col class="pa-5 yellow"> </v-col>
      <v-col class="pa-5 red"> </v-col>
      <v-col class="pa-5 blue"> </v-col>
    </v-row>
    <v-row>
      <v-col class="pa-5 yellow"> </v-col>
      <v-col class="pa-5 red"> </v-col>
      <v-col class="pa-5 blue"> </v-col>
    </v-row>
    
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      desserts: [
        {
          id: 1,
          username: "user1",
          fullname: "username1",
        },
        {
          id: 2,
          username: "user2",
          fullname: "username2",
        },
        {
          id: 3,
          username: "user3",
          fullname: "username3",
        },
      ],
      fireworks: {},
    };
  },
  mounted() {
    
  },
 
};
</script>

<style>
</style>