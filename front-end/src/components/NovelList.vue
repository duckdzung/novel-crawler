<template>
  <div class="container" v-if="!isLoading">
    <NovelItem
      v-for="(novel, index) in novelList"
      :key="index"
      :novel="novel"
    />

    <h2 v-if="!novelList" class="not-found-text">
      Không có truyện nào được tìm thấy
    </h2>

    <h2 v-if="isLoading" class="not-found-text">Đang tải ...</h2>

    <PaginationComponent v-if="totalPages > 1" />
  </div>
</template>
<script>
import { mapState, mapActions } from "vuex";
import NovelItem from "./NovelItem";
import PaginationComponent from "./Pagination";

export default {
  name: "NovelList",
  data() {
    return {
      isLoading: true,
    };
  },
  components: {
    NovelItem,
    PaginationComponent,
  },
  computed: {
    ...mapState({
      novelList: (state) => state.novelList,
      totalPages: (state) => state.totalPages,
    }),
  },
  methods: {
    ...mapActions(["fetchNovelList"]),
  },
  async mounted() {
    this.isLoading = true;
    await this.fetchNovelList();
    this.isLoading = false;
  },
};
</script>

<style>
.not-found-text {
  text-align: center;
  margin-top: 30px;
}
</style>
