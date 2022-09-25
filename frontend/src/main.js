// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from '@/store/store'

import vuetify from './plugins/vuetify'
import Vuelidate from 'vuelidate'
import EventBus from "@/EventBus"




// sweet
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
Vue.use(VueSweetalert2);


import VueExcelXlsx from "vue-excel-xlsx";
Vue.use(VueExcelXlsx);


import resize from "vue-element-resize-detector";
Vue.use(resize)

Vue.config.productionTip = false

/* eslint-disable no-new */

Vue.use(Vuelidate)
Vue.prototype.$eventBus = EventBus;


import GlobalComponents from "./plugins/GlobalComponents"
Vue.use(GlobalComponents);

import Nofication from "./plugins/Nofication"
Vue.use(Nofication);

new Vue({
  el: '#app',
  router,
  store,
  vuetify,
  components: { App },
  template: '<App/>'
})
