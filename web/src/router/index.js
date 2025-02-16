import { createRouter, createWebHistory } from 'vue-router'
import MyhomeIndexView from '../views/MyhomeIndexView.vue'
import RegisterIndexView from '../views/RegisterIndexView.vue'
import LoginIndexView from '../views/LoginIndexView.vue'
import PasswordRecoveryIndexView from '../views/PasswordRecoveryIndexView.vue'
import ContestsIndexView from '../views/ContestsIndexView.vue'
import GymIndexView from '../views/GymIndexView.vue'
import RatingIndexView from '../views/RatingIndexView.vue'
import HelpIndexView from '../views/HelpIndexView.vue'
import GameIndexView from '../views/GameIndexView.vue'
import ProvinceRatingIndexView from '../views/ProvinceRatingIndexView.vue'
import CityRatingIndexView from '../views/CityRatingIndexView.vue'
import SettingsIndexView from '../views/SettingsIndexView.vue'
import ProfileIndexView from '../views/ProfileIndexView.vue'
import StandingsIndexView from '../views/StandingsIndexView.vue'
import ContestIndexView from '../views/ContestIndexView.vue'
import OfficialContestIndexView from '../views/OfficialContestIndexView.vue'

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
  {
    path: "/contests/",
    name: "contests_index",
    component: ContestsIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/gym/",
    name: "gym_index",
    component: GymIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/rating/",
    name: "rating_index",
    component: RatingIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/help/",
    name: "help_index",
    component: HelpIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/game/",
    name: "game_index",
    component: GameIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/provinceRating/",
    name: "provinceRating_index",
    component: ProvinceRatingIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/cityRating/",
    name: "cityRating_index",
    component: CityRatingIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/settings/",
    name: "settings_index",
    component: SettingsIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/profile/:username/",
    name: "profile_index",
    component: ProfileIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/standings/:contestId/",
    name: "standings_index",
    component: StandingsIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/contest/:contestId/",
    name: "contest_index",
    component: ContestIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/officialContest/:contestId/:topicId/",
    name: "officialContest_index",
    component: OfficialContestIndexView,
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
