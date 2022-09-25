<template>
  <v-card class="elevation-0 text-left">
    <v-img
      height="200px"
      src="https://cdn.pixabay.com/photo/2020/07/12/07/47/bee-5396362_1280.jpg"
    >
    </v-img>

    <v-card-title>
      Mã số giảng viên:&nbsp
      <h-route-to-student-details :username="value.username" />
    </v-card-title>

    <v-card-text>
      <h-is-non-locked class="mb-4" :value="value.isNonLocked" />

      <h-date-formater before="Ngày tạo: " :date="value.createdDate" />
      <div>
        Tạo bởi:
        <h-route-to-admin-details :username="value.createdBy" />
      </div>
      <h-date-formater before="Ngày sửa: " :date="value.modifiedDate" />
      <div>
        Sửa bởi:
        <h-route-to-admin-details :username="value.modifiedBy" />
      </div>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>
  </v-card>
</template>

<script>
export default {
  props: {
    value: {
      type: Object,
      default: () => ({
        createdBy: "0",
        createdDate: 0,
        id: 0,
        isNonLocked: true,
        modifiedBy: "0",
        modifiedDate: 0,
        username: "0",
      }),
    },
  },
};
</script>