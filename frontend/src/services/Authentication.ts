import Api from "@/services/Api";
import {reactive} from "vue";


interface AuthenticationReqDTO {
    username: string,
    password: string
};

interface AuthenticationRespDTO {
    jwt: string,
    username: string,
    authorities: string[]
}

function authenticate(onSuccess: Function = (resp:any) => {
}, onError: Function = (err:any) => {
}) {

    const authenticationReqDTO = reactive<AuthenticationReqDTO>({
        username: "",
        password: ""
    })

    const submit = () => Api().post<AuthenticationReqDTO>("/authentication", authenticationReqDTO)
        .then(resp => onSuccess(resp))
        .catch(err => onError(err))

    return {
        authenticationReqDTO,
        submit
    }
}

export type{
    AuthenticationRespDTO
}

export {
    authenticate
}

