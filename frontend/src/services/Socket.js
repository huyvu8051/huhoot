
import io from "socket.io-client"

const socket = io("http://localhost:8082")
socket.on("connected", (message) => {
    socket.send("messageEvent")
})

socket.on("abc", (msg) => {
    console.log(msg);
})

function sentSt(){
    socket.emit("messageEvent")
}

function Socket(){


    return{

    }
}


export default Socket