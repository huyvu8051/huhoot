<template>
  <v-data-table
    :headers="headers"
    :items="desserts"
    :options.sync="options"
    :server-items-length="totalDesserts"
    :loading="loading"
    item-key="studentId"
    :footer-props="{
      'items-per-page-options': [
        5,
        10,
        20,
        30,
        50,
        100,
        {
          text: 'All',
          value: 999,
        },
      ],
    }"
    disable-sort
    class="elevation-1"
  >
    <template v-slot:[`item.rank`]="{ index }">
      {{ index + 1 + options.itemsPerPage * (options.page - 1) }}
    </template>
    <template v-slot:[`item.score`]="{ item }">
      {{ Math.round(item.score) }}
    </template>
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Rank Table</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer> </v-spacer>
        <slot name="nextQuestion" />
      </v-toolbar>
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="getDataFromApi()"> Reset </v-btn>
    </template>
  </v-data-table>
</template>


<script>
import HostOrganizeService from "@/services/HostOrganizeService";

export default {
  components: {},
  data() {
    return {
      headers: [
        {
          text: "Rank",
          align: "start",
          sortable: false,
          value: "rank",
        },
        { text: "ID", value: "studentId" },
        { text: "Username", value: "username" },
        { text: "Fullname", value: "studentFullName" },

        { text: "Score", value: "score" },
      ],
      desserts: [],
      totalDesserts: 0,
      loading: true,
      options: {},
    };
  },

  watch: {
    options: {
      handler() {
        this.getDataFromApi();
      },
      deep: true,
    },
  },
  methods: {
    getDataFromApi() {
      this.loading = true;
      this.options.challengeId = this.$route.query.challengeId;
      HostOrganizeService.getTopStudent(this.options)
        .then((response) => {
          this.desserts = response.data.list;
          this.totalDesserts = response.data.totalElements;
        })
        .catch(console.log)
        .finally((this.loading = false));
    },
  },
};
</script>

<style>
</style>