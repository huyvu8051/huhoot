import Vue from "vue";
import Router from "vue-router";
import ListChallenge from "@/components/student/ListChallenge";
import Participate from "@/components/participate/Participate";
import HostLayout from "@/components/host/HostLayout";
import HostListChallenge from "@/components/host/challenge/ListChallenge";

import Store from "@/store/store";
Vue.use(Router);

const router = new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "login",
      component: () => import("@/components/plugin/LoginBoxed")
    },


    // host


    {
      path: "/host/",
      component: HostLayout,
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: "challenge",
          name: "HOST",
          component: HostListChallenge
        },
        {
          path: "question2",
          name: "host.listQuestion2",
          component: () => import("@/components/host/question/ListQuestion2")
        },
        {
          path: "rank",
          name: "host.rank",
          component: () => import("@/components/challengeRank/ChallengeRank")
        },
        {
          path: "question",
          name: "host.listQuestion",
          component: () => import("@/components/host/question/ListQuestion")
        },
        {
          path: "answer",
          name: "host.listAnswer",
          component: () => import("@/components/host/answer/ListAnswer")
        },
        {
          path: "studentInChallenge",
          name: "host.listStudentInChallenge",
          component: () =>
            import(
              "@/components/host/studentInChallenge/ListStudentInChallenge"
            )
        },
        {
          path: "student",
          name: "host.student",
          component: () =>
            import("@/components/host/studentInChallenge/ListStudent")
        },
        {
          path: "account",
          name: "host.account",
          component: () => import("@/components/host/Account")

        },
        {
          path: "/my-library",
          name: "myLibrary",
          // component: () => import("@/components/plugin/LoginBoxed")
        },
        {
          path: "/creator/:challengeId",
          name: "creator",
          // component: () => import("@/components/plugin/LoginBoxed")
        },



        { path: "*", redirect: "/host/challenge" }

      ]
    },
    {
      path: "/organize/",
      component: () => import("@/components/organize/Organize"),
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: "wait",
          name: "organize.wait",
          component: () => import("@/components/organize/wait/Wait")
        },
        {
          path: "start",
          name: "organize.start",
          component: () => import("@/components/organize/start/Start")
        },
        {
          path: "get",
          name: "organize.get",
          component: () => import("@/components/organize/get/GetQuestion")
        },
        {
          path: "ready",
          name: "organize.ready",
          component: () => import("@/components/organize/ready/Ready")
        },
        {
          path: "preview",
          name: "organize.preview",
          component: () => import("@/components/organize/preview/Preview")
        },
        {
          path: "ask",
          name: "organize.ask",
          component: () => import("@/components/organize/ask/Ask")
        },
        {
          path: "show",
          name: "organize.show",
          component: () => import("@/components/organize/show/Show")
        },
        {
          path: "statistic",
          name: "organize.statistic",
          component: () => import("@/components/organize/statistic/Statistic")
        },
        {
          path: "rank",
          name: "organize.rank",
          component: () => import("@/components/organize/rank/Rank")
        },
        {
          path: "finish",
          name: "organize.finish",
          component: () => import("@/components/organize/finish/Finish")
        },
        {
          path: "podium",
          name: "organize.podium",
          component: () => import("@/components/organize/finish/Podium")
        },
        {
          path: "*",
          redirect: "/organize/wait"
        }
      ]
    },

    // student
    {
      path: "/student",
      component: () => import("@/components/student/Layout"),
      redirect: "/student/challenge",
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: "challenge",
          name: "STUDENT",
          component: ListChallenge
        },
        {
          path: "account",
          name: "student.account",
          component: () => import("@/components/student/Account")
        }
      ]
    },

    {
      path: "/participate",
      component: Participate,
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: "wait",
          name: "participate.wait",
          component: () => import("@/components/participate/wait/Wait")
        },
        {
          path: "start",
          name: "participate.start",
          component: () => import("@/components/participate/start/Start")
        },
        {
          path: "ready",
          name: "participate.ready",
          component: () => import("@/components/participate/ready/Ready")
        },
        {
          path: "preview",
          name: "participate.preview",
          component: () => import("@/components/participate/preview/Preview")
        },
        {
          path: "ask",
          name: "participate.ask",
          component: () => import("@/components/participate/ask/Ask")
        },
        {
          path: "show",
          name: "participate.show",
          component: () => import("@/components/participate/show/Show")
        },
        {
          path: "finish",
          name: "participate.finish",
          component: () => import("@/components/participate/finish/Finish")
        }
      ]
    },

    // admin
    {
      path: "/admin",
      component: () => import("@/components/admin/Layout"),

      redirect: '/admin/challenge',

      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: "student",
          name: "ADMIN",
          component: () => import("@/components/admin/ListStudent")
        },
        {
          path: "edit-challenge/:challengeId",
          name: "editChallenge",
          component: () => import("@/views/details/EditChallengeView")
        },
        {
          path: "host",
          name: "admin.host",
          component: () => import("@/components/admin/ListHost")
        },
        {
          path: "account",
          name: "admin.account",

          component: () => import("@/components/admin/Account"),
        },
        {
          path: "challenge",
          name: "admin.challenge",
          component: () => import("@/views/admin/AdminListChallenge"),
        },
        // details
        {
          path: "student-details/:username",
          name: "studentDetails",
          component: () => import("@/views/details/StudentDetailsView")
        },
        {
          path: "admin-details/:username",
          name: "adminDetails",
          component: () => import("@/views/details/AdminDetailsView")
        },
        {
          path: "student-reports/:challengeId/:username",// xem ket qua tra loi cua sinh vien dok
          name: "studentReports",

          component: () => import("@/views/details/StudentReportsView.vue")
        },
        {
          path: "challenge-reports/:challengeId",// danh sach cau hoi, hien thi so luong nguoi dung, sai, chon dap an nao
          name: "challengeReports",

          //component: () => import("@/components/plugin/LoginBoxed")
        },

        {
          path: "challenge-rank/:challengeId",// xep hang cac sinh vien 
          name: "challengeRank",
          component: () => import("@/views/details/ChallengeRankView.vue")
        },
        {
          path: "participants/:challengeId",
          name: "participants",

          component: () => import("@/views/details/ParticipantsView.vue")
        },


      ]
    },

    //*
    {
      path: "/*",
      name: 404,
      component: () => import("@/components/plugin/404Page")
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!Store.state.token) {
      next({
        name: "login"
      });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;