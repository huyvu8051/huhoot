<template>
  <v-icon big class="mr-2" @click="route"> {{ icon }} </v-icon>
</template>

<script>
export default {
  props: {
    icon: String,
    name: String,
    query: Object,
    params: Object
  },
  methods: {
    route() {
      this.$router.push({
        name: this.name,
        query: this.query,
        params: this.params
      });
    },
  },
};
</script>