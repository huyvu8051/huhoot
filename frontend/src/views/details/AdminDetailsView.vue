<template>
  <div>
    <AdminCard v-model="admin" />
    <ListChallenge
      title="Danh sách cuộc thi đã tạo"
      :options.sync="options"
      :desserts="desserts"
      :totalDesserts="totalDesserts"
    >
    </ListChallenge>
  </div>
</template>

<script>
import AdminCard from "@/components/AdminCard";
import ListChallenge from "@/components/ListChallenge";
import DetailsService from "@/services/DetailsService";
export default {
  components: {
    AdminCard,
    ListChallenge,
  },
  data() {
    return {
      options: {},
      admin: {
        createdBy: "0",
        createdDate: 0,
        fullName: "0",
        id: 0,
        isNonLocked: true,
        modifiedBy: "0",
        modifiedDate: 0,
        username: "0",
      },
      desserts: [],
      totalDesserts: 0,
    };
  },
  watch: {
    options: {
      handler() {
        this.getDataFromApi();
      },
      deep: true,
    },
  },
  methods: {
    getDataFromApi() {
      var body = Object.assign(
        {
          username: this.$route.params.username,
        },
        this.options
      );

      DetailsService.getAdminDetails(body).then((res) => {
         this.desserts = res.data.listChallenge.list;
        this.totalDesserts = res.data.listChallenge.totalElements;
        this.admin = res.data.adminDetails;
      });
    },
  },
};
</script>

<style>
</style>