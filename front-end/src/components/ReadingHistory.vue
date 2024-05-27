<template>
  <div>
    <h2 class="text-center">Lịch sử truyện đã đọc</h2>
    <div class="container">
      <div v-if="readingState?.length > 0" class="row">
        <div
          v-for="(novel, novelId) in readingState"
          :key="novelId"
          class="card col-3 m-1"
          style="width: 15rem; margin-bottom: 1rem"
        >
          <img
            :src="novel.coverUrl"
            class="card-img-top"
            alt="novel.novelName"
          />
          <div class="card-body">
            <h5 class="card-title">{{ novel.novelName }}</h5>
            <p class="card-text">
              Chương hiện tại: {{ novel.chapterNumber || 1 }}
            </p>
            <router-link :to="getUrlNovel(novel)" class="btn btn-primary"
              >Tiếp tục đọc</router-link
            >
          </div>
        </div>
      </div>
      <div v-else>
        <p>Không có truyện nào trong lịch sử đọc.</p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ReadingHistory",
  computed: {
    ...mapState(["readingState"]),
  },
  methods: {
    getUrlNovel(novel) {
      return `../detail/${novel.novelUrl}/${novel.chapterNumber || 1}`;
    },
  },
};
</script>

<style scoped></style>
