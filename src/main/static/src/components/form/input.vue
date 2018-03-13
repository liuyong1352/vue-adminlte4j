<template>
    <input :type="type" class="form-control" :placeholder="placeholder" :value="currentValue" @input="handleInput">
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
  method: {
    handleInput (event) {
        let value = event.target.value
        this.currentValue = value

    },
  } ,
  mounted : function() {

  }
}
</script>