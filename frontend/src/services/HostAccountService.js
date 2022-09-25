import Api from "@/services/Api";

export default {
    updatePassword(data) {
        return Api().put("host/account", data);
    },
};
