<template>
  <v-row class="ma-0 pa-0">
    <v-col
      cols="6"
      xs="6"
      sm="6"
      class="d-flex answer-col"
      v-for="(i, index) in answers"
      :key="i.id"
    >
      <!-- :disabled="unselectable" -->

      <div
        class="flex d-flex rounded-lg answer-card"
        disabled
        :style="getColor(i.id, index)"
        v-if="i.answerContent !== ''"
      >
        <div class="flex white--text answer-content">
          <b>{{ i.answerContent }}</b>
        </div>
      </div>
    </v-col>
  </v-row>
</template>

<script>

export default {
  props: {
    answers: Array,
  },
  data() {
    return {
      colors: [
        "rgb(226, 27, 60)",
        "rgb(19, 104, 206)",
        "rgb(216, 158, 0)",
        "rgb(38, 137, 12)",
        "#FE670A",
        "#74007E",
      ],
    };
  },
  
  created() {},
  methods: {
    
    getColor(id, index) {
      if (index < 0 || index > this.colors.length - 1) return {};
      var opacity = 1;
      var scale;
      var rotate;
      var result = {
        "background-color": this.colors.at(index),
        opacity: opacity,
        // opacity: 1,
        transform: "scale(" + scale + ") rotateX(" + rotate + ")",
      };
      return result;
    },
  },
};
</script>

<style scoped>


.answer-col {
  padding: 0.5vmin;

}

.answer-card{
  padding: 0px;
  margin: 0px;
}

.answer-content {
  line-height: normal;
  font-size: calc(0.4rem + 1.66267vmin);

  padding : 1vmin;
  margin: 0;

  text-align: justify;
  display: flex;
  justify-content: left;
  align-items: center;
}

.icon {
  font-size: 5vmin;
  margin-right: 1vmin;
  font-weight: 1000;
}
</style>