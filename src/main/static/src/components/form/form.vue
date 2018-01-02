<template>
    <form class="form-horizontal">
        <slot></slot>
    </form>
</template>

<script>

export default {
  name: 'v-form',
  props: {
    data: String
  } ,
  mounted : function() {
    console.log("form + mounted!!")
  }
}
</script>