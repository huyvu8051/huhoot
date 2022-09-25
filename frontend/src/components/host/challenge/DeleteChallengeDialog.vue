<template>
  <h-confirm-dialog
    :value="value"
    @input="(e) => $emit('input', e)"
    maxWidth="550px"
    :confirmMessage="confirmMessage"
    :action="action"
  >
    <template v-slot:activator>
      <div />
      <!-- null slot, disable default activator btn-->
    </template>
  </h-confirm-dialog>
</template>

<script>
import ManageChallengeService from "@/components/host/challenge/ManageChallengeService";
export default {
  props: {
    item: Object,
    value: Boolean,
  },

  data() {
    return {
      action: {
        confirm: () =>
          ManageChallengeService.updateChallenge({
            id: this.editedItem.id,
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
};
</script>

<style>
</style>