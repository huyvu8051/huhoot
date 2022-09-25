import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

import AutoOrganizeService from "@/services/AutoOrganizeService";
import DecryptUtil from "@/services/DecryptUtil";
import HostOrganizeService from "@/services/HostOrganizeService";
import router from "@/router"

Vue.use(Vuex)

var store = new Vuex.Store({
	strict: true,
	plugins: [
		createPersistedState()
	],
	state: {
		token: null,
		username: null,
		fullName: null,
		roles: null,
		hashCorrectAnswerIds: "",
		challenge: null,
		question: null,
		answers: null,
		adminSocketId: "",
		totalPoints: 0,
		hashPointsReceived: "",
		comboToken: "",
		combo: "",
		currCombo: "",
		pointsReceived: "",
		studentAnswered: 0,
		totalStudent: 0,
		totalStudentCorrect: 0,
		totalStudentWrong: 0,
		encryptedResponse: "",
		questionToken: null,
		publishNextQuestion: (challengeId) => { /*console.log("disable auto next", challengeId)*/ },
		getCorrectAnswer: (questionId) => { /*console.log("disable auto show", questionId)*/ },

		getStatisticPage: (challengeId) => { /*console.log("disable route to statistic page", challengeId)*/ },
		getRankTablePage: (challengeId) => { /*console.log("disable route to rank table page", challengeId)*/ },
		getNextQuestionPage: (challengeId) => { /*console.log("disable route to next question page", challengeId)*/ },
		studentSubmited: false,
		correctAnswerIds: [],
		selectedAnswerIds: [],
		organizeGetCorrectAnswer: HostOrganizeService.showCorrectAnswer


	},
	mutations: {
		setAutoOrganize(state, value) {
			if (state.challenge) {
				state.challenge.autoOrganize = value
			}
		},
		pnq(state) {
			state.publishNextQuestion(state.challenge.id)
		},
		enableOrganizeGetCorrectAnswer(state) {
			state.organizeGetCorrectAnswer = HostOrganizeService.showCorrectAnswer
		},
		disableOrganizeGetCorrectAnswer(state) {
			state.organizeGetCorrectAnswer = (questionId) => { /* console.log("disable organize get correct answer", questionId) */}
		},

		studentSubmited(state) {
			state.studentSubmited = true;
		},


		setToken(state, data) {
			state.token = data
		},
		setUsername(state, data) {
			state.username = data
		},

		setRoles(state, data) {
			state.roles = data
		},

		setTotalPoints(state, data) {
			state.totalPoints = data
		},


		//===========================

		publishExam(state, data) {
			state.studentAnswered = 0
			state.question = data.question
			state.answers = data.answers
			state.questionToken = data.questionToken;
			state.hashPointsReceived = ""


			state.studentSubmited = false
			state.correctAnswerIds = []
			state.selectedAnswerIds = []
		},
		timeout(state) {
			state.question.timeout++;

			state.getCorrectAnswer(state.question.id)
			state.organizeGetCorrectAnswer({
				questionId: state.question.id,
			});
		},

		calculatePointReceived(state, data) {

			try {
				var result = JSON.parse(DecryptUtil.decrypt(state.encryptedResponse, data.encryptKey))
				state.pointsReceived = result.pointsReceived;
				state.currCombo = result.comboCount;

				state.totalPoints = parseFloat(state.totalPoints) + parseFloat(state.pointsReceived);
			} catch (error) {
				state.pointsReceived = null;
				state.currCombo = 0;
			}

			switch (state.pointsReceived) {
				case null:
					Vue.swal({
						icon: "warning",
						title: "Hết giờ",
						text: "Chúc may mắn lần sau!",
						timer: 3000,
					});
					break;

				case 0:
					Vue.swal({
						icon: "error",
						title: "Xui quá...",
						text: "Đáp án của bạn chưa đúng!",
						timer: 3000,
					});
					break;

				default:
					Vue.swal({
						icon: "success",
						title: "Làm tốt lắm!",
						text:
							"Đáp án chính xác! \n Điểm nhận được +" +
							parseInt(state.pointsReceived) +
							" combo: " +
							state.currCombo,
						timer: 3000,
					});
					break;
			}


		},
		showCorrectAnswer(state, data) {

			//console.log("show correct answer");
			state.totalStudent = data.totalStudent
			state.totalStudentCorrect = data.totalStudentCorrect
			state.totalStudentWrong = data.totalStudentWrong
			state.correctAnswerIds = data.answers
			if (state.question) {
				state.question.timeout = data.timeout
			}




		},
		saveStudentAnswerResponse(state, data) {
			state.comboToken = data.comboToken;
			state.encryptedResponse = data.encryptedResponse;
		},

		saveChallengeData(state, data) {
			state.challenge = data.challenge;
			state.question = data.question
			state.answers = data.answers
		},
		enableAutoOrganize(state, data) {
			//console.log("enableAutoOrganize");
			state.publishNextQuestion = AutoOrganizeService.publishNextQuestion
			state.getCorrectAnswer = AutoOrganizeService.showCorrectAnswer

			state.getStatisticPage = (challengeId) => {
				router.push({
					name: "organize.statistic",
					query: {
						challengeId: challengeId
					}
				})
			}
			state.getRankTablePage = (challengeId) => {
				router.push({
					name: "organize.rank",
					query: {
						challengeId: challengeId
					}
				})
			}
			state.getNextQuestionPage = (challengeId) => {
				router.push({
					name: "organize.get",
					query: {
						challengeId: challengeId
					}
				})
			}

			// state.challenge = data.challenge
			// state.question = data.question
			// state.answers = data.answers
			// state.questionToken = data.questionToken




		},
		disableAutoOrganize(state) {
		state.publishNextQuestion = (challengeId) =>{ /* console.log("disable auto next", challengeId)*/}
			state.getCorrectAnswer = (questionId) => {/* console.log("disable auto show", questionId)*/}

			state.getStatisticPage = (challengeId) => {/*  console.log("disable route to statistic page", challengeId)*/ }
			state.getRankTablePage = (challengeId) => {/*  console.log("disable route to rank table page", challengeId)*/ }
			state.getNextQuestionPage = (challengeId) => { /* console.log("disable route to next question page", challengeId) */}


		},
		selectAnswer(state, item) {

			if (state.selectedAnswerIds.includes(item.id)) {
				state.selectedAnswerIds = state.selectedAnswerIds.filter(e => e !== item.id);
			} else {
				state.selectedAnswerIds.push(item.id);
			}

		}


	}
})

export default store;