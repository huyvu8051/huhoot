<template >
  <div v-html="before + formatDate2(date) + after" />
</template>


<script>
export default {
  props: {
    date: Number,
    after: {
      type: String,
      default: "",
    },
    before: {
      type: String,
      default: "",
    },
  },
  methods: {
    formatDate(s) {
      var date = new Date(s);
      return date.toLocaleString();
    },

    formatDate2(s) {
      var today = new Date(s);
      var year = today.getFullYear();
      var mes = today.getMonth() + 1;
      var dia = today.getDate();
      var fecha = dia + "-" + mes + "-" + year;
      return fecha;
    },
  },
};
</script>

<style>
</style>