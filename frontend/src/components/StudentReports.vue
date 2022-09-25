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
          value: 999,
        },
      ],
    }"
    multi-sort
    class=""
  >
    <template #top>
      <v-toolbar flat>
        <v-toolbar-title>{{ title }}</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>

        <v-toolbar-title>tổng điểm: {{ totalPoint }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <slot name="topRight" />
      </v-toolbar>
    </template>

    <template #no-data>
      <v-btn color="primary" @click="options2 = Object.assign({}, options2)">
        <v-icon>mdi-reload</v-icon>
      </v-btn>
    </template>

    <!-- item format -->
  </v-data-table>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "Danh sách cuộc thi",
    },
    desserts: Array,
    totalDesserts: Number,
    options: Object,
    totalPoint: Number,
  },
  data() {
    return {
      headers: [
        { text: "Id", value: "id", align: "start", sortable: true },
        { text: "Content", align: "center", value: "questionContent" },
        // { text: "Ngày tạo", value: "createdDate" },
        { text: "Kiểu trả lời", align: "center", value: "answerOption" },
        { text: "Thời gian hỏi", value: "answerTimeLimit" },
        { text: "Kiểu tính điểm", align: "center", value: "point" },
        { text: "Kết quả trả lời", align: "center", value: "isCorrect" },
        { text: "Điểm nhận được", align: "center", value: "pointReceived" },
        { text: "Thời gian trả lời", align: "center", value: "answerTime" },
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