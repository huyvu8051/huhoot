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
      disable-sort
      class="elevation-10"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Danh sách câu hỏi</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <!-- <v-text-field
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          ></v-text-field> -->
          <v-spacer></v-spacer>
          <CreateQuestionDialog />
          <EditQuestionDialog v-model="editDialog" :item="editedItem" />
          <DeleteQuestionDialog v-model="deleteDialog" :item="editedItem" />
        </v-toolbar>
      </template>
      <template v-slot:[`item.point`]="{ item }">
        {{ vietSubQuesPoint(item.point) }}
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="getDataFromApi()">
          <v-icon>mdi-reload</v-icon>
        </v-btn>
      </template>
      <template v-slot:body="props">
        <draggable
          v-model="desserts"
          tag="tbody"
          @change="onUnpublishedChange"
          @start="beforeChange"
        >
          <tr
            v-for="(item, index) in props.items"
            :key="index"
            class="pa-9 ma-8"
          >
            <td>{{ item.id }}</td>
            <td>{{ item.questionContent }}</td>
            <td class="text-center">
              <h-image-data-table
                class="justify-center align-center"
                :src="item.questionImage"
              />
            </td>
            <td><h-date-formater :date="item.createdDate" /></td>
            <td>{{ item.answerOption }}</td>
            <td>{{ item.answerTimeLimit }}</td>
            <td>{{ item.point }}</td>
            <td>
              <h-data-table-router-icon
                icon="view_list"
                name="host.listAnswer"
                :query="{ questionId: item.id }"
              />

              <v-icon small class="mr-2" @click="editItem(item)">
                mdi-pencil
              </v-icon>

              <!--<Review :question="item" /> -->
              <v-icon small class="mr-2" @click="deleteItem(item)">
                mdi-delete
              </v-icon>
            </td>
          </tr>
        </draggable>
      </template>
    </v-data-table>
  </v-flex>
</template>

<script>
import draggable from "vuedraggable";
import HostManageService from "@/services/HostManageService";

import Review from "@/components/host/challenge/Review";
import EditQuestionDialog from "@/components/host/question/EditQuestionDialog";
import CreateQuestionDialog from "@/components/host/question/CreateQuestionDialog";
import DeleteQuestionDialog from "@/components/host/question/DeleteQuestionDialog";
import { vietSubQuesPoint } from "@/services/VietsubServices";

export default {
  components: {
    Review,
    CreateQuestionDialog,
    draggable,
    EditQuestionDialog,
    DeleteQuestionDialog,
  },

  // data
  data: () => ({
    vietSubQuesPoint: vietSubQuesPoint,
    editedItem: {},
    editDialog: false,
    deleteDialog: false,
    headers: [
      {
        text: "Nội dung",
        align: "center",
        value: "questionContent",
        align: "center",
      },
      { text: "Ảnh", value: "questionImage", align: "center" },
      { text: "Ngày tạo", value: "createdDate", align: "center" },
      {
        text: "Kiểu trả lời",
        align: "center",
        value: "answerOption",
        align: "center",
      },
      { text: "Thời gian", value: "answerTimeLimit", align: "center" },
      { text: "Điểm", align: "center", value: "point", align: "center" },
      {
        text: "Loại",
        align: "center",
        value: "actions",
        sortable: false,
        align: "center",
      },
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
  },

  created() {
    this.$eventBus.$on("api-loading", (data) => {
      this.loading = data;
    });
    this.$eventBus.$on("reloadData", () => {
      this.getDataFromApi();
    });
  },

  methods: {
    editItem(item) {
      this.clone(item);
      this.editDialog = true;
    },

    clone(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
    },
    beforeChange() {
      this.tempDesserts = JSON.parse(JSON.stringify(this.desserts));
    },
    onUnpublishedChange(data) {
      for (var i = 0; i < this.tempDesserts.length; i++) {
        this.desserts[i].ordinalNumber = this.tempDesserts[i].ordinalNumber;
      }

      HostManageService.updateOrdinal({
        list: this.desserts,
      }).then((e) => this.getDataFromApi());
    },

    deleteItem(item) {
      this.editedItem = Object.assign({}, item);
      this.deleteDialog = true;
    },

    getDataFromApi() {
      console.log(this.options);
      this.options.challengeId = this.$route.query.challengeId;
      console.log(this.options);
      HostManageService.findAllQuestion(this.options)
        .then((response) => {
          //  console.log(response.data.list);
          this.desserts = response.data.list;
          this.totalDesserts = response.data.totalElements;
          console.log(this.desserts);
        })
        .catch(console.log);
    },
  },
};
</script>

<style>
thead tr th {
  text-align: center;
}
td {
  max-width: 15em;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
