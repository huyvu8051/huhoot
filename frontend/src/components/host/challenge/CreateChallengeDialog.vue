<template>
  <h-confirm-dialog
    v-model="value"
    @input="reset()"
    :action="action"
    :error="$v"
    btnText="TẠO MỚI"
    fullscreen
    actionLeft
  >
    <v-card-title style="background: #262766; color: white; ">
      <span class="text-h5">Tạo cuộc thi mới</span>
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
            <v-switch v-model="editedItem.randomAnswer" label="Câu trả lời ngẫu nhiên">
            </v-switch>
          </v-col>
          <v-col cols="12" sm="6" md="3">
            <v-switch v-model="editedItem.randomQuest" label="Câu hỏi ngẫu nhiên">
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
import {
  required,
  maxLength,
  minLength,
  minValue,
} from "vuelidate/lib/validators";

export default {
  mixins: [validationMixin],
  validations: {
    editedItem: {
      title: { required, minLength: minLength(2), maxLength: maxLength(255) },
    },
  },
  data() {
    return {
      value: false,
      challengeStatus: [
        "BUILDING",
        "WAITING",
        "IN_PROGRESS",
        "LOCKED",
        "ENDED",
      ],
      answerOption: ["SINGLE_SELECT", "MULTI_SELECT"],
      point: ["STANDARD", "DOUBLE_POINTS", "NO_POINTS"],
      radioGroup: 1,

      editedItem: {},
      defaultItem: {
        id: 0,
        title: "",
        coverImage: "huhoot-logo.png",
        randomAnswer: false,
        randomQuest: false,
        challengeStatus: "BUILDING",
      },
      action: {
        confirm: () => HostManageService.addChallenge(this.editedItem),
      },
      imageHeight: "100%",
    };
  },
  computed: {
    titleErrors() {
      const errors = [];
      if (!this.$v.editedItem.title.$dirty) return errors;
      !this.$v.editedItem.title.required &&
        errors.push("Nhập tiêu đề cuộc thi");
      !this.$v.editedItem.title.minLength &&
        errors.push("Tiêu đề cuộc thi phải có 2 ký tự trở lên");
      !this.$v.editedItem.title.maxLength &&
        errors.push("Tiêu đề cuộc thi không quá 255 ký tự");
      return errors;
    },
  },

  methods: {
    reset() {
      this.editedItem = Object.assign({}, this.defaultItem);
    },
  },
};
</script>

<style>
</style>