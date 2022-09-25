<template>
  <v-card class="mx-auto" flat>
    <v-toolbar color="cyan" dense flat>
      <v-toolbar-title class="text-body-2">
        <slot name="topRight" />
      </v-toolbar-title>
      <v-spacer>
        <v-toolbar-title class="text-body-2">
          <strong> Statistic </strong>
        </v-toolbar-title>
      </v-spacer>
      <ShowTopStudent />
    </v-toolbar>
    <v-card-text ref="card" class="d-flex justify-center">
      <VueApexCharts
        class="chart"
        type="pie"
        :width="chartWidth"
        :options="chartOptions"
        :series="series"
      />
    </v-card-text>
  </v-card>
</template>

<script>
import ShowTopStudent from "@/components/organize/show/ShowTopStudent";
import VueApexCharts from "vue-apexcharts";

import { mapState } from "vuex";

export default {
  components: {
    ShowTopStudent,
    VueApexCharts,
  },
  data() {
    return {
      chartWidth: 200,
      series: [],
      chartOptions: {
        chart: {
          width: 380,
          type: "pie",
        },
        labels: ["Correct", "Wrong", "No Answer"],
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                width: 200,
              },
              legend: {
                position: "bottom",
              },
            },
          },
        ],
      },
      autoOrgTimeout: null,
    };
  },
  computed: {
    ...mapState({
      timeout: (state) => state.question.timeout,
      totalStudent: (state) => state.totalStudent,
      totalStudentCorrect: (state) => state.totalStudentCorrect,
      totalStudentWrong: (state) => state.totalStudentWrong,
    }),
  },
  created() {
    this.series.push(this.totalStudentCorrect);
    this.series.push(this.totalStudentWrong);
    this.series.push(
      this.totalStudent - this.totalStudentCorrect - this.totalStudentWrong
    );

    // console.log(this.totalStudentCorrect, this.totalStudentWrong);

    window.addEventListener("resize", this.resizeWindowEventHandler);
  },

  beforeDestroy() {
    window.removeEventListener("resize", this.resizeWindowEventHandler);
    clearTimeout(this.autoOrgTimeout);
  },
  mounted() {
    this.resizeWindowEventHandler({});
    this.setAutoOrgTimeout();
  },
  methods: {
    resizeWindowEventHandler(e) {
      this.chartWidth = this.$refs.card.clientWidth * 0.5;
      // console.log(this.$refs.card.clientWidth);
    },

    setAutoOrgTimeout() {
      clearTimeout(this.autoOrgTimeout);

      this.autoOrgTimeout = setTimeout(() => {
        this.$store.state.getRankTablePage(this.$route.query.challengeId);
      }, 4000);
    },
  },
};
</script>

<style scoped>
.v-progress-circular {
  margin: 1rem;
}
.chart {
  text-align: left;
}
</style>