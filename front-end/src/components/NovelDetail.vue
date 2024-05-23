<template>
  <div class="container" v-if="!isLoading">
    <div class="col-xs-12 col-info-desc">
      <div class="title-list book-intro">
        <h2>Thông tin truyện</h2>
      </div>
      <h3 class="title text-center">{{ novel.title }}</h3>
      <div class="row">
        <div class="col-4 info-holder">
          <div class="books">
            <div class="book">
              <img :src="novel.coverUrl" alt="Cực Phẩm Đại Tiểu Thư" />
            </div>
          </div>
          <div class="info">
            <div class="d-flex align-items-center">
              <h4>Tác giả:</h4>
              <a
                href="https://truyenfull.vn/tac-gia/chan-ai-vi-luong/"
                :title="novel.author"
                class="ms-3"
              >
                {{ novel.author }}
              </a>
            </div>
            <div class="d-flex align-items-center">
              <h4>Thể loại:</h4>
              <a
                v-for="(genre, index) in novel.genres"
                :key="index"
                itemprop="genre"
                href="https://truyenfull.vn/the-loai/ngon-tinh/"
                :title="genre"
                class="ms-3"
              >
                {{ genre }}
              </a>
            </div>
            <div class="d-flex align-items-center">
              <h4>Trạng thái:</h4>
              <span class="text-success ms-3">{{ novel.status }}</span>
            </div>
          </div>
        </div>
        <div class="col-8 desc" v-html="novel.description"></div>
      </div>
    </div>
    <div class="col-12" id="list-chapter">
      <div class="title-list">
        <h2>Danh sách chương</h2>
      </div>
      <div class="row">
        <div class="col-12">
          <ul class="list-chapter">
            <li v-for="(chapter, index) in novel.chapters" :key="index">
              <span class="glyphicon glyphicon-certificate"></span>
              <a href="https://truyenfull.vn/cuc-pham-dai-tieu-thu/chuong-1/">
                {{ chapter }}
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <h3 v-else class="loading-text">Đang tải ...</h3>
</template>

<script>
import { getNovelDetail } from "@/services/apiService";
import "./assets/NovelDetail.css";

export default {
  name: "NovelDetail",
  data() {
    return {
      novel: {},
      totalPages: 1,
      currentPage: 1,
      isLoading: true,
    };
  },
  async mounted() {
    const novelName = this.$route.params.novelName;
    const respone = await getNovelDetail(novelName, this.currentPage);

    this.novel = respone.data.content[0];
    this.totalPages = respone.data.totalPages;
    this.isLoading = false;
  },
};
</script>

<style scoped>
.loading-text {
  text-align: center;
  margin-top: 30px;
}
</style>
