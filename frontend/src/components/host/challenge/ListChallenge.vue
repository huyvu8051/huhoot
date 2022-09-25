<template>
  <v-flex xs12 sm12 md12>
    <v-data-table
      :headers="headers"
      :items="desserts"
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
            value: 999,
          },
        ],
      }"
      multi-sort
      class=""
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Danh sách cuộc thi</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <!-- <v-text-field
            append-icon="mdi-magnify"
            label="Tìm kiếm"
            single-line
            hide-details
            color="#262766"
          ></v-text-field> -->
          <v-spacer></v-spacer>
          <DeleteChallengeDialog v-model="deleteDialog" :item="editedItem" />
          <EditChallengeDialog v-model="editDialog" :item="editedItem" />
          <CreateChallengeDialog />
        </v-toolbar>
      </template>

      <template v-slot:no-data>
        <v-btn color="primary" @click="getDataFromApi()">
          <v-icon>mdi-reload</v-icon>
        </v-btn>
      </template>

      <!-- item format -->

      <template v-slot:[`item.actions`]="{ item }">
        <h-data-table-router-icon
          icon="poll"
          name="host.rank"
          :query="{ challengeId: item.id }"
        />
        <h-data-table-router-icon
          icon="view_list"
          name="host.listQuestion"
          :query="{ challengeId: item.id }"
        />
        <h-data-table-router-icon
          icon="group"
          name="host.listStudentInChallenge"
          :query="{ challengeId: item.id }"
        />

        <v-icon medium class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
        <v-icon medium class="mr-2" @click="deleteItem(item)"
          >mdi-delete
        </v-icon>
        <OpenChallenge :challengeId="item.id" />

        <h-data-table-router-icon
          icon="mdi-play-circle"
          name="organize.wait"
          :query="{ challengeId: item.id }"
        />
      </template>

      <template v-slot:[`item.coverImage`]="{ item }">
        <h-image-data-table :src="item.coverImage" style="margin: 0 auto" />
      </template>

      <template v-slot:[`item.createdDate`]="{ item }">
        <h-date-formater :date="item.createdDate" />
      </template>

      <template v-slot:[`item.randomAnswer`]="{ item }">
        <h-true-false :condition="item.randomAnswer" />
      </template>

      <template v-slot:[`item.challengeStatus`]="{ item }">
        <h-status-icon :item="item" />
      </template>

      <template v-slot:[`item.randomQuest`]="{ item }">
        <h-true-false :condition="item.randomQuest" />
      </template>

      <template v-slot:[`item.modifiedDate`]="{ item }">
        <h-date-formater :date="item.modifiedDate" />
      </template>
    </v-data-table>
  </v-flex>
</template>

<script>
import HostManageService from "@/services/HostManageService";
import EditChallengeDialog from "@/components/host/challenge/EditChallengeDialog";

import CreateChallengeDialog from "@/components/host/challenge/CreateChallengeDialog";

import DeleteChallengeDialog from "@/components/host/challenge/DeleteChallengeDialog";
import OpenChallenge from "@/components/OpenChallenge";

export default {
  components: {
    OpenChallenge,
    EditChallengeDialog,
    DeleteChallengeDialog,
    CreateChallengeDialog,
  },
  // data
  data: () => ({
    dialog: false,
    deleteDialog: false,
    editDialog: false,

    headers: [
      { text: "Mã", value: "id", align: "center", sortable: true },
      { text: "Ảnh", value: "coverImage", align: "center" },
      { text: "Tiêu Đề", value: "title", align: "center" },
      // { text: "Created date", value: "createdDate" },
      // { text: "Modified date", value: "modifiedDate" },
      // { text: "Owner", value: "owner" },
      {
        text: "Câu trả lời ngẫu nhiên",
        value: "randomAnswer",
        align: "center",
      },
      { text: "Câu hỏi ngẫu nhiên", value: "randomQuest", align: "center" },
      { text: "Trạng thái", value: "challengeStatus", align: "center" },
      { text: "Thao tác", value: "actions", sortable: false },
    ],
    desserts: [],
    editedItem: {},

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
  },

  created() {
    this.$eventBus.$on("api-loading", (data) => {
      this.loading = data;
    });
    this.$eventBus.$on("reloadData", () => {
      this.getDataFromApi();
    });
  },
  beforeDestroy() {
    this.$eventBus.$off("api-loading");
    this.$eventBus.$off("reloadData");
  },

  methods: {
    deleteItem(item) {
      this.clone(item);
      this.deleteDialog = true;
    },

    // =========================
    editItem(item) {
      this.clone(item);
      this.editDialog = true;
    },

    clone(item) {
      
      this.editedIndex = this.desserts.indexOf(item);
      console.log(item)
      this.editedItem = Object.assign({}, item);
    },

    //===================================================
    getDataFromApi() {
      //  console.log(this.options);
      HostManageService.findAllChallenge(this.options)
        .then((response) => {
          // console.log(response.data);
          this.desserts = response.data.list;
          this.totalDesserts = response.data.totalElements;
        })
        .catch(console.log);
    },
  },
};
</script>

<style>
.text-center,
.v-data-table__mobile-row__cell {
  max-width: 10em;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>