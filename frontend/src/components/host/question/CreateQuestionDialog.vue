<template>
  <h-confirm-dialog
    v-model="value"
    @input="reset()"
    :action="action"
    :error="$v"
    btnText="TẠO MỚI"
    actionLeft
    fullscreen
  >
    <v-card-title style="background: #262766; color: white">
      <span class="text-h5">TẠO MỚI</span>
    </v-card-title>

    <v-card-text>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="editedItem.questionContent"
              label="Nội dung"
              :error-messages="questionContentErrors"
              :counter="255"
              required
              outlined
              @input="$v.editedItem.questionContent.$touch()"
              @blur="$v.editedItem.questionContent.$touch()"
            >
            </v-text-field>
          </v-col>
          <v-col cols="12" sm="3" md="3">
            <v-text-field
              v-model="editedItem.answerTimeLimit"
              type="number"
              min="5"
              label="Thời gian"
              :error-messages="answerTimeLimitErrors"
              required
              outlined
              @input="$v.editedItem.answerTimeLimit.$touch()"
              @blur="$v.editedItem.answerTimeLimit.$touch()"
            >
            </v-text-field>
          </v-col>

          <v-col cols="12" sm="3" md="3">
            <v-select
              :items="answerOption"
              v-model="editedItem.answerOption"
              label="Loại trả lời"
              outlined
            >
            </v-select>
          </v-col>
          <v-col cols="12" sm="3" md="3">
            <v-select
              :items="point"
              v-model="editedItem.point"
              label="Loại câu hỏi"
              outlined
            >
            </v-select>
          </v-col>

          <v-col cols="12" sm="3" md="3">
            <h-upload-file v-model="editedItem.questionImage" />
          </v-col>
          <v-col cols="12">
            <p>Ảnh câu hỏi</p>
            <h-fit-height-image :src="editedItem.questionImage" />
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
      answerTimeLimit: { required, minValue: minValue(5) },
      questionContent: {
        required,
        maxLength: maxLength(255),
      },
    },
  },
  data() {
    return {
      value: false,
      answerOption: ["SINGLE_SELECT", "MULTI_SELECT"],
      point: ["STANDARD", "DOUBLE_POINTS", "NO_POINTS"],

      editedItem: {},
      defaultItem: {
        id: 0,
        questionContent: "",
        questionImage: "hutech-logo.png",
        answerOption: "SINGLE_SELECT",
        answerTimeLimit: 10,
        point: "STANDARD",
      },
      action: {
        confirm: () =>
          HostManageService.addQuestion(
            Object.assign(
              {
                challengeId: this.$route.query.challengeId,
              },
              this.editedItem
            )
          ),
      },
    };
  },
  computed: {
    answerTimeLimitErrors() {
      const errors = [];
      if (!this.$v.editedItem.answerTimeLimit.$dirty) return errors;

      !this.$v.editedItem.answerTimeLimit.required &&
        errors.push("Nhập thời gian cuộc thi");
      !this.$v.editedItem.answerTimeLimit.minValue &&
        errors.push("Thời gian cuộc thi từ 5s trở lên");

      return errors;
    },
    questionContentErrors() {
      const errors = [];
      if (!this.$v.editedItem.questionContent.$dirty) return errors;
      !this.$v.editedItem.questionContent.required &&
        errors.push("Nhập Nội dung câu hỏi");
      !this.$v.editedItem.questionContent.minLength &&
        errors.push("Nội dung câu hỏi phải có 2 ký tự trở lên");
      !this.$v.editedItem.questionContent.maxLength &&
        errors.push("Nội dung câu hỏi không quá 255 ký tự");
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