import { createRouter, createWebHistory } from "vue-router";
import HomePage from "../components/HomePage.vue";
import NovelDetail from "../components/NovelDetail.vue";
import NovelChapter from "../components/NovelChapter.vue";
import ReadingHistory from "../components/ReadingHistory.vue";

const routes = [
  {
    path: "/",
    name: "HomePage",
    component: HomePage,
  },
  {
    path: "/:filter",
    name: "FilteredHomePage",
    component: HomePage,
  },
  {
    path: "/detail/:novelName",
    name: "NovelDetail",
    component: NovelDetail,
  },
  {
    path: "/detail/:novelName/:chapterNumber",
    name: "NovelChapter",
    component: NovelChapter,
  },
  {
    path: "/history",
    name: "ReadingHistory",
    component: ReadingHistory,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
