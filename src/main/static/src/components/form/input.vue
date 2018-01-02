<template>
    <input :type="type" class="form-control" :placeholder="placeholder" :value="currentValue" @input="value => { currentValue = value }">
</template>

<script>

export default {
  name: 'v-input',
  props: {
    value: [String, Number],
    placeholder: String,
    type: {
            type: String,
            default: 'text'
    }
  } ,
  data() {
    return {
        currentValue : this.value
    }
  } ,
  mounted : function() {

  }
}
</script>