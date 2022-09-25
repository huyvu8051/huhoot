<template>
  <!-- 
    @input="reset()"
    
    :error="$v" -->
  <h-simple-dialog
    :action="action"
    v-model="value"
    btnText="Xem"
    fullscreen
    actionLeft
    class="mb-0"
  >
    <Question2 :question="item" style="height: 43vh" />
    <Answers2 :answers="item.answers"  style="height: 43vh" />
  </h-simple-dialog>
</template>

<script>
///import Question2 from "../../Question2.vue";
//import Answers2 from "../../Answers2.vue";

export default {
  props: {
    item: Object,
  },
  components: {
    Question2,
    Answers2,
  },
  data() {
    return {
      value: false,
      action: {
        confirm: () => {},
      },
    };
  },
  methods: {},
  computed: {},
};
</script>


<style>
</style>