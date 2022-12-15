<template>
  <div>
<GoodsClass></GoodsClass>
    <GoodsList></GoodsList>
  </div>
</template>

<script>

import GoodsClass from './GoodsClass'
import GoodsList from './GoodsList'

export default {
  name: 'Hello',
  data () {
    return {

    }
  },
  components: {
    GoodsClass,
    GoodsList
  }
}
</script>

<style scoped>

</style>
