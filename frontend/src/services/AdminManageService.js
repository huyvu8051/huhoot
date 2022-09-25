import Api from "@/services/Api";

export default {

    findAllStudent(data) {
        return Api().post("admin/student/findAll", data);
    },

    updateStudent(data) {
        return Api().patch("admin/student", data);
    },
    resetPasswordStudent(data) {
        return Api().post("admin/resetPasswordStudent", data);
    },
    addStudent(data) {
        return Api().post("admin/student", data);
    },


    findAllHost(data) {
        return Api().post("admin/host/findAll", data);
    },
    addHost(data) {
        return Api().post("admin/host", data);
    },
    updateHost(data) {
        return Api().patch("admin/host", data);
    },

    resetPasswordAdmin(data) {
        return Api().post("admin/resetPasswordAdmin", data);
    },


    // manage challenge
    getAllChallenge(data) {
        return Api().post("admin/challenge/findAll", data);
    },
    updateChallenge(data) {
        return Api().patch("admin/challenge", data);
    },

    addChallenge(data) {
        return Api().post("admin/challenge", data);
    },




};