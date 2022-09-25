export const vietSubQuesPoint = point => {
  return point === "STANDARD"
    ? "Tiêu chuẩn"
    : point === "NO_POINTS"
    ? "Không có điểm"
    : "Nhân đôi";
};
