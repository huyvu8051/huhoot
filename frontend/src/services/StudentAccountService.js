import Api from "@/services/Api";

export default {
    updatePassword(data) {
        return Api().post("student/account", data);
    },
};
