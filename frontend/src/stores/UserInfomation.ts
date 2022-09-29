import {defineStore} from 'pinia'

export interface UserInfo {
    username?: string,
    jwt?: string,
    roles?: string[],
    fullName?: string
}


export const useUserInformationStore = defineStore('userInformationStore', {
    state: () => (
        {
            username: "",
            jwt: "",
            roles: [""],
            fullName: ""
        }
    ),
    actions: {
        saveUserInfo(username: string, jwt: string, roles: string[], fullName: string) {
            this.username = username;
            this.jwt = jwt;
            this.roles = roles;
            this.fullName = fullName;
        }
    },
    persist: {
        enabled: true
    }
})