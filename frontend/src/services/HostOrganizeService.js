import Api from "@/services/Api";

export default {

    getStudentsOnline(params) {
        return Api().get("host/studentOnline", {
            params: params
        });
    },
    publishQuestion(params) {
        return Api().get("host/publishQuestion", {
            params: params
        })
    },
    showCorrectAnswer(params) {
        return Api().get("host/showCorrectAnswer", {
            params: params
        })
    },
    getTopStudent(params) {
        return Api().get("host/getTopStudent", {
            params: params
        })
    },
    publishNextQuestion(params) {
        return Api().get("host/publishNextQuestion", {
            params: params
        })
    },
    startChallenge(params) {
        return Api().get("host/startChallenge", {
            params: params
        })
    },
    endChallenge(params) {
        return Api().get("host/endChallenge", {
            params: params
        })
    },
    getCurrentQuestion(params) {
        return Api().get("host/getCurrentQuestion", {
            params: params
        })
    },
    kickStudent(data) {
        return Api().post("host/kickStudent",data);
    }



};
