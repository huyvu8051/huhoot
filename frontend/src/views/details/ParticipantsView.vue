<template>
  <div>
    <ChallengeCard :challenge="challenge" />
    <Participants
      :options.sync="options"
      :desserts="desserts"
      :totalDesserts="totalDesserts"
    />
  </div>
</template>

<script>
import Participants from "@/components/Participants";

import ChallengeCard from "@/components/ChallengeCard";
import DetailsService from "@/services/DetailsService";

export default {
  components: {
    Participants,

    ChallengeCard,
  },
  // validate

  // data
  data() {
    return {
      options: {},
      student: {},
      desserts: [],
      totalDesserts: 0,
      challenge: {
        id: 0,
        coverImage: null,
        title: "Tiêu đề cuộc thi",
        createdDate: 0,
        createdBy: "people",
        modifiedDate: 0,
        modifiedBy: "people",
        owner: "people",
        randomAnswer: true,
        randomQuest: false,
        challengeStatus: "WAITING",
      },
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
          challengeId: this.$route.params.challengeId,
        },
        this.options
      );

      DetailsService.getAllParticipants(body).then((res) => {
        this.desserts = res.data.participants.list;
        this.totalDesserts = res.data.participants.totalElements;
        this.challenge = res.data.challengeResponse;
      });
    },
  },
};
</script>