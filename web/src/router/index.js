import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: 'dashboard',
    children: [{
      path: 'dashboard',
      name: '首页',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  }
]
export const asyncRoutes = [
  {
    path: '/laboratory/',
    component: Layout,
    redirect: '/laboratory/laboratoryIndex',
    name: 'laboratory',
    alwaysShow: true,
    meta: { title: '实验管理', icon: 'el-icon-s-help', roles:['admin']},
    children: [
      {
        path: 'laboratoryIndex',
        name: 'laboratoryIndex',
        component: () => import('@/views/laboratory/index'),
        meta: { title: '实验资源' }
      },
      {
        path: 'laboratoryInfo',
        name: 'laboratoryInfo',
        component: () => import('@/views/laboratoryInfo/index'),
        meta: { title: '实验试题' }
      }
    ]
  },
  {
    path: '/procedure/',
    component: Layout,
    redirect: '/procedure/laboratoryAppointment',
    name: 'procedure',
    alwaysShow: true,
    meta: { title: '实验预约管理', icon: 'el-icon-s-help'},
    children: [
      {
        path: 'laboratoryAppointment',
        name: 'laboratoryAppointment',
        component: () => import('@/views/laboratoryAppointment/laboratoryAppointment'),
        meta: { title: '实验预约' , roles: ['student'] }
      },
      {
        path: 'myProcedure',
        name: 'myProcedure',
        component: () => import('@/views/laboratoryAppointment/myProcedure'),
        meta: { title: '我的预约', roles: ['student'] }
      },
      {
        path: 'laboratoryAssess',
        name: 'laboratoryAssess',
        component: () => import('@/views/laboratoryAppointment/laboratoryAssess'),
        meta: { title: '实验评估', roles: ['teacher'] }
      },
      {
        path: 'laboratoryAdmin',
        name: 'laboratoryAdmin',
        component: () => import('@/views/laboratoryAppointment/laboratoryAdmin'),
        meta: { title: '实验统计', roles: ['admin'] }
      },
      {
        path: 'laboratoryPrepare/:id/:appointmentId',
        name: 'laboratoryPrepare',
        hidden: true,
        component: () => import('@/views/laboratoryAppointment/laboratoryPrepare'),
        meta: { title: '准备实验' }
      },
      {
        path: 'laboratoryStart/:id/:appointmentId',
        name: 'laboratoryStart',
        hidden: true,
        component: () => import('@/views/laboratoryAppointment/laboratoryStart'),
        meta: { title: '开始实验' }
      },
      {
        path: 'laboratoryScore/:id',
        name: 'laboratoryScore',
        hidden: true,
        component: () => import('@/views/laboratoryAppointment/laboratoryScore'),
        meta: { title: '实验评估' }
      }
    ]
  },
  // 公告管理
  {
    path: '/notice',
    redirect: '/notice/list', // 默认访问地址
    component: Layout,
    alwaysShow: true,
    meta: { title: '公告管理', icon: 'dr' , roles: ['admin'] },
    children: [
      {
        path: 'list',
        name: '公告信息',
        component: () => import('@/views/notice/index'),
        meta: { title: '公告信息'}
      }
    ]
  },
  // 系统管理
  {
    path: '/system',
    redirect: '/system/user', // 默认访问地址
    component: Layout,
    meta: { title: '系统管理', icon: 'dr' },
    children: [
      {
        path: 'user',
        name: '用户信息',
        component: () => import('@/views/system/user/index'),
        meta: { title: '用户信息', roles: ['admin']}
      },
      {
        path: 'updatePassword',
        name: '密码修改',
        component: () => import('@/views/system/updatePassword'),
        meta: { title: '密码修改' }
      },
      {
        path: 'info',
        name: '个人中心',
        component: () => import('@/views/system/info'),
        meta: { title: '个人中心' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]
const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
