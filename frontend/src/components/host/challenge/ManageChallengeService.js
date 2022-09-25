import Api from "@/services/Api";

export default {
    findAllChallenge(data) {
        return Api().post("host/challenge/findAll", data);
    },
    updateChallenge(data) {
        return Api().patch("host/challenge", data);
    },
    addChallenge(data) {
        return Api().post("host/challenge", data);
    },
}