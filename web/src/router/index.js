import { createRouter, createWebHistory } from 'vue-router'
import MyhomeIndexView from '../views/MyhomeIndexView.vue'
import RegisterIndexView from '../views/RegisterIndexView.vue'
import LoginIndexView from '../views/LoginIndexView.vue'
import PasswordRecoveryIndexView from '../views/PasswordRecoveryIndexView.vue'

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/myhome/",
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/myhome/",
    name: "myhome_index",
    component: MyhomeIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/register/",
    name: "register_index",
    component: RegisterIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/login/",
    name: "login_index",
    component: LoginIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/passwordRecovery/",
    name: "passwordRecovery_index",
    component: PasswordRecoveryIndexView,
    meta: {
      requestAuth: false,
    }
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
