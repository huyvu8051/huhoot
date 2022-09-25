<template>
  <v-dialog v-model="dialog" max-width="1000px">
    <template v-slot:activator="{ on, attrs }">
      <v-btn color="primary" dark class="mb-2 mr-2" v-bind="attrs" v-on="on">
        Import
      </v-btn>
    </template>
    <v-card>
      <v-card-title>
        <span class="text-h5">Import</span>
      </v-card-title>

      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-file-input
                name="xlsx"
                accept="xlsx/*"
                label="Upload Excel/xlsx"
                @change="uploadFile"
                show-size
              >
              </v-file-input>
            </v-col>
            <v-col cols="12">
              <ListStudentImport
                :loading="loading"
                :title="title"
                :desserts="listStudent"
              />
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>

        <vue-excel-xlsx
          :columns="columns"
          :data="listStudent"
          :filename="'filename'"
          :sheetname="'sheetname'"
        >
          <v-btn color="blue darken-1" text> Export </v-btn>
        </vue-excel-xlsx>

        <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
        <v-btn color="blue darken-1" text @click="save"> Save </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import readXlsxFile from "read-excel-file";

import AdminManageService from "@/services/AdminManageService";

import ListStudentImport from "@/components/admin/ListStudentImport";

export default {
  components: {
    ListStudentImport,
  },
  data: () => {
    return {
      columns: [
        { label: "Username", field: "username" },
        { label: "Fullname", field: "fullName" },
        { label: "Error", field: "errorMessage" },
      ],
      dialog: false,
      listStudent: [],
      file: null,
      title: "Import",
      loading: false,
    };
  },
  methods: {
    close() {
      this.dialog = false;
    },
    save() {
      if (this.listStudent.length == 0) {
        this.$eventBus.$emit("nofication", {
          message: "Import file first",
          status: "error",
        });
      }
      this.loading = true;
      AdminManageService.addStudent(this.listStudent)
        .then((response) => {
          this.listStudent = response.data;
          this.title = "Error response";
        })
        .catch(console.log)
        .finally((this.loading = false));
    },

    uploadFile(event) {
      this.file = event;
      this.loading = true;
      readXlsxFile(this.file)
        .then((rows) => {
          // console.log("rows:", rows);
          this.title = "Import";
          var map = rows.map((e) => ({
            username: e[0],
            fullName: e[1],
            isNonLocked: true,
          }));
          this.listStudent = map;
        })
        .catch(() => {
          this.file = null;
          this.listStudent = [];
        })
        .finally((this.loading = false));
    },
  },
};
</script>

<style>
</style>