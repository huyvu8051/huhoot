<template>
  <v-row class="ma-0 pa-0">
    <v-col
      cols="12"
      xs="12"
      sm="3"
      md="2"
      lg="1"
      class="d-flex question_nav pa-0"
    >
      <draggable
        v-model="data"
        tag="tbody"
        @change="onUnpublishedChange"
        @start="beforeChange"
        class="question_nav_res d-flex"
      >
        <v-card
          v-for="(i, index) in data"
          :key="index"
          class="question_container"
          @click="chooseQuestion(index)"
        >
          <!--  -->
          <h-image-data-table
            class="justify-center align-center question_image"
            :src="i.questionImage"
            style="margin: 0 auto"
          />
          <v-card-subtitle
            class="question_text"
            style="
              text-align: center;
              text-overflow: ellipsis;
              overflow: hidden;
              white-space: nowrap;
              font-size: 1rem;
              margin: 0;
            "
          >
            {{ i.questionContent }}
          </v-card-subtitle>
        </v-card>
      </draggable>
      <div style="margin: 3vmin auto">
        <v-btn color="green white--text ma-1" @click="addQuestion()">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn color="blue white--text ma-1" @click="save()">
          <v-icon>mdi-content-save</v-icon>
        </v-btn>
        <!-- <v-btn color="black white--text ma-1">
          <v-icon>mdi-settings</v-icon>
        </v-btn> -->
      </div>
    </v-col>
    <v-col cols="12" xs="12" sm="9" md="10" lg="11" style="height: 90vh">
      <v-card flat style="height: 40vh">
        <!-- input -->
        <div
          class="quest-content d-flex align-center"
          style="height: 8vh; padding: 0.5vh 0; gap: 1rem"
        >
          <v-text-field
            counter="255"
            v-model="editedItem.questionContent"
            placeholder="Nội dung câu hỏi"
            class="ma-0"
            :error-messages="questionContentErrors"
            @input="$v.editedItem.questionContent.$touch()"
          ></v-text-field>
          <v-btn
            color="red"
            class="white--text pa-1 mb-2"
            style="height: fit-content; width: fit-content"
            @click="deleteQuestion(editedItem.index)"
            ><v-icon class="pa-0">mdi-delete</v-icon></v-btn
          >
          <ReviewDialogVue :item="editedItem" />
          <v-btn
            color="green"
            class="white--text pa-1 mb-2"
            style="height: fit-content; width: fit-content"
            @click="clone()"
            ><v-icon class="pa-0"> mdi-content-copy</v-icon></v-btn
          >
        </div>
        <!-- pic -->
        <div>
          <!--  -->
          <h-upload-file v-model="editedItem.questionImage" />
          <h-fit-height-image
            style="height: 16vh; margin-bottom: 0.5rem; margin-top: -1rem"
            :src="editedItem.questionImage"
          />
        </div>
        <!-- setting -->
        <v-row>
          <v-col cols="4" sm="4" md="4">
            <!-- v-model="editedItem.answerTimeLimit"
              :error-messages="answerTimeLimitErrors"
              @input="$v.editedItem.answerTimeLimit.$touch()"
              @blur="$v.editedItem.answerTimeLimit.$touch()" -->
            <v-text-field
              v-model="editedItem.answerTimeLimit"
              type="number"
              min="5"
              label="Thời gian"
              :error-messages="answerTimeLimitErrors"
              required
              outlined
              @input="$v.editedItem.answerTimeLimit.$touch()"
              @blur="$v.editedItem.answerTimeLimit.$touch()"
              height="1vh"
            >
            </v-text-field>
          </v-col>
          <v-col cols="4" sm="4" md="4">
            <!-- :items="answerOption"
              v-model="editedItem.answerOption" -->
            <v-select
              :items="answerOption"
              v-model="editedItem.answerOption"
              label="Loại trả lời"
              outlined
            >
            </v-select>
          </v-col>
          <v-col cols="4" sm="4" md="4">
            <!-- :items="point"
              v-model="editedItem.point" -->
            <v-select
              :items="point"
              v-model="editedItem.point"
              label="Loại câu hỏi"
              outlined
            >
            </v-select>
          </v-col>
        </v-row>
      </v-card>
      <!-- answer -->
      <v-row class="ma-0 pa-0" style="height: 50vh">
        <v-col
          cols="6"
          xs="6"
          sm="6"
          class="d-flex answer-col"
          v-for="(i, index) in colors"
          :key="index"
          style="height: calc(50vh / 3)"
        >
          <div
            class="flex d-flex rounded-lg answer-card"
            :style="getColor(i.id, index)"
            style="height: -webkit-fill-available; width: 100%"
          >
            <!-- <v-checkbox
              class="icon"
              color="white darken-3"
              v-model="answers[index]"
            >
            </v-checkbox> -->

            <div class="flex answer-content">
              <v-textarea
                no-resize
                rows="3"
                :value="
                  editedItem.answers
                    ? editedItem.answers[index].answerContent
                    : ''
                "
                @input="
                  (e) => {
                    editedItem.answers[index].answerContent = e;
                  }
                "
              />
            </div>
          </div>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
import HostManageService from "@/services/HostManageService";
import { validationMixin } from "vuelidate";
import {
  required,
  maxLength,
  minLength,
  minValue,
} from "vuelidate/lib/validators";
import ReviewDialogVue from "./ReviewDialog.vue";
import draggable from "vuedraggable";
export default {
  mixins: [validationMixin],
  validations: {
    // validate question
    editedItem: {
      answerTimeLimit: { required, minValue: minValue(5) },
      questionContent: {
        required,
        maxLength: maxLength(255),
        minLength: minLength(2),
      },
    },
  },
  data: () => ({
    // answer colors
    colors: [
      "rgb(226, 27, 60)",
      "rgb(19, 104, 206)",
      "rgb(216, 158, 0)",
      "rgb(38, 137, 12)",
      "#FE670A",
      "#74007E",
    ],
    // question options
    options: {
      challengeId: "3",
      groupBy: [],
      groupDesc: [],
      itemsPerPage: 100,
      multiSort: false,
      mustSort: false,
      page: 1,
      sortBy: [],
      sortDesc: [],
    },
    editedItem: {},
    answerOption: ["SINGLE_SELECT", "MULTI_SELECT"],
    point: ["STANDARD", "DOUBLE_POINTS", "NO_POINTS"],
    // question data
    data: [],
    // answer data
    answers: [],
    // answer options
    answerOptions: {
      groupBy: [],
      groupDesc: [],
      itemsPerPage: 100,
      multiSort: false,
      mustSort: false,
      page: 1,
      sortBy: [],
      sortDesc: [],
    },
    // default question
    defaultItem: {
      questionContent: "",
      questionImage: "hutech-logo.png",
      answerOption: "SINGLE_SELECT",
      answerTimeLimit: 10,
      point: "STANDARD",
    },
  }),
  created() {
    //   this.options.challengeId = this.$route.query.challengeId;
    HostManageService.findAllQuestion(this.options)
      .then((response) => {
        this.data = response.data.list;
        this.answerOptions.questionId = this.data[0].id;
        HostManageService.findAllAnswer(this.answerOptions).then((response) => {
          this.answers = response.data.list;
          for (let i = 0; i < 6; i++) {
            if (this.answers[i] === undefined) {
              this.answers[i] = {
                answerContent: "",
                isCorrect: false,
              };
            }
          }
          this.data[0].answers = [...this.answers];
          this.editedItem = this.data[0];
          this.editedItem.index = 0;
          console.log(this.data);
        });
      })
      .catch(console.log);
  },
  methods: {
    save() {
      console.log(this.data);
      console.log(this.editedItem);
    },
    clone() {
      const temp = Object.assign({}, this.editedItem);
      temp.answers = this.editedItem.answers.slice();
      this.data.push(temp);
    },
    chooseQuestion(index) {
      if (this.data[index].answers === undefined) {
        this.answerOptions.questionId = this.data[index].id;
        HostManageService.findAllAnswer(this.answerOptions).then((response) => {
          this.answers = response.data.list;
          for (let i = 0; i < 6; i++) {
            if (this.answers[i] === undefined) {
              this.answers[i] = {
                answerContent: "",
                isCorrect: false,
              };
            }
          }
          this.data[index].answers = [...this.answers];
          this.editedItem = this.data[index];
          this.editedItem.index = index;
        });
        return;
      }
      // this.editedItem = this.data[index];
      this.editedItem = this.data[index];
      this.editedItem.index = index;
    },
    deleteQuestion(index) {
      if (this.data.length === 1) {
        return;
      } else {
        this.data.splice(index, 1);
        if (this.data[0].answers === undefined) {
          this.answerOptions.questionId = this.data[0].id;
          HostManageService.findAllAnswer(this.answerOptions).then(
            (response) => {
              console.log("click");
              this.answers = response.data.list;
              for (let i = 0; i < 6; i++) {
                if (this.answers[i] === undefined) {
                  this.answers[i] = {
                    answerContent: "",
                    isCorrect: false,
                  };
                }
              }
              this.data[0].answers = [...this.answers];
              this.editedItem = { ...this.data[0] };
              this.editedItem.index = 0;
            }
          );
        }
        this.editedItem = { ...this.data[0] };
        this.editedItem.index = 0;
        console.log(this.editedItem);
      }
    },
    addQuestion() {
      this.data.push({ ...this.defaultItem });

      this.editedItem = this.data[this.data.length - 1];
      this.editedItem.index = this.data.length - 1;
    },
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

    beforeChange() {
      this.tempdata = JSON.parse(JSON.stringify(this.data));
    },
    onUnpublishedChange(data) {
      for (var i = 0; i < this.tempdata.length; i++) {
        this.data[i].ordinalNumber = this.tempdata[i].ordinalNumber;
      }
    },
  },
  computed: {
    // editedItem() {
    //   return this.item;
    // },
    answerTimeLimitErrors() {
      const errors = [];
      if (!this.$v.editedItem.answerTimeLimit.$dirty) return errors;
      !this.$v.editedItem.answerTimeLimit.required &&
        errors.push("Answer time limit required!");
      !this.$v.editedItem.answerTimeLimit.minValue &&
        errors.push("Answer time limit must greater than 5!");
      return errors;
    },
    questionContentErrors() {
      const errors = [];
      if (!this.$v.editedItem.questionContent.$dirty) return errors;
      !this.$v.editedItem.questionContent.required &&
        errors.push("Nhập Nội dung câu hỏi");
      !this.$v.editedItem.questionContent.minLength &&
        errors.push("Nội dung câu hỏi phải có 2 ký tự trở lên");
      !this.$v.editedItem.questionContent.maxLength &&
        errors.push("Nội dung câu hỏi không quá 255 ký tự");
      return errors;
    },
  },
  components: { ReviewDialogVue, draggable },
};
</script>

