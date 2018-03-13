
<template>
<div class="modal fade" :id="id">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">{{title}}</h4>
              </div>
              <div class="modal-body" >
                <slot></slot>
              </div>

              <div v-if="$slots.footer" class="modal-footer">
                    <slot name="footer"></slot>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
</div>
</template>


<script>

export default {
  name: 'v-modal',
  props: {
    title:String ,
    id:{type:String , default:'modal0'}
  } ,
  mounted : function() {
    console.log("modal + mounted!!")
  }
}
</script>

