<template>
  <router-link
    :to="{
      name: 'adminDetails',
      params: {
        username: username,
      },
    }"
  >
    <slot>
      {{ username }}
    </slot>
  </router-link>
</template>

<script>
export default {
  props: {
    username: String,
  },
};
</script>

<style>
</style>