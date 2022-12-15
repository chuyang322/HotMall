
const main = [
  {
    path: '/',
    name: 'Main',
    component: () =>
      import(/* webpackChunkName: "Main" */ '../pages/Main'),
    children: [
      {
        path: '/',
        name: 'Hello',
        component: () =>
          import(/* webpackChunkName: "Hello" */ '../components/Hello')
      }
    ]
  }]
const User = [
  {
    path: '/User',
    name: 'User',
    component: () =>
      import('../pages/User'),
    children: [
      {
        path: '/ModifyInfo',
        name: 'ModifyInfo',
        component: () =>
          import('../components/ModifyInfo')
      },
      {
        path: '/Car',
        name: 'CarGoods',
        component: () =>
          import('../components/CarGoods')
      },
      {
        path: '/MyMall',
        name: 'MyMall',
        component: () =>
          import('../components/MyMall')
      },
      {
        path: '/Orders',
        name: 'Orders',
        component: () =>
          import('../components/Orders')
      },
      {
        path: '/MyOrder',
        name: 'MyOrder',
        component: () =>
          import('../components/MyOrder')
      },
      {
        path: '/MyAddress',
        name: 'MyAddress',
        component: () =>
          import('../components/MyAddress')
      }
    ]
  }
]
export default [...main, ...User]
