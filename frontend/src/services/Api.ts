import {authorizationStore} from "@/stores/authorization";
import axios from "axios";

interface CustomBodyResponseDTO {
    status: number,
    message?: string,
    data?: object
}

let instance = axios.create({
    baseURL: "/api",
    headers: {
        Authorization: `${authorizationStore.$id}`,
    }
});

instance.interceptors.request.use(req => {
    console.log(req);
    return req;
}, error => Promise.reject(error));

instance.interceptors.response.use(resp => {

    let customBodyRespDTO: CustomBodyResponseDTO = resp.data;

    switch (customBodyRespDTO.status) {
        case 200:
        case 201:
            return customBodyRespDTO.data;
            break;
        case 400:
        case 401:
        case 403:

            return Promise.reject(customBodyRespDTO);
            break;

        default :
            console.log(resp.data);
            break;
    }


}, err => Promise.reject(err))

export default () => {
    return instance;
}

