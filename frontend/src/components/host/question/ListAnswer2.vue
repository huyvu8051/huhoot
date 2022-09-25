<template></template>

<script>
import ManageAnswerService from "@/components/host/answer/ManageAnswerService";
import { validationMixin } from "vuelidate";
import {
  required,
  maxLength,
  minLength,
  minValue,
} from "vuelidate/lib/validators";
export default {
  mixins: [validationMixin],
  // validate answer
  validations: {
    editedItem: {
      ordinalNumber: { required, minValue: minValue(0) },
      answerContent: {
        required,
        maxLength: maxLength(255),
      },
    },
  },
  
};
</script>

<style scoped>
</style>