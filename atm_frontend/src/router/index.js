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
  },
  {
    path: '/usercheckpass/:cardId',
    name: 'usercheckpass',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserCheckPassView.vue')
  },
  {
    path: '/useroperation',
    name: 'useroperation',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserOperationView.vue')
  },
  {
    path: '/userstorermb',
    name: 'userstorermb',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserStoreRMBView.vue')
  },
  {
    path: '/userstoresuccess/:storeNum/:recordid',
    name: 'userstoresuccess',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserStoreSuccessView.vue')
  },
  {
    path: '/usertakermb',
    name: 'usertakermb',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserTakeRMBView.vue')
  },
  {
    path: '/usertakesuccess/:takeAmount/:recordid',
    name: 'usertakesuccess',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserTakeSuccessView.vue')
  },
  {
    path: '/usercheckbalance',
    name: 'usercheckbalance',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserCheckBalanceView.vue')
  },
  {
    path: '/userchangepassword',
    name: 'userchangepassword',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserChangePasswordView.vue')
  },
  {
    path: '/usertransfer',
    name: 'usertransfer',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserTransferView.vue')
  },
  {
    path: '/usergetallrecord',
    name: 'usergetallrecord',
    component: () => import(/* webpackChunkName: "desktop" */ '../views/UserGetAllRecordView.vue')
  }
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
