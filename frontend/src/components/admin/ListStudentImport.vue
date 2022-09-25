<template>
  <v-data-table
    :headers="headers"
    :items="desserts"
    item-key="username"
    :items-per-page="10"
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
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title> {{ title }} </v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
      </v-toolbar>
    </template>
  </v-data-table>
</template>

<script>
export default {
  props: {
    desserts: Array,
    title: String,
    loading: Boolean,
  },
  data() {
    return {
      headers: [
        { text: "Username", value: "username" },
        { text: "Fullname", value: "fullName" },
        { text: "Error", value: "errorMessage" },
      ],
    };
  },
};
</script>