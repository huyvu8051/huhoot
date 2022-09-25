<template>
  <v-data-table
    :headers="headers"
    :items="desserts"
    :options.sync="options2"
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
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title> {{ title }} </v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
    </template>

    <template #no-data>
      <v-btn color="primary" @click="options2 = Object.assign({}, options2)">
        Reset
      </v-btn>
    </template>
    <template #[`item.studentUsername`]="{ item }">
      <h-route-to-student-details :username="item.studentUsername" />
    </template>
    <template #[`item.createdDate`]="{ item }">
      <h-date-formater :date="item.createdDate" />
    </template>
    <template #[`item.modifiedDate`]="{ item }">
      <h-date-formater :date="item.createdDate" />
    </template>
    <template #[`item.isKicked`]="{ item }">
      {{ item.isKicked ? "Có" : "Không" }}
    </template>
    <template #[`item.isNonDeleted`]="{ item }">
      {{ item.isNonDeleted ? "Được tham gia" : "Không được tham gia" }}
    </template>
    <template #[`item.actions`]="{ item }">
      <h-data-table-router-icon
        icon="info"
        name="studentReports"
        :params="{ username: item.studentUsername, challengeId: $route.params.challengeId }"
      />
    </template>
  </v-data-table>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "Danh sách sinh viên tham gia cuộc thi",
    },
    desserts: Array,
    totalDesserts: Number,
    options: Object,
  },
  // data
  data: () => ({
    headers: [
      { text: "ID", value: "studentId", align: "start", sortable: true },
      { text: "MSSV", value: "studentUsername" },
      { text: "Họ Tên", value: "studentFullName" },
      { text: "Kick", value: "isKicked" },
      { text: "Trạng thái", value: "isNonDeleted" },
      { text: "Thao tác", value: "actions", sortable: false },
    ],

    loading: false,
  }),
  computed: {
    options2: {
      get() {
        return this.options;
      },
      set(value) {
        this.$emit("update:options", value);
      },
    },
  },

  created() {
    this.$eventBus.$on("api-loading", (data) => {
      this.loading = data;
    });
  },
  beforeDestroy() {
    this.$eventBus.$off("api-loading");
  },
};
</script>