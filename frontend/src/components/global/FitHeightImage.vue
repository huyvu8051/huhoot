<template>
  <h-image-wrapper
    :src="src"
    :contain="true"
    :height="height"
    v-resize
    @resize="onResize"
  />
</template>

<script>
export default {
  props: {
    src: String,
    windowRatio: {
      type: Number,
      default: 33,
    },
  },
  data(){
    return{
      height: "auto"
    } 
  },
  methods: {
      onResize() {
        this.height = (window.innerHeight / 100) * this.windowRatio + "px";
        //console.log(this.height);
      },
    },
};
</script>

<style>
</style>