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
            value: 9999,
          },
        ],
      }"
      disable-sort
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title
            >Danh sách sinh viên tham gia cuộc thi</v-toolbar-title
          >
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="1000px">
            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="6">
                      <v-switch v-model="editedItem.isKicked" label="Kich">
                      </v-switch>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-switch
                        v-model="editedItem.isNonDeleted"
                        label="Hoạt động"
                      >
                      </v-switch>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close">
                  Cancel
                </v-btn>
                <v-btn color="blue darken-1" text @click="save"> Save </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogAdd" max-width="1000px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
                THÊM MỚI
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12">
                      <ListStudent>
                        <template v-slot:top="props">
                          <AddStudentInChallenge :selected="props.selected" />
                        </template>
                      </ListStudent>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close">
                  Cancel
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">
                Bạn có muốn chắc xóa không?
              </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete">
                  Cancel
                </v-btn>
                <v-btn color="blue darken-1" text @click="deleteItemConfirm">
                  OK
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)"> mdi-pencil </v-icon>
        <v-icon small class="mr-2" @click="deleteItem(item)">
          mdi-delete
        </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="getDataFromApi()"> Reset </v-btn>
      </template>
      <template v-slot:[`item.createdDate`]="{ item }">
        <h-date-formater :date="item.createdDate" />
      </template>
      <template v-slot:[`item.modifiedDate`]="{ item }">
        <h-date-formater :date="item.createdDate" />
      </template>
      <template v-slot:[`item.isKicked`]="{ item }">
        {{ item.isKicked ? "Có" : "Không" }}
      </template>
      <template v-slot:[`item.isNonDeleted`]="{ item }">
        {{ item.isNonDeleted ? "Được tham gia" : "Không được tham gia" }}
      </template>
    </v-data-table>
  </v-flex>
</template>

<script>
import HostManageService from "@/services/HostManageService";

import AddStudentInChallenge from "@/components/host/studentInChallenge/AddStudentInChallenge";
import ListStudent from "@/components/host/studentInChallenge/ListStudent";

export default {
  components: {
    AddStudentInChallenge,
    ListStudent,
  },
  // validate

  // data
  data: () => ({
    challengeStatus: ["BUILDING", "WAITING", "IN_PROGRESS", "LOCKED", "ENDED"],
    answerOption: ["SINGLE_SELECT", "MULTI_SELECT"],
    point: ["STANDARD", "DOUBLE_POINTS", "NO_POINTS"],
    radioGroup: 1,
    dialog: false,
    dialogAdd: false,
    dialogDelete: false,
    headers: [
      { text: "ID", value: "studentId", align: "start", sortable: true },
      { text: "MSSV", value: "studentUsername" },
      { text: "Họ Tên", value: "studentFullName" },
      { text: "Kick", value: "isKicked" },
      { text: "Trạng thái", value: "isNonDeleted" },
      { text: "Thao tác", value: "actions", sortable: false },
    ],
    desserts: [],
    editedIndex: -1,
    editedItem: {
      studentId: 0,
      isKicked: false,
      isNonDeleted: true,
    },
    defaultItem: {
      studentId: 0,
      isKicked: false,
      isNonDeleted: true,
    },
    totalDesserts: 0,
    loading: true,
    options: {},
  }),
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "Thêm mới" : "Cập nhật";
    },
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
    options: {
      handler() {
        this.getDataFromApi();
      },
      deep: true,
    },
  },

  created() {
    this.$eventBus.$on("refreshListStudentInChallenge", this.getDataFromApi);
  },

  methods: {
    editItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    deleteItemConfirm() {
      this.loading = true;
      this.editedItem.isNonDeleted = false;
      HostManageService.updateStudentInChallenge(
        Object.assign(
          { challengeId: this.$route.query.challengeId },
          this.editedItem
        )
      )
        .catch(console.log)
        .finally(() => {
          this.loading = false;
          this.getDataFromApi();
        });

      this.closeDelete();
      this.loading = false;
    },

    close() {
      this.dialogAdd = false;
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      this.loading = true;

      console.log(this.editedItem);

      if (this.editedIndex > -1) {
        HostManageService.updateStudentInChallenge(
          Object.assign(
            { challengeId: this.$route.query.challengeId },
            this.editedItem
          )
        )
          .catch(console.log)
          .finally(() => {
            this.loading = false;
            this.getDataFromApi();
          });
      } else {
        HostManageService.addChallenge(
          Object.assign(
            { challengeId: this.$route.query.challengeId },
            this.editedItem
          )
        )
          .catch(console.log)
          .finally(() => {
            this.loading = false;
            this.getDataFromApi();
          });
      }
      this.close();
    },
    getDataFromApi() {
      //  console.log(this.options);
      this.loading = true;
      this.options.challengeId = this.$route.query.challengeId;
      console.log(this.options);
      HostManageService.findAllStudentInChallenge(this.options)
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