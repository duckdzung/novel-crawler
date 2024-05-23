import { createRouter, createWebHistory } from "vue-router";
import HomePage from "../components/HomePage.vue";
import NovelDetail from "../components/NovelDetail.vue";

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
    path: "/detail/chapter/:chapterNumber",
    name: "DetailNovel",
    component: NovelDetail,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
