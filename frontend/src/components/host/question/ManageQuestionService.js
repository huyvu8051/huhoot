import Api from "@/services/Api";

export default {
    findAll(data) {
        return Api().post("host/question/findAll", data);
    },
    update(data) {
        return Api().patch("host/question", data);
    },
    add(data) {
        return Api().post("host/question", data);
    },
   
}