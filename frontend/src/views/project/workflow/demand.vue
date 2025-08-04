<template>
  <el-card>
    <el-form :model="form" label-width="120px">
      <el-form-item label="需求名称">
        <el-input v-model="form.name" />
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script setup lang="ts">
import { ref } from 'vue';

const form = ref({
  name: ''
});
</script>