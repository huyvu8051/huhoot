<template>
  <v-dialog
    v-model="dialog"
    fullscreen
    hide-overlay
    transition="dialog-bottom-transition"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-icon small class="mr-2" v-bind="attrs" v-on="on"> preview </v-icon>
    </template>
    <v-card>
      <v-toolbar dark color="primary">
        <v-toolbar-title>Review</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items>
          <v-btn icon dark @click="dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar-items>
      </v-toolbar>
      <Ask
        :question="question"
        :answers="answers"
        :studentAnswered="69"
      />
    </v-card>
  </v-dialog>
</template>
<script>
import Ask from "@/components/organize/ask/Ask";

export default {
  components: {
    Ask,
  },
  props: {
    question: Object,
  },
  data() {
    return {
      dialog: false,
      notifications: false,
      sound: true,
      widgets: false,
      answers: [],
    };
  },
 
};
</script>

<style>
</style>