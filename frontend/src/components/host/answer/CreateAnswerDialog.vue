<template>
  <h-confirm-dialog
    v-model="value"
    @input="reset()"
    :action="action"
    :error="$v"
    btnText="NEW ITEM"
    actionLeft
    fullscreen
  >
    <v-card-title>
      <span class="text-h5">NEW ITEM</span>
    </v-card-title>

    <v-card-text>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-switch v-model="editedItem.isCorrect" label="Correct">
            </v-switch>
          </v-col>
          <v-col cols="12">
            <v-textarea
              v-model="editedItem.answerContent"
              label="Content"
              :error-messages="answerContentErrors"
              :counter="255"
              required
              @input="$v.editedItem.answerContent.$touch()"
              @blur="$v.editedItem.answerContent.$touch()"
            >
            </v-textarea>
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
      answerContent: {
        required,
        maxLength: maxLength(255),
      },
    },
  },
  data() {
    return {
      value: false,
      editedItem: {},
      defaultItem: {
        id: 0,
        answerContent: "",
        isCorrect: false,
      },
      action: {
        confirm: () =>
          HostManageService.addAnswer(
            Object.assign(
              {
                questionId: this.$route.query.questionId,
              },
              this.editedItem
            )
          ),
      },
    };
  },
  computed: {
    answerContentErrors() {
      const errors = [];
      if (!this.$v.editedItem.answerContent.$dirty) return errors;
      !this.$v.editedItem.answerContent.required &&
        errors.push("Answer content required!");
      !this.$v.editedItem.answerContent.maxLength &&
        errors.push("Answer content length must less than 255!");
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