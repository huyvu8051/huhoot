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
      class="elevation-1"
      loading-text="Loading... Please wait"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>List Answer</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
         
          <v-spacer></v-spacer>
          <CreateAnswerDialog />
          <DeleteAnswerDialog v-model="deleteDialog" :item="editedItem" />
        </v-toolbar>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="getDataFromApi()"> Reset </v-btn>
      </template>

      <template #body>
        <draggable
          v-model="desserts"
          tag="tbody"
          @change="onUnpublishedChange"
          @start="beforeChange"
        >
          <tr v-for="(item, index) in desserts" :key="index">
            <td>{{ item.id }}</td>
            <td>{{ item.ordinalNumber }}</td>
            <td>
              <v-edit-dialog
                @save="updateContent(editedItem)"
                @open="editedItem = Object.assign({}, item)"
              >
                {{ item.answerContent }}
                <template v-slot:input>
                  <v-text-field
                    v-model="editedItem.answerContent"
                    :rules="[max255chars]"
                    label="Edit"
                    single-line
                    counter
                  ></v-text-field>
                </template>
              </v-edit-dialog>
            </td>

            <td><h-date-formater :date="item.createdDate" /></td>
            <td>
              <v-simple-checkbox
                v-model="item.isCorrect"
                @input="updateIsCorrect(item)"
              ></v-simple-checkbox>
            </td>

            <td>
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
import HostManageService from "@/services/HostManageService";
import DeleteAnswerDialog from "@/components/host/answer/DeleteAnswerDialog";
import CreateAnswerDialog from "@/components/host/answer/CreateAnswerDialog";
import draggable from "vuedraggable";
import ManageAnswerService from "@/components/host/answer/ManageAnswerService";
import { validationMixin } from "vuelidate";
import {
  required,
  maxLength,
  minLength,
  minValue,
} from "vuelidate/lib/validators";

export default {
  // validate
  mixins: [validationMixin],
  validations: {
    editedItem: {
      ordinalNumber: { required, minValue: minValue(0) },
      answerContent: {
        required,
        maxLength: maxLength(255),
      },
    },
  },
  components: {
    draggable,
    CreateAnswerDialog,
    DeleteAnswerDialog,
  },
  // data
  data: () => ({
    deleteDialog: false,
    max255chars: (v) => v.length <= 255 || "Input too long!",

    headers: [
      { text: "Id", value: "id", align: "center", sortable: true },
      { text: "Ordinal number", align: "center", value: "ordinalNumber" },
      { text: "Content", align: "center", value: "answerContent" },
      { text: "Created date", align: "center", value: "createdDate" },
      { text: "Correct", align: "center", value: "isCorrect" },
      { text: "Actions", align: "center", value: "actions", sortable: false },
    ],
    desserts: [],
    tempDesserts: [],
    editedIndex: -1,
    editedItem: {
      id: 0,
      answerContent: "",
      ordinalNumber: 0,
      isCorrect: false,
    },

    totalDesserts: 0,
    loading: true,
    options: {},
  }),
  computed: {
    answerContentErrors() {
      const errors = [];
      if (!this.$v.editedItem.answerContent.$dirty) return errors;
      !this.$v.editedItem.answerContent.required &&
        errors.push("Answer content required!");
      !this.$v.editedItem.answerContent.maxLength &&
        errors.push("Answer content length must less than 255!");
      return errors;
    },
  },

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
    beforeChange() {
      this.tempDesserts = JSON.parse(JSON.stringify(this.desserts));
    },
    onUnpublishedChange(data) {
      for (var i = 0; i < this.tempDesserts.length; i++) {
        this.desserts[i].ordinalNumber = this.tempDesserts[i].ordinalNumber;
      }

      ManageAnswerService.updateOrdinal({
        list: this.desserts,
      }).then((e) => this.getDataFromApi());
      
    },
    updateContent(item) {
      this.$v.$touch();
      if (this.$v.$anyError) {
        return this.$eventBus.$emit("nofication", {
          message: "Not valid",
          status: "error",
        });
      }
      HostManageService.updateAnswer(item).then((e) =>
        this.$eventBus.$emit("reloadData")
      );
    },
    updateIsCorrect(item) {
      HostManageService.updateAnswer(item).then((e) =>
        this.$eventBus.$emit("reloadData")
      );
    },

    deleteItem(item) {
      this.editedItem = Object.assign({}, item);
      this.deleteDialog = true;
    },

    getDataFromApi() {
      console.log(this.options);
      this.options.questionId = this.$route.query.questionId;

      HostManageService.findAllAnswer(this.options).then((response) => {
        // console.log(response.data);
        this.desserts = response.data.list;
        console.log(this.desserts);
        this.totalDesserts = response.data.totalElements;
      });
    },
  },
};
</script>