import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout/index'

Vue.use(Router)
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

// 定义静态路由
const staticRoutes = [
  {
    path: '/',
    name: 'home',
    redirect: '/home',
    meta: {
      breadcrumb: false,
      nav: 'home',
      title: '首页'
    },
    component: () => import('@/views/hetong'),
    children: [
      {
        name: 'homeIndex',
        path: '/home',
        component: () => import('@/views/note'),
        meta: {
          nav: 'home',
          title: '首页'
        },
      },
      {
          name: 'note-editor',
          path: '/note-editor',
          component: () => import('@/views/note/note-editor'),
          meta: {
          nav: 'note-editor',
              title: '笔记'
          },
      }
    ]
  }
]

// 实例化 Router 对象
const router = new Router({
  scrollBehavior: () => ({
    y: 0
  }),
  routes: staticRoutes
})

// 定义全局前置守卫
router.beforeEach((to, from, next) => {
  next()
})

export default router
