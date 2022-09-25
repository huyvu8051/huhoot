<template>
  <h-confirm-dialog
    :value="value"
    @input="(e) => $emit('input', e)"
    maxWidth="550px"
    :confirmMessage="confirmMessage"
    :action="action"
  >
    <template v-slot:activator>
      <v-icon medium class="mr-2" @click="openDialog()">mdi-delete </v-icon>
    </template>
  </h-confirm-dialog>
</template>

<script>
import ManageChallengeService from "@/components/host/challenge/ManageChallengeService";
export default {
  props: {
    id: Number,
  },
  data() {
    return {
      value: false,
      action: {
        confirm: () =>
          ManageChallengeService.updateChallenge({
            id: this.id,
            isNonDeleted: false,
          }),
      },
      confirmMessage: "Bạn có muốn xóa không?",
    };
  },
  computed: {
    editedItem() {
      return this.item;
    },
  },
  methods: {
    openDialog() {
      this.value = true;
    },
  },
};
</script>

<style>
</style>