<template>
  <v-dialog v-model="dialog" transition="dialog-bottom-transition">
    <template v-slot:activator="{ on, attrs }">
      <v-btn color="black" text v-bind="attrs" v-on="on"> Show question </v-btn>
    </template>

    <v-card>
      <v-card-title>
        <span class="text-h5"> Question </span>
        <v-spacer></v-spacer>
        <v-btn icon @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>

      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <Question/>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import Question from "@/components/Question";
export default {

  components: {
    Question,
  },
  data: () => {
    return {
      dialog: false,
    };
  },
};
</script>

<style>
</style>