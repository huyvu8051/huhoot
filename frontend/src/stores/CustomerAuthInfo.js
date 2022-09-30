import {defineStore} from 'pinia'

export const useCustomerAuthInfoStore = defineStore('customerAuthInfo', {
    state: () => ({
        username: "",
        jwt: "",
        roles: [],
        fullName: ""
    }),
    actions: {
        updateAuthInfo(authResp) {
            let state = this;
            state = Object.assign(state, authResp)
        }
    },
    persist: {
        enabled: true
    }
})