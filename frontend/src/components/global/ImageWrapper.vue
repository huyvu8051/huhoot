<template >
  <v-img
    :lazy-src="require(`@/assets/img/huhoot-logo.png`)"
    :src="url"
    :height="height"
    :width="width"
    :contain="contain"
    v-on:error="url = require(`@/assets/img/huhoot-logo.png`)"
  >
    <slot />
  </v-img>
</template>

<script>
export default {
  props: {
    src: String,
    height: {
      type: String,
      default: "auto",
    },
    width: {
      type: String,
      default: "auto",
    },
    contain: {
      type: Boolean,
      default: false,
    },
  },
  data: () => {
    return {
      url: "",
    };
  },
  computed: {},
  watch: {
    src(val) {
      if (val) {
        this.url = process.env.BACKEND_URL + "/uploads/" + val;
      } else {
        this.url = null;
      }
      // console.log(this.url);
    },
  },
  created() {
    if (this.src) {
      this.url = process.env.BACKEND_URL + "/uploads/" + this.src;
    } else {
      this.url = null;
    }
  },
};
</script>