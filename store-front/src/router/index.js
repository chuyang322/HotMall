import Vue from 'vue'
import Router from 'vue-router'
import routes from './routes'
Vue.use(Router)

let router = new Router({
  routes
})

// 全局路由守卫
router.beforeEach((to, from, next) => {
  console.log(to, from)
  if (to.meta.title) {
    document.title = `${to.meta.title}`
  }
  next()
})

router.afterEach((to, from) => {
  console.log(to, from)
  console.log('afterEach')
})
export default router
