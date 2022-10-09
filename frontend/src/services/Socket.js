import io from "socket.io-client";

import {useAuthStore} from "@/stores/AuthStore";
import {useChallengeStore} from "@/stores/ChallengeStore";

const authStore = useAuthStore();
const challengeStore = useChallengeStore();

function connect(challengeId) {

    const socket = io("http://localhost:8082", {
        query: {
            challengeId: challengeId
        },
        transportOptions: {
            polling: {
                extraHeaders: {
                    'Authorization': 'Bearer ' + authStore.jwt
                }
            }
        }
    });

    socket.on("connected", challengeStore.connected);
    socket.on("showCorrectAnswer", challengeStore.showCorrectAnswer);
    socket.on("publishQuestion", challengeStore.publishQuestion)

    return socket

}

export default connect;

