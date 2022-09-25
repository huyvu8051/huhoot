import Api from "@/services/Api";
import store from '@/store/store'

var publishNextQuestion = (challengeId) => {
    console.log("Sent to BE auto Publish next question");
    return Api().get("autoOrganize/publishNextQuestion", {
        params: {
            challengeId: challengeId
        }
    })
}
var showCorrectAnswer = (questionId) => {
    console.log("Sent to BE auto Show correct answer");
    return Api().get("autoOrganize/showCorrectAnswer", {
        params: {
            questionId: questionId
        }
    })
}
var enableAutoOrganize = (challengeId) => {
    console.log("Sent to BE Enable auto organize");
    return Api().get("autoOrganize/enableAutoOrganize", {
        params: {
            challengeId: challengeId
        }
    })
}
var disableAutoOrganize = (challengeId) => {
    console.log("Sent to BE Disable auto organize");
    return Api().get("autoOrganize/disableAutoOrganize", {
        params: {
            challengeId: challengeId
        }
    })
}



export default {
    publishNextQuestion,
    showCorrectAnswer,
    enableAutoOrganize,
    disableAutoOrganize,
   

};