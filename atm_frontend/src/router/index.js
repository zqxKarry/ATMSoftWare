import Vue from 'vue'
import VueRouter from 'vue-router'
import DeskTopView from '../views/DeskTopView.vue'

Vue.use(VueRouter)

const routes = [
  {
    /* 默认路径，运行开始就在这个界面 */
    path: '/',
    name: 'desktop',
    component: DeskTopView
  },
  {
    path: '/admincheckid',
    name: 'admincheckid',
    component: () => import('../views/AdminCheckIdView.vue')
  },
  {
    path: '/admincheckpass/:adminId',
    name: 'admincheckpass',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/AdminCheckPassView.vue')
  },
  {
    path: '/adminoperation',
    name: 'adminoperation',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/AdminOperationView.vue')
  },
  {
    path: '/admincheckbalance',
    name: 'admincheckbalance',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/AdminCheckBalanceView.vue')
  },
  {
    path: '/adminaddpaper',
    name: 'adminaddpaper',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/AdminAddPaperView.vue')
  },
  {
    path: '/adminaddrmb',
    name: 'adminaddrmb',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/AdminAddRMBView.vue')
  },
  {
    path: '/admintakermb',
    name: 'admintakermb',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/AdminTakeRMBView.vue')
  }
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
