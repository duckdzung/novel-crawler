<template>
  <div class="row novel-container">
    <div class="col-3">
      <div>
        <img :src="novel.coverUrl" class="novel-cover" :alt="novel.title" />
      </div>
    </div>
    <div class="col-9">
      <h3 class="novel-title">
        <router-link
          class="navbar-brand"
          :to="`/detail/${novelUrl}`"
          style="font-weight: bold"
        >
          {{ novel.title }}
        </router-link>
      </h3>
      <h2 class="novel-author">
        <span>{{ novel.author }}</span>
      </h2>
    </div>
  </div>
</template>

<script>
export default {
  name: "NovelItem",
  props: {
    novel: {
      type: Object,
      required: true,
    },
  },
  computed: {
    novelUrl() {
      return `${this.convertToSlug(this.novel.title)}`;
    },
  },
  methods: {
    removeVietnameseTones(str) {
      var accentsMap = new Map([
        ["a", "áàạảãâấầậẩẫăắằặẳẵ"],
        ["e", "éèẹẻẽêếềệểễ"],
        ["i", "íìịỉĩ"],
        ["o", "óòọỏõôốồộổỗơớờợởỡ"],
        ["u", "úùụủũưứừựửữ"],
        ["y", "ýỳỵỷỹ"],
        ["d", "đ"],
      ]);

      for (let [char, accents] of accentsMap) {
        for (let accent of accents) {
          str = str.replaceAll(accent, char);
          str = str.replaceAll(accent.toUpperCase(), char.toUpperCase());
        }
      }

      return str;
    },
    convertToSlug(str) {
      // Remove Vietnamese tones
      str = this.removeVietnameseTones(str);

      // Convert to lower case
      str = str.toLowerCase();

      // Replace spaces with hyphens
      str = str.replace(/\s+/g, "-");

      // Remove all non-alphanumeric characters except for hyphens
      str = str.replace(/[^a-z0-9-]/g, "");

      return str;
    },
  },
};
</script>

<style>
.novel-container {
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
  margin: 5px;
  padding: 5px;
  font-family: "Roboto Condensed", Tahoma, sans-serif;
}

.novel-cover {
  max-height: 100%;
  max-width: 100%;
  border: 1px solid #e7e7e7;
}

.novel-title,
.novel-author {
  font-weight: bold;
  font-size: 18px;
  margin-top: 20px;
}

.novel-title a {
  text-decoration: none;
  color: #4e4e4e;
}

.novel-title a:hover {
  text-decoration: underline;
}

.novel-author {
  font-size: 15px;
  color: #4e4e4e;
  font-style: italic;
}
</style>
