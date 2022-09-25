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
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>List Host</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="1000px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
                New Item
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="editedItem.username"
                        label="Username"
                        counter="15"
                        :error-messages="usernameErrors"
                        required
                        @input="$v.editedItem.username.$touch()"
                        @blur="$v.editedItem.username.$touch()"
                        id="username-input"
                      >
                      </v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-switch
                        v-model="editedItem.isNonLocked"
                        label="Non locked"
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
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">
                Are you sure you want to delete this item?
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
          <v-dialog v-model="dialogReset" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">
                Are you sure you want to reset password for this item?
              </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete">
                  Cancel
                </v-btn>
                <v-btn color="blue darken-1" text @click="resetPasswordConfirm">
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
        <v-icon small class="mr-2" @click="reset(item)"> lock_reset </v-icon>
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
        <h-date-formater :date="item.modifiedDate" />
      </template>
    </v-data-table>
  </v-flex>
</template>

<script>
import AdminManageService from "@/services/AdminManageService";

import OpenChallenge from "@/components/host/challenge/OpenChallenge";


import { validationMixin } from "vuelidate";
import {
  required,
  maxLength,
  minLength,
  minValue,
  length,
} from "vuelidate/lib/validators";

export default {
  components: {
    OpenChallenge,
  },
  // validate
  mixins: [validationMixin],
  validations: {
    editedItem: {
      username: {
        required,
        maxLength: maxLength(15),
        startWith: function (value) {
          const regex = /^(admin).*$/.test(value);
          return regex;
        },
      },
    },
  },

  // data
  data: () => ({
    showErrorDialog: false,
    dialog: false,
    dialogDelete: false,
    dialogReset: false,
    headers: [
      { text: "Id", value: "id", align: "start", sortable: true },
      { text: "Username", value: "username" },
      { text: "Created date", value: "createdDate" },
      { text: "Created by", value: "createdBy" },
      { text: "Modified date", value: "modifiedDate" },
      { text: "Modified by", value: "modifiedBy" },
      { text: "Non locked", value: "isNonLocked" },
      { text: "Actions", value: "actions", sortable: false },
    ],
    desserts: [],
    editedIndex: -1,
    editedItem: {
      id: 0,
      username: "",
      isNonLocked: true,
    },
    defaultItem: {
      id: 0,
      username: "",
      isNonLocked: true,
    },
    totalDesserts: 0,
    loading: true,
    options: {},
  }),
  computed: {
    usernameErrors() {
      const errors = [];
      if (!this.$v.editedItem.username.$dirty) return errors;
      !this.$v.editedItem.username.required &&
        errors.push("Username required!");

      !this.$v.editedItem.username.maxLength &&
        errors.push("Username must less than 15");

      !this.$v.editedItem.username.startWith &&
        errors.push("Username must start with 'admin'");
      return errors;
    },

    formTitle() {
      return this.editedIndex === -1 ? "New Item" : "Edit Item";
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
      AdminManageService.updateHost(
        Object.assign(this.editedItem, { isNonLocked: false })
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
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    closeDelete() {
      this.dialogReset = false;
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      this.$v.$touch();
      if (this.$v.$anyError) {
        return;
      }
      this.loading = true;
      if (this.editedIndex > -1) {
        AdminManageService.updateHost(Object.assign({}, this.editedItem))
          
          .finally(() => {
            this.showAddErrorDialog();
            this.loading = false;
            this.getDataFromApi();
          });
      } else {
        AdminManageService.addHost([Object.assign({}, this.editedItem)])
          .then((response)=>{
            console.log("cc", response.data)
            if(response.data.length > 0){
              this.$eventBus.$emit("nofication",{
              message: "Add error",
              status: "error"
            })
            }
          })
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

      console.log(this.options);

      AdminManageService.findAllHost(this.options)
        .then((response) => {
          console.log(response.data);
          this.desserts = response.data.list;
          this.totalDesserts = response.data.totalElements;
        })
        .catch(console.log)
        .finally((this.loading = false));
    },
    resetPasswordConfirm() {
      console.log("reset", this.editedItem);
      AdminManageService.resetPasswordAdmin([this.editedItem.id])
        .then((response) => {
          this.$eventBus.$emit("nofication", {
            message: "Reset password success",
          });
        })
        .finally(() => {
          this.dialogReset = false;
        });
    },
    reset(item) {
      this.editedIndex = this.desserts.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogReset = true;
    },
    showAddErrorDialog() {
      this.showErrorDialog = true;
    },
  },
};
</script>