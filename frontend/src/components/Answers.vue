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
        @click="selectAnswer(i)"
        :style="getColor(i.id, index)"
      >
        <v-icon color="white" class="icon2">
          {{ getShape(index) }}
        </v-icon>
        <div class="flex white--text answer-content">
          <b>{{ i.content }}</b>
        </div>
        <v-icon v-if="timeout" color="white" class="icon">
          {{ getIcon(i) }}
        </v-icon>
      </div>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  props: {
    disable: Boolean,
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
  computed: mapState({
    answers: (state) => {
      return state.answers;
    },
    unselectable(state) {
      return (
        state.studentSubmited ||
        state.question.timeout < new Date().getTime() ||
        this.disable
      );
    },
    timeout: (state) => state.question.timeout < new Date().getTime(),
  }),
  methods: {
    getShape(index) {
      var shapes = ["crop_square", "change_history",  "circle", "star", "add", "numbers"];

      return shapes[index];
    },
    getIcon(item) {
      var state = this.$store.state;
      if (state.correctAnswerIds.includes(item.id)) {
        return "check";
      } else {
        return "close";
      }
    },
    getColor(id, index) {
      if (index < 0 || index > this.colors.length - 1) return {};

      var state = this.$store.state;

      var opacity =
        state.correctAnswerIds.includes(id) ||
        state.question.timeout > new Date().getTime()
          ? 1
          : 0.2;

      var s =
        state.question.timeout > new Date().getTime()
          ? state.selectedAnswerIds.includes(id)
          : false;

      var scale;
      var rotate;

      if (s) {
        // map selected answer style

        scale = 0.8;
        rotate = "20deg";
      } else {
        scale = 1;
        rotate = "0deg";
      }

      var result = {
        "background-color": this.colors.at(index),
        opacity: opacity,
        // opacity: 1,
        transform: "scale(" + scale + ") rotateX(" + rotate + ")",
      };
      return result;
    },
    selectAnswer(item) {
      if (!this.unselectable) {
        this.$store.commit("selectAnswer", item);
      }
    },
  },
};
</script>

<style scoped>
.answer-col {
  padding: 0.5vmin;
}

.answer-card {
  padding: 0px;
  margin: 0px;
}

.answer-content {
  line-height: normal;
  font-size: calc(0.4rem + 1.66267vmin);

  padding: 1vmin;
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
.icon2 {
  font-size: 5vmin;
  margin-left: 1vmin;
  font-weight: 1000;
}
</style>