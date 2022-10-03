import {defineStore} from 'pinia'

export const useParticipantStore = defineStore('participant', {
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
    }),
    actions: {
        update(input) {
            console.log("update par", input)
            let state = this;
            state = Object.assign(state, input)

            if (input.corrects) {
                input.corrects.forEach(correct => {
                    const answer = state.answers.find(answer => answer.id = correct);
                    answer.isCorrect = true;
                })
            }

        },
        publishQuestion(data){
            this.question = data.question;
            this.answers = data.answers;
            this.isConfirmed = false;

        },
        connected(data) {
            if (this.question.id != data.question.id) {
                this.answers = data.answers;
                this.question = data.question;
            }
        },

        showCorrectAnswer(data) {
            data.corrects.forEach(e => {
                const find = this.answers.find(a => a.id == e.id);
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