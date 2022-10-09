import {defineStore} from 'pinia'

export const useChallengeStore = defineStore('challenge', {
    state: () => ({
        challenge: {
            id: 0,
            challengeStatus: "",
            title: ""
        },
        questionToken: "",
        question: {
            id: 0,
            content: "",
            image: "",
            timeLimit: 0,
            askDate: 0,
            timeout: 0,
            challengeId: 0,
            totalQuestion: 0,
            questionOrder: 0,
        },
        answers: [{
            id: 0,
            content: "",
            isSelected: false,
            isCorrect: false,
            isConfirmed: false
        }],
        isConfirmed: false,
        participantsRank: [{
            id: 0,
            username: '',
            fullName: '',
            totalPoint: 0
        }]
    }),
    actions: {

        publishQuestion(data) {
            this.isConfirmed = false;
            console.log("publishQuestion", data)
            this.question = data.question;
            this.answers = data.answers;
            this.isConfirmed = false;

        },
        connected(data) {

            if (data.question) {
                this.answers = data.answers;
                this.question = data.question;
            }
            this.challenge = data.challenge;
        },

        saveParticipantsRank(data) {
            this.participantsRank = data.participantsRank

        },

        showCorrectAnswer(data) {
            data.corrects.forEach(correctAnswerId => {
                const find = this.answers.find(a => a.id == correctAnswerId);
                if (find) {
                    find.isCorrect = true;
                }
            })

        },
        select(id) {
            const answer = this.answers.find(e => e.id == id);
            if (answer == null) return;
            if (answer.isSelected == null || answer.isSelected == undefined) {
                answer.isSelected = false;
            }
            answer.isSelected = !answer.isSelected;
        },
        submit() {
            this.answers.forEach(e => e.isConfirmed = e.isSelected)
            this.isConfirmed = true;
        }
    }
    ,
    persist: {
        enabled: true
    }
})