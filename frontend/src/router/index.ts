import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/Login.vue'),
        },
        {
            path: '/customer',
            component: () => import("@/views/customer/Customer.vue"),
            redirect: {name: "customer.home"},
            children: [{
                path: "home",
                name: "customer.home",
                component: () => import("@/views/customer/Home.vue")
            }]
        },
        {
            path: '/organizer',
            component: () => import('../views/organizer/Organizer.vue'),
            redirect: {name: "organizer.home"},
            children: [

                {
                    path: ":id/lobby",
                    name: "organizer.lobby",
                    component: () => import("@/views/organizer/Lobby.vue")
                },
                {
                    path: ":id/start",
                    name: "organizer.start",
                    component: () => import("@/views/organizer/Start.vue")
                },
                {
                    path: "preview",
                    name: "organizer.preview",
                    component: () => import("@/views/organizer/Preview.vue")
                },
                {
                    path: "answer",
                    name: "organizer.answer",
                    component: () => import("@/views/organizer/Answer.vue")
                },
                {
                    path: "result",
                    name: "organizer.result",
                    component: () => import("@/views/organizer/Result.vue")
                }
            ]
        }
    ]
})

export default router
