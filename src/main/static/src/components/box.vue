<template>
  <div class="box">
    <div class="box-header" :class="(isBorder || isSolid)?'with-border':''">
        <slot name="icon-title"></slot>
        <template v-if="title">
            <h3 class="box-title">{{ title }}</h3>
        </template>
        <template v-if="$slots.header">
            <slot name="header"></slot>
        </template>
        <div  class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" :data-widget="widgetType"><i :class="btnIcon"></i>
                </button>
        </div>
        <!-- /.box-tools -->
    </div>

    <div class="box-body">
          <slot></slot>
    </div>
    <!-- /.box-body -->
    <div v-if="$slots.footer" class="box-footer">
          <slot name="footer"></slot>
    </div>
  </div>

</template>

<script>
export default {
  name: 'v-box',
  props: {
      widgetType:   { type: String, default: 'collapse' },
      title:        { type: String },
      isSolid:      { type: Boolean, default: false },
      isOpen:       { type: Boolean , default:true } ,
      isBorder:     { type: Boolean, default: false }
  },
  mounted: function() {
     $('.box').boxWidget()
  },
  computed: {
      btnIcon () {
        if (this.widgetType === 'collapse') {
          return this.isOpen ? 'fa fa-minus' : 'fa fa-plus'
        } else if (this.widgetType === 'remove') {
          return 'fa fa-times'
        }
        return ''
      }
  },
  created () {

  }
}
</script>
