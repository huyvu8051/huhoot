import Api from "@/services/Api";

export default {
    findAllChallenge() {
        return Api().get("student/challenge", {
            params: {
                size: 999
            }
        });
    },

};