<style scoped>
@media screen and (max-width: 599px) {
  .question_nav_res {
    overflow-x: scroll !important;
    flex-direction: row !important;
    overflow-y: hidden !important;
    height: auto;
    margin-right: 1vmin;
  }
  .question_text {
    width: 20vw;
  }
  .question_nav {
    flex-direction: row !important;
  }
}

@media screen and (min-width: 600px) {
  .question_nav_res {
    height: 75vh;
  }
}
@media screen and (max-width: 768px) {
  .question_container {
    width: 150px;
  }
}
.question_nav_res {
  overflow-y: scroll;
  flex-direction: column;
  overflow-x: hidden;
  gap: 1vmin;
}
.question_nav {
  flex-direction: column;
}

.question_container {
  background-color: #e6e5e5;
  padding: 1vmin;
  width: 150px;
  margin: 1vmin auto;
}

.question_text {
  padding: 0px;
  padding-top: 0.5vmin;
}

.quest-content {
  height: 10vh;
  line-height: normal;
  font-size: calc(0.4rem + 1.76267vmin);
  /* background-color: rgba(158, 158, 158, 0.445);  */

  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.answer-col {
  padding: 0.5vmin;
}

.answer-card {
  padding: 0px;
  margin: 0px;
  position: relative;
}

.icon {
  position: absolute;
  right: 2px;
  bottom: -15px;
}
.answer-content {
  line-height: normal;
  font-size: calc(0.4rem + 1.66267vmin);
  padding: 1vmin;
  margin: 0.1rem !important;

  text-align: justify;
  display: flex;
  justify-content: left;
  align-items: center;
}

</style>

