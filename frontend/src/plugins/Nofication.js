
import EventBus from "@/EventBus"

const Nofication = {
    install(app) {
        app.prototype.$success = (message) => {
            EventBus.$emit("nofication", {
                message: message,
                status: "success",
            });
        };

        app.prototype.$error = (message) => {
            EventBus.$emit("nofication", {
                message: message,
                status: "error",
            });
        };

        app.prototype.$warning = (message) => {
            EventBus.$emit("nofication", {
                message: message,
                status: "warning",
            });
        };

    }
}

export default Nofication;