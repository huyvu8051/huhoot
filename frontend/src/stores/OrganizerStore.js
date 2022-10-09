import {defineStore} from 'pinia'

export const useOrganizerStore = defineStore('organizer', {
    state: () => ({
        challenge: {
            id: 0,
            challengeStatus: "",
            title: ""
        },
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
            theLastQuestion: false
        },
        answers: [{
            id: 0,
            content: "",
            isCorrect: false
        }],
        participantsRank: [{
            fullName: "",
            id: 0,
            totalPoint: 0,
            username: "",
        }]
    }),
    actions: {
        update(input) {
            console.log("update org", input)
            let state = this;
            state = Object.assign(state, input)

            if (input.corrects) {
                input.corrects.forEach(correct => {
                    const answer = state.answers.find(answer => answer.id = correct);
                    answer.isCorrect = true;
                })
            }

        }
    },
    persist: {
        enabled: true
    }
})