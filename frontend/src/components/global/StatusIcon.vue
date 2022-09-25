<template>
  <v-chip
    style="color: white"
    big
    class="mr-2"
    :color="
      item.challengeStatus === 'WAITING'
        ? 'green'
        : item.challengeStatus === 'BUILDING'
        ? 'yellow'
        : item.challengeStatus === 'IN_PROGRESS'
        ? 'blue'
        : item.challengeStatus === 'LOCKED'
        ? 'red'
        : 'grey'
    "
  >
    <v-icon left>
      {{
        item.challengeStatus === "WAITING"
          ? "mdi-reload"
          : item.challengeStatus === "BUILDING"
          ? "mdi-creation"
          : item.challengeStatus === "IN_PROGRESS"
          ? "mdi-cards-playing-outline"
          : item.challengeStatus === "LOCKED"
          ? "mdi-lock-outline"
          : "mdi-clock-end"
      }}
    </v-icon>
    {{
      item.challengeStatus === "WAITING"
        ? "Chờ"
        : item.challengeStatus === "BUILDING"
        ? "Đang xây dựng"
        : item.challengeStatus === "IN_PROGRESS"
        ? "Đang diễn ra"
        : item.challengeStatus === "LOCKED"
        ? "Khóa"
        : "Kết thúc"
    }}
  </v-chip>
</template>



<script>
export default {
  props: {
    item: Object,
  },
};
</script>
<style>
</style>