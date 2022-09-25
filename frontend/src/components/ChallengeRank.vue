<template>
  <v-data-table
    :headers="headers"
    :items="desserts"
    :options.sync="options2"
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
    class="elevation-0"
  >
    <template #[`item.rank`]="{ index }">
      {{ index + 1 + options.itemsPerPage * (options.page - 1) }}
    </template>
    <template #[`item.score`]="{ item }">
      {{ Math.round(item.score) }}
    </template>
    <template #[`item.username`]="{ item }">
      <h-route-to-student-details :username="item.username" />
    </template>
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>{{ title }}</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer> </v-spacer>
        <slot name="topRight" />
      </v-toolbar>
    </template>
    <template #no-data>
      <v-btn color="primary" @click="options2 = Object.assign({}, options2)">
        Reset
      </v-btn>
    </template>
    <template #[`item.actions`]="{ item }">
      <h-data-table-router-icon
        icon="info"
        name="studentReports"
        :params="{ username: item.username, challengeId: $route.params.challengeId }"
      />
    </template>
  </v-data-table>
</template>


<script>
export default {
  props: {
    title: {
      type: String,
      default: "Bảng xếp hạng",
    },
    desserts: Array,
    totalDesserts: Number,
    options: Object,
  },
  components: {},
  data() {
    return {
      headers: [
        {
          text: "Position",
          align: "start",
          sortable: false,
          value: "rank",
        },
        { text: "ID", value: "studentId" },
        { text: "Username", value: "username" },
        { text: "Fullname", value: "studentFullName" },
        { text: "Score", value: "score" },
        { text: "Hành động", value: "actions", sortable: false },
      ],
      loading: false,
    };
  },

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

<style>
</style>