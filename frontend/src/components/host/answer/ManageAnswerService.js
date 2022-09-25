import Api from "@/services/Api";

export default {
    findAllAnswer(data) {
        return Api().post("host/answer/findAll", data);
    },
    updateAnswer(data) {
        return Api().patch("host/answer", data);
    },
    addAnswer(data) {
        return Api().post("host/answer", data);
    },
    updateOrdinal(data){
        return Api().patch("host/answer/ordinal", data);
    }
}