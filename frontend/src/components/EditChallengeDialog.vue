<template>
  <h-confirm-dialog
    :value="value"
    @input="(e) => $emit('input', e)"
    :action="action"
    :error="$v"
    fullscreen
    actionLeft
  >
    <template v-slot:activator>
      <div />
      <!-- null slot, disable default activator btn-->
    </template>
    <v-card-title style="background: #262766; color: white">
      <span class="text-h5">Cập nhật cuộc thi</span>
    </v-card-title>

    <v-card-text>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="editedItem.title"
              label="Tiêu Đề"
              counter="255"
              :error-messages="titleErrors"
              required
              @input="$v.editedItem.title.$touch()"
              @blur="$v.editedItem.title.$touch()"
              id="txtTitle"
            >
            </v-text-field>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-switch
              v-model="editedItem.randomAnswer"
              label="Câu trả lời ngẫu nhiên"
            >
            </v-switch>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-switch
              v-model="editedItem.randomQuest"
              label="Câu hỏi ngẫu nhiên"
            >
            </v-switch>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-select
              :items="challengeStatus"
              v-model="editedItem.challengeStatus"
              label="Trạng thái"
              outlined
            ></v-select>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <h-upload-file v-model="editedItem.coverImage" />
          </v-col>
          <v-col>
            <h3>Ảnh cuộc thi</h3>
            <h-fit-height-image :src="editedItem.coverImage" />
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
  </h-confirm-dialog>
</template>

<script>
import HostManageService from "@/services/HostManageService";
import { validationMixin } from "vuelidate";
import { required, maxLength } from "vuelidate/lib/validators";
export default {
  props: {
    item: Object,
    value: Boolean,
  },
  mixins: [validationMixin],
  validations: {
    editedItem: {
      title: { required, maxLength: maxLength(255) },
    },
  },
  data() {
    return {
      challengeStatus: [
        "BUILDING",
        "WAITING",
        "IN_PROGRESS",
        "LOCKED",
        "ENDED",
      ],
      answerOption: ["SINGLE_SELECT", "MULTI_SELECT"],
      point: ["STANDARD", "DOUBLE_POINTS", "NO_POINTS"],
      action: {
        confirm: () => HostManageService.updateChallenge(this.editedItem),
      },
    };
  },
  computed: {
    // on create
    editedItem() {
      return this.item;
    },
    // on error input
    titleErrors() {
      const errors = [];
      if (!this.$v.editedItem.title.$dirty) return errors;
      !this.$v.editedItem.title.required &&
        errors.push("Question title required!");
      !this.$v.editedItem.title.maxLength &&
        errors.push("Question title length must less than 255!");
      return errors;
    },
  },
};
</script>

<style>
</style>