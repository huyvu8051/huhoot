<template>
  <router-link
    :to="{
      name: 'studentDetails',
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
