import Api from "@/services/Api";

export default {
    joinRoom(params) {
        return Api().get("student/joinRoom", {
            params: params
        });
    },
    sentAnswer(data) {
        return Api().post("student/sendAnswer", data);
    }

};
