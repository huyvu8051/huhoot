import {useCustomerAuthInfoStore} from "@/stores/CustomerAuthInfo";
import axios from "axios";

const {jwt} = useCustomerAuthInfoStore();

let instance = axios.create({
    baseURL: "/api",
    headers: {
        Authorization: "Bearer " + `${jwt}`,
    }
});

instance.interceptors.request.use(req => {
    // console.log(req);
    return req;
}, error => Promise.reject(error));

instance.interceptors.response.use(resp => {

    let customBodyRespDTO = resp.data;

    switch (customBodyRespDTO.status) {
        case 200:
        case 201:
            return customBodyRespDTO;
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
