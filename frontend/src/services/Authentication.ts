import Api from "@/services/Api";
import {reactive} from "vue";
import type {AxiosResponse} from "axios";
import {useUserInformationStore} from "@/stores/UserInfomation";
import router from "@/router";

const {saveUserInfo} = useUserInformationStore();

interface AuthenticationReqDTO {
    username: string,
    password: string
}

interface AuthenticationRespDTO {
    jwt: string,
    username: string,
    authorities: string[],
    fullName: string
}

function authenticate(onError: Function = (err: any) => {
}) {

    const authenticationReqDTO = reactive<AuthenticationReqDTO>({
        username: "",
        password: ""
    })

    function submit() {
        Api().post("/authentication", authenticationReqDTO)
            .then((resp: AxiosResponse<AuthenticationRespDTO>) => {
                router.push({name: "customer.home"}).then();
                saveUserInfo(resp.data.username, resp.data.jwt, resp.data.authorities, resp.data.fullName);
            })
            .catch(err => onError(err))
    }

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

