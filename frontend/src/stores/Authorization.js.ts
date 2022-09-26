import {defineStore} from 'pinia'

export interface UserInfo {
    username?: string,
    jwt?: string,
    roles?: string[],
    fullName?: string
}


export const authorizationStore = defineStore('authorizationStore', {
    state: () => {

        let state: UserInfo = {
            username: "",
            jwt: "",
            roles: [],
            fullName: ""
        }

        return state;
    },
    // could also be defined as
    // state: () => ({ count: 0 })
    actions: {
        saveUserInfo(username: string, jwt: string, roles: string[], fullName: string) {
            this.username = username;
            this.jwt = jwt;
            this.roles = roles;
            this.fullName = fullName;
        }
    },
})