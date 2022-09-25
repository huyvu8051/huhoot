import ProcessBar from "@/components/global/ProcessBar.vue";
import UploadFile from "@/components/global/UploadFile.vue";
import ImageWrapper from "@/components/global/ImageWrapper.vue";
import DateFormater from "@/components/global/DateFormater.vue";
import ImageDataTable from "@/components/global/ImageDataTable.vue";
import Nofication from "@/components/global/Nofication.vue";
import FlexLayout from "@/components/global/FlexLayout.vue";
import FitHeightImage from "@/components/global/FitHeightImage.vue";
import DataTableRouterIcon from "@/components/global/DataTableRouterIcon.vue";
import ConfirmDialog from "@/components/global/ConfirmDialog.vue";
import Timeout from "@/components/global/Timeout.vue";
import ThreeDot from "@/components/global/ThreeDot.vue";
import TrueFalseIcon from "@/components/global/TrueFalseIcon.vue";
import StatusIcon from "@/components/global/StatusIcon.vue";
import ItemTrait from "@/components/global/ItemTrait.vue";

import IsNonLocked from "@/components/global/IsNonLocked.vue";
import IsRandomAnswers from "@/components/global/IsRandomAnswers.vue";
import IsRandomQuestions from "@/components/global/IsRandomQuestions.vue";
import RouteToStudentDetails from "@/components/global/RouteToStudentDetails.vue";
import RouteToAdminDetails from "@/components/global/RouteToAdminDetails.vue";

import SimpleDialog from "@/components/global/SimpleDialog.vue";


const GlobalComponents = {
  install(app) {
    app.component("h-process-bar", ProcessBar);
    app.component("h-upload-file", UploadFile);
    app.component("h-image-wrapper", ImageWrapper);
    app.component("h-date-formater", DateFormater);
    app.component("h-image-data-table", ImageDataTable);
    app.component("h-nofication", Nofication);
    app.component("h-flex-layout", FlexLayout);
    app.component("h-fit-height-image", FitHeightImage);
    app.component("h-data-table-router-icon", DataTableRouterIcon);
    app.component("h-confirm-dialog", ConfirmDialog);
    app.component("h-timeout", Timeout);
    app.component("h-three-dot", ThreeDot);
    app.component("h-true-false", TrueFalseIcon);
    app.component("h-status-icon", StatusIcon);
    app.component("h-item-trait", ItemTrait);

    app.component("h-is-non-locked", IsNonLocked);
    app.component("h-is-random-answers", IsRandomAnswers);
    app.component("h-is-random-questions", IsRandomQuestions);
    app.component("h-route-to-student-details", RouteToStudentDetails);
    app.component("h-route-to-admin-details", RouteToAdminDetails);

    app.component("h-simple-dialog", SimpleDialog);

  }
};

export default GlobalComponents;