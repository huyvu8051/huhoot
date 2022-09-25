<template>
  <ListChallenge
    title="Danh sách cuộc thi"
    :options.sync="options"
    :desserts="desserts"
    :totalDesserts="totalDesserts"
  >
  
    <template #actions="item">
      <!-- <h-data-table-router-icon
        icon="mdi-pencil"
        name="editChallenge"
        :params="{ challengeId: item.item.id }"
      /> -->
      
      <OpenChallenge :challengeId="item.item.id" />
      <h-data-table-router-icon
        icon="mdi-play-circle"
        name="organize.wait"
        :query="{ challengeId: item.item.id }"
      />
      <DeleteChallengeDialog :id="item.item.id" />
    </template>
    <template #topRight="item">
      <CreateChallengeDialog />
    </template>
  </ListChallenge>
</template>

<script>
import OpenChallenge from "@/components/host/challenge/OpenChallenge";
import ListChallenge from "@/components/ListChallenge";
import AdminManageService from "@/services/AdminManageService";
import EditChallengeDialog from "@/components/EditChallengeDialog";

import CreateChallengeDialog from "@/components/host/challenge/CreateChallengeDialog";

import DeleteChallengeDialog from "@/components/DeleteChallengeDialog";

export default {
  components: {
    OpenChallenge,
    EditChallengeDialog,
    DeleteChallengeDialog,
    CreateChallengeDialog,
    ListChallenge,
  },
  // data
  data: () => ({
    dialog: false,
    deleteDialog: false,
    editDialog: false,

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

  methods: {
    //===================================================
    getDataFromApi() {
      //  console.log(this.options);
      AdminManageService.getAllChallenge(this.options)
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