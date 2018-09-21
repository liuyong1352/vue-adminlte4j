<template>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1 id="page_header">Welcome</h1>
        <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> <span id="lib_menu1">home</span></li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content container-fluid">
        <slot></slot>
    </section>
</div>

</template>

<script>
export default {
  name: 'v-content',
  props: {

  }
}
</script>