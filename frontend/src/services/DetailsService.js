import Api from "@/services/Api";

export default {

    getStudentDetails(data) {
        return Api().post("/details/student",data);
    },
    getChallengeDetails(challengeId) {
        return Api().get("/details/challenge",{
            params: {
                challengeId: challengeId
            }
        });
    },
    getStudentReports(data) {
        return Api().post("/details/student-reports",data);
    },
    getAdminDetails(body) {
        return Api().post("/details/admin", body);
    },
    getAllParticipants(body) {
        return Api().post("/details/participants", body);
    },
    getChallengeRank(params) {
        return Api().get("/details/challenge-rank", {
            params: params
        });
    },
}