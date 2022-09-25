<template>
  <v-dialog
    :value="value"
    @input="(e) => $emit('input', e)"
    :max-width="maxWidth"
    :fullscreen="fullscreen"
  >
    <template v-slot:activator="{ on, attrs }">
      <!-- start default activator -->
      <slot name="activator" :attrs="attrs" :on="on">
        <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
          {{ btnText }}
        </v-btn>
      </slot>
      <!-- end default activator -->
    </template>
    <v-card>
      <!-- start body of dialog -->
      <slot>
        <v-card-title class="text-h5">
          <v-spacer>{{ confirmMessage }}</v-spacer>
        </v-card-title>
      </slot>
      <!-- end body of dialog -->

      <v-card-actions>
        <v-spacer v-if="actionLeft" />
        <v-spacer>
          <v-btn color="blue darken-1" text @click="closeConfirm">
            Đóng
          </v-btn>
          
        </v-spacer>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: {
    action: Object,
    btnText: {
      type: String,
      default: "Action",
    },
    confirmMessage: {
      type: String,
      default: "Bạn có chắc muốn làm hành động này không?",
    },
    maxWidth: {
      type: String,
      default: "auto",
    },
    actionLeft: Boolean,
    fullscreen: Boolean,
    value: Boolean,
    error: Object,
  },
  data() {
    return {};
  },
  methods: {
    closeConfirm() {
      if (this.error !== null && this.error !== undefined) {
        this.error.$reset();
      }
      this.$emit("input", false);
    },
  },
};
</script>
