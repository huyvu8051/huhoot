import {authorizationStore} from "@/stores/authorization";
import axios from "axios";

export default () => {
    let instance = axios.create({
        headers: {
            Authorization: `${authorizationStore.$id}`,
        }
    });

    instance.interceptors.request.use((request) => {

        return request;

    }, (error) => {
        return Promise.reject(error);

    });

    instance.interceptors.response.use(response => {
        return response;
    }, err => {
        return Promise.reject(err);

    })

    return instance;
}