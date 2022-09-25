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
import ManageQuestionService from "@/components/host/question/ManageQuestionService";
export default {
  props: {
    item: Object,
    value: Boolean,
  },

  data() {
    return {
      action: {
        confirm: () =>
          ManageQuestionService.update({
            id: this.editedItem.id,
            isNonDeleted: false,
          }),
      },
      confirmMessage: "Are you sure you want to delete this item?",
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