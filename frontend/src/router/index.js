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
            path: '/organizer/:challengeId',
            component: () => import('../views/organizer/Organizer.vue'),
            redirect: {name: "organizer.lobby"},
            children: [

                {
                    path: "lobby",
                    name: "organizer.lobby",
                    component: () => import("@/views/organizer/Lobby.vue")
                },
                {
                    path: "start",
                    name: "organizer.start",
                    component: () => import("@/views/organizer/Start.vue")
                },
                {
                    path: "request",
                    name: "organizer.request",
                    component: () => import("@/views/organizer/Request.vue")
                },
                {
                    path: "preview",
                    name: "organizer.preview",
                    component: () => import("@/views/organizer/Preview.vue")
                },
                {
                    path: "ask",
                    name: "organizer.ask",
                    component: () => import("@/views/organizer/Ask.vue")
                },
                {
                    path: "result",
                    name: "organizer.result",
                    component: () => import("@/views/organizer/Result.vue")
                }
            ]
        },
        {
            path: '/participant/:challengeId',
            component: () => import('../views/participant/Participant.vue'),
            redirect: {name: "participant.lobby"},
            children: [
                {
                    path: "lobby",
                    name: "participant.lobby",
                    component: () => import("@/views/participant/Lobby.vue")
                },
                {
                    path: "start",
                    name: "participant.start",
                    component: () => import("@/views/participant/Start.vue")
                },
                {
                    path: "preview",
                    name: "participant.preview",
                    component: () => import("@/views/participant/Preview.vue")
                },
                {
                    path: "ask",
                    name: "participant.ask",
                    component: () => import("@/views/participant/Ask.vue")
                }

            ]
        }
    ]
})

export default router