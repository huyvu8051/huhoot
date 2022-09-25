<template>
  <v-card class="elevation-0 text-left">
    <h-image-wrapper
      height="200px"
      class="white--text align-end"
      :src="challenge.coverImage"
    >
      <v-card-title class="cart-title">{{ challenge.title }}</v-card-title>
    </h-image-wrapper>

    <v-card-title> Mã cuộc thi: {{ challenge.id }} </v-card-title>

    <v-card-subtitle class="pb-0">
      <h-is-random-answers :value="challenge.randomAnswer" />
      <h-is-random-questions :value="challenge.randomQuest" />
      <h-status-icon :item="challenge" />
    </v-card-subtitle>

    <v-card-text class="text--primary">
      Người sở hữu:
      <h-route-to-admin-details :username="challenge.owner" />
      <h-date-formater before="Ngày tạo: " :date="challenge.createdDate" />

      <div>
        Tạo bởi:
        <h-route-to-admin-details :username="challenge.createdBy" />
      </div>
      <h-date-formater before="Ngày sửa: " :date="challenge.modifiedDate" />
      <div>
        Sửa bởi:
        <h-route-to-admin-details :username="challenge.modifiedBy" />
      </div>
    </v-card-text>

    <v-card-actions>
      <v-btn color="orange" text> Share </v-btn>
      <v-btn color="orange" text> Explore </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: {
    challenge: {
      type: Object,
      default: () => ({
        id: 0,
        coverImage: null,
        title: "Tiêu đề cuộc thi",
        createdDate: 0,
        createdBy: "Created by people",
        modifiedDate: 0,
        modifiedBy: "Modified by people",
        owner: "Owner by people",
        randomAnswer: true,
        randomQuest: false,
        challengeStatus: "WAITING",
      }),
    },
  },
};
</script>

<style scoped>
.cart-title {
  background: linear-gradient(
    to bottom,
    transparent 20%,
    rgba(0, 0, 0, 0.664) 100%
  );
}
</style>