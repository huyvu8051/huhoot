<template>
  <v-data-table
    :headers="headers"
    :items="desserts"
    :search="search"
    class="elevation-1"
  >
    <template v-slot:[`item.coverImage`]="{ item }">
      <h-image-data-table :src="item.coverImage" />
    </template>
    <template v-slot:[`item.createdDate`]="{ item }">
      <h-date-formater :date="item.createdDate" />
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize">Reset</v-btn>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon small class="mr-2" @click="play(item)">play_arrow</v-icon>
    </template>

    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title> List challenge </v-toolbar-title>
        <v-divider class="mx-4" inset vertical> </v-divider>
        <v-spacer> </v-spacer>
        <v-dialog v-model="dialogPlay" max-width="500px">
          <v-card>
            <v-card-title class="text-h5"
              >Are you sure you want to join this game?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closePlay"
                >Cancel</v-btn
              >
              <v-btn color="blue darken-1" text @click="playConfirm">OK</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
  </v-data-table>
</template>
<script>
import StudentManageService from "@/services/StudentManageService";

export default {
  data: () => ({
    search: "",
    dialogPlay: false,
    headers: [
       { text: "Id", value: "id", align: "start", sortable: true },
      { text: "Title", value: "title" },
      { text: "Cover", value: "coverImage" },
      { text: "Created date", value: "createdDate" },
      { text: "Owner", value: "owner" },
      { text: "Random answer", value: "randomAnswer" },
      { text: "Random quest", value: "randomQuest" },
      { text: "Status", value: "challengeStatus" },
      { text: "Actions", value: "actions", sortable: false },
    ],
    desserts: [],
    editedItem: {},
  }),

  watch: {
    dialogPlay(val) {
      val || this.closePlay();
    },
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      StudentManageService.findAllChallenge()
        .then((response) => {
          // console.log(response.data);
          this.desserts = response.data.list;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    closePlay() {
      this.dialogPlay = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    // play an available game
    playConfirm() {
      // join a game

      let challengeId = this.editedItem.id;

      this.$router.push({
        name: "participate.wait",
        query: { challengeId: challengeId },
      });

      this.dialogPlay = false;
    },

    play(data) {
      this.editedItem = data;
      this.dialogPlay = true;
    },
    formatDate(s) {
      var date = new Date(s);

      return date.toLocaleString();
    },
  },
};
</script>
