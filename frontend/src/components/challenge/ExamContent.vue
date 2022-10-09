<template>
  <div class="exam-content">
    <h2 class="question-content">{{ question.content }}</h2>
    <img class="question-image" :src="questionImage"/>
    <div class="time-left">Time left: {{ diff }}</div>
    <div class="answer-container">
      <div class="answer-item"
           :class="{correct: answer.isCorrect, selected: answer.isSelected, timeout: diff == 0 && !answer.isCorrect, confirmed: answer.isConfirmed}"
           v-for="(answer, index) in answers" key="index" @click="diff == 0 || challengeStore.isConfirmed ? null : challengeStore.select(answer.id)">
        {{ answer.content }}
      </div>
    </div>
  </div>
</template>

<script>
import {useChallengeStore} from "@/stores/ChallengeStore";
import questionImage from "@/assets/question-imagejpg.jpg"
import {onUnmounted, ref} from "vue";

export default {
  name: "Question",
  emits: ['timeout'],
  setup(props, ctx) {
    const challengeStore = useChallengeStore();
    const question = challengeStore.question;
    const answers = challengeStore.answers;

    const diff = ref(0)

    const interval = setInterval(() => {
      diff.value = (question.timeout - Date.now()) / 1000;

      if (diff.value < 0) {
        diff.value = 0;
        ctx.emit("timeout");
        clearInterval(interval)
      }

    }, 200);

    onUnmounted(() => {
      clearInterval(interval)
    })

    return {
      question,
      answers,
      questionImage,
      diff,
      challengeStore
    }
  }
}
</script>


<style scoped>

.question-content {
  max-height: 20vh;
  text-align: center;
  font-size: calc(0.9rem + 1.6%);
  /*background-color: #2ad72a;*/
}

.question-image {
  height: 40%;
  width: 100%;
  text-align: center;
  object-fit: contain;
  /*background-color: #006666;*/
}

.answer-container {
  height: 40%;
  display: flex;
  align-content: stretch;
  flex-flow: row wrap;
}

.answer-item {
  border: 1px solid black;
  border-radius: 5px;
  margin: 0.5vmin;
  padding: 1vmin;
  flex: 1 auto;
  font-size: calc(0.4rem + 1.6vmin);
}

.answer-item:hover{
  cursor: pointer;
}

.time-left {
  height: 3%;
  font-size: calc(0.9rem + 1.6%);
}

.correct {
  background: green;
  color: white;
}

.selected {
  border: 3px solid blue;
}

.confirmed{
  border: 3px solid yellow;
}

.timeout {
  opacity: 0.3;
}
</style>