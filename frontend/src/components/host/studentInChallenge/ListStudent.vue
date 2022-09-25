<template>
  <v-data-table
    v-model="selected"
    :headers="headers"
    :items="desserts"
    item-key="id"
    show-select
    :options.sync="options"
    :server-items-length="totalDesserts"
    :loading="loading"
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
          value: 9999,
        },
      ],
    }"
    disable-sort
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>List Challenge</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        {{ selected.length }} Selected
        <v-spacer></v-spacer>
        <slot :selected="selected" name="top"/>
      </v-toolbar>
    </template>
    <template v-slot:[`item.createdDate`]="{ item }">
      <h-date-formater :date="item.createdDate" />
    </template>
  </v-data-table>
</template>

<script>
import HostManageService from "@/services/HostManageService";
export default {
  data: () => ({
    selected: [],
    headers: [
      { text: "ID", value: "id", align: "start", sortable: true },
      { text: "MSSV", value: "username" },
      { text: "Họ Tên", value: "fullName" }
    ],
    desserts: [],
    totalDesserts: 0,
    loading: true,
    options: {},
  }),
  watch: {
    options: {
      handler() {
        this.getDataFromApi();
      },
      deep: true,
    },
    selected: {
      handler() {
        console.log(this.selected);
      },
    },
  },
  created() {},
  methods: {
    getDataFromApi() {
      console.log(this.options);
      this.loading = true;
      HostManageService.findAllStudent(this.options)
        .then((response) => {
          console.log(response.data);
          this.desserts = response.data.list;
          this.totalDesserts = response.data.totalElements;
        })
        .catch(console.log)
        .finally((this.loading = false));
    },
    
  },
};
</script>