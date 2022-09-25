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

  findAllQuestion(data) {
    return Api().post("host/question/findAll", data);
  },
  updateQuestion(data) {
    return Api().patch("host/question", data);
  },
  updateOrdinal(data) {
    return Api().patch("host/question/ordinal", data);
  },
  addQuestion(data) {
    return Api().post("host/question", data);
  },

  findAllChallenge(data) {
    return Api().post("host/challenge/findAll", data);
  },
  updateChallenge(data) {
    return Api().patch("host/challenge", data);
  },

  addChallenge(data) {
    return Api().post("host/challenge", data);
  },
  openChallenge(params) {
    return Api().get("host/openChallenge", {
      params: params
    });
  },


  upload(data) {
    return Api().post("host/upload", data, {
      headers: {
        'content-type': 'multipart/form-data'
      }
    });
  },

  findAllStudent(data) {
    return Api().post("host/student", data);
  },

  findAllStudentInChallenge(data) {
    return Api().post("host/studentChallenge/findAll", data);
  },
  addStudentInChallenge(data) {
    return Api().post("host/studentChallenge/add", data);

  },
  updateStudentInChallenge(data) {
    return Api().patch("host/studentChallenge", data);

  }
};
