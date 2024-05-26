package com.duckdzung.novelcrawler.service;

import com.duckdzung.novelcrawler.common.PageableData;
import com.duckdzung.novelcrawler.entity.Novel;
import com.duckdzung.novelcrawler.entity.ChapterNovel;
import com.duckdzung.novelcrawler.exception.InternalServerErrorException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NovelService {

    public PageableData<Novel> getALlNovels(String filter, int page) {
        PageableData<Novel> pageableData = new  PageableData<Novel>();
        List<Novel> novelList = new ArrayList<>();

        try {
            String baseUrl = "https://truyenfull.vn/danh-sach/";
            String url = String.format("%s%s/trang-%d", baseUrl, filter, page);

            Document doc = Jsoup.connect(url).get();

            // Select element <div class="list-truyen">
            Element listTruyenDiv = doc.selectFirst("div.list-truyen");

            // Select all elements have class that equals row
            if (listTruyenDiv != null) {
                Elements rowlElements = listTruyenDiv.select("div.row");

                for (Element row : rowlElements) {
                    Novel newNovel = new Novel();

                    // Get cover URL from novel
                    Element coverElement = row.selectFirst("div[data-classname='cover']");
                    if (coverElement != null) {
                        String coverUrl = coverElement.attr("data-image");
                        newNovel.setCoverUrl(coverUrl);
                    }

                    // Get title from novel
                    Element titleElement = row.selectFirst("div.col-xs-7 h3.truyen-title a");
                    if (titleElement != null) {
                        String title = titleElement.text();
                        newNovel.setTitle(title);
                    }

                    // Get author from novel
                    Element authorElement = row.selectFirst("div.col-xs-7 span.author");
                    if (authorElement != null) {
                        String author = authorElement.text();
                        newNovel.setAuthor(author);
                    }

                    // Add newNovel to novelList
                    novelList.add(newNovel);
                }
            }

            // Get totalPages
            Element paginationUl = doc.selectFirst("ul.pagination.pagination-sm");

            // Nếu tìm thấy phần tử pagination
            if (paginationUl != null) {
                // Chọn tất cả các phần tử <a> bên trong
                Elements pageLinks = paginationUl.select("a");

                // Nếu có ít nhất một phần tử <a>
                if (!pageLinks.isEmpty()) {
                    // Lấy phần tử cuối cùng
                    Element lastPageLink = pageLinks.get(pageLinks.size() - 2);

                    // Lấy giá trị của thuộc tính href từ phần tử này
                    String lastPageHref = lastPageLink.attr("href");

                    // Lấy ra số trang từ URL
                    String[] parts = lastPageHref.split("/");
                    String totalPagesStr = parts[parts.length - 1];
                    totalPagesStr = totalPagesStr.replace("trang-", "").replace("/", "");
                    int totalPages = Integer.parseInt(totalPagesStr);

                    // Set total pages
                    pageableData.setTotalPages(totalPages);
                }

                pageableData.setContent(novelList);
                pageableData.setTotalElements(novelList.size());
            }
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        return pageableData;
    }

    public PageableData<Novel> searchNovel(String searchText, int page) {
        PageableData<Novel> pageableData = new  PageableData<Novel>();
        List<Novel> novelList = new ArrayList<>();

        try {
            String baseUrl = "https://truyenfull.vn/tim-kiem/";
            String url = String.format("%s?tukhoa=%s&page=%s", baseUrl, searchText, page);

            Document doc = Jsoup.connect(url).get();

            // Select element <div class="list-truyen">
            Element listTruyenDiv = doc.selectFirst("div.list-truyen");

            // Select all elements have class that equals row
            if (listTruyenDiv != null) {
                Elements rowlElements = listTruyenDiv.select("div.row");

                for (Element row : rowlElements) {
                    Novel newNovel = new Novel();

                    // Get cover URL from novel
                    Element coverElement = row.selectFirst("div[data-classname='cover']");
                    if (coverElement != null) {
                        String coverUrl = coverElement.attr("data-image");
                        newNovel.setCoverUrl(coverUrl);
                    }

                    // Get title from novel
                    Element titleElement = row.selectFirst("div.col-xs-7 h3.truyen-title a");
                    if (titleElement != null) {
                        String title = titleElement.text();
                        newNovel.setTitle(title);
                    }

                    // Get author from novel
                    Element authorElement = row.selectFirst("div.col-xs-7 span.author");
                    if (authorElement != null) {
                        String author = authorElement.text();
                        newNovel.setAuthor(author);
                    }

                    // Add newNovel to novelList
                    novelList.add(newNovel);
                }
            }

            // Get totalPages
            Element paginationUl = doc.selectFirst("ul.pagination.pagination-sm");

            // Nếu tìm thấy phần tử pagination
            if (paginationUl != null) {
                // Chọn tất cả các phần tử <a> bên trong
                Elements pageLinks = paginationUl.select("a");

                // Nếu có ít nhất một phần tử <a>
                if (!pageLinks.isEmpty()) {
                    // Lấy phần tử cuối cùng
                    Element lastPageLink = pageLinks.get(pageLinks.size() - 2);

                    // Lấy giá trị của thuộc tính href từ phần tử này
                    String lastPageHref = lastPageLink.attr("href");

                    int totalPages = 1;
                    if (lastPageHref.contains("page=")) {
                        String[] parts = lastPageHref.split("page=");
                        String totalPagesStr = parts[1].split("&")[0];
                        totalPages = Integer.parseInt(totalPagesStr);
                    } else {
                        String[] parts = lastPageHref.split("/");
                        String totalPagesStr = parts[parts.length - 1].replace("trang-", "").replace("/", "");
                        totalPages = Integer.parseInt(totalPagesStr);
                    }

                    // Set total pages
                    pageableData.setTotalPages(totalPages);
                }
            }

            pageableData.setContent(novelList);
            pageableData.setTotalElements(novelList.size());
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        return pageableData;
    }

    public PageableData<Novel> getNovelDetail(String novelName, int page) {
        String baseUrl = "https://truyenfull.vn";
        Novel novel = new Novel();
        PageableData<Novel> pageableData = new  PageableData<Novel>();

        try {
            String url = String.format("%s/%s/trang-%d/#list-chapter", baseUrl, novelName, page);
            Document doc = Jsoup.connect(url).get();

            Element novelInfoDiv = doc.selectFirst("div.col-xs-12.col-info-desc");
            if (novelInfoDiv != null) {
                // Set novel title
                String title = novelInfoDiv.selectFirst("h3.title").text();
                novel.setTitle(title);

                // Set coverUrl
                Element imgElement = novelInfoDiv.selectFirst("div.book img");
                if (imgElement != null) {
                    String coverUrl = imgElement.attr("src");
                    novel.setCoverUrl(coverUrl);
                }

                // Set author
                Element authorElement = novelInfoDiv.selectFirst("div.info-holder div.info a[href~=tac-gia]");
                if (authorElement != null) {
                    String author = authorElement.text();
                    novel.setAuthor(author);
                }

                // Set genres
                Elements genreElements = novelInfoDiv.select("div.info-holder div.info a[href~=the-loai]");
                List<String> genres = new ArrayList<>();
                for (Element genreElement : genreElements) {
                    genres.add(genreElement.text());
                }

                novel.setGenres(genres);

                // Set status
                Element statusElement = novelInfoDiv.selectFirst("div.info-holder div.info span.text-success");
                if (statusElement != null) {
                    String status = statusElement.text();
                    novel.setStatus(status);
                }

                // Set description
                Element descriptionElement = novelInfoDiv.selectFirst("div.desc div.desc-text");
                if (descriptionElement != null) {
//                    String description = descriptionElement.text();
                    novel.setDescription(descriptionElement.toString());
                }
            }

            // Get chapter list
            Element listChapterDiv = doc.selectFirst("#list-chapter");
            if (listChapterDiv != null) {
                // Lấy danh sách các phần tử li
                Elements chapterElements = listChapterDiv.select("ul.list-chapter li");
                List<String> chapters = new ArrayList<>();
                for (Element chapterElement : chapterElements) {
                    // Trích xuất thông tin về số chương và tiêu đề của chương
//                    String chapterUrl = chapterElement.selectFirst("a").attr("href");
                    String chapterTitle = chapterElement.selectFirst("a").text();
                    // Tạo đối tượng chương và thêm vào danh sách
                    chapters.add(chapterTitle);
                }
                // Lưu danh sách chương vào đối tượng truyện
                novel.setChapters(chapters);
            }

            // Get total pages of chaper ist
            Element paginationUl = doc.selectFirst("ul.pagination.pagination-sm");

            // Lấy ra thẻ <li> cuối cùng trong thẻ <ul>
            Elements liElements = paginationUl.select("li");
            Element lastPageLi = liElements.get(liElements.size() - 2);

            String lastPageHref = lastPageLi.selectFirst("a").attr("href");

            // Tách số trang từ thuộc tính href
            String lastPageNumber = lastPageHref.replaceAll("^.*trang-(\\d+).*$", "$1");

            // Lấy ra số trang từ văn bản trích xuất
            int totalPages = Integer.parseInt(lastPageNumber);

            pageableData.setContent(Collections.singletonList(novel));
            pageableData.setTotalPages(totalPages);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        return pageableData;
    }

    public ChapterNovel getChapterNovel(String novelName, int chapterNumber) {
        String baseUrl = "https://truyenfull.vn";
        ChapterNovel chapterNovel = new ChapterNovel();
        try {
            String url = String.format("%s/%s/chuong-%d", baseUrl, novelName, chapterNumber);
            Document doc = Jsoup.connect(url).get();

            // Get title
            Element novelTitleElement = doc.selectFirst("a.truyen-title");
            if (novelTitleElement != null) {
                String novelTitle = novelTitleElement.text();
                chapterNovel.setNovelTitle(novelTitle);
            }

            // Get chapter number
            Element chapterTitleElement = doc.selectFirst("h2 a.chapter-title");
            if (chapterTitleElement != null) {
                String chapterTitle = chapterTitleElement.text();
                chapterNovel.setChapterTitle(chapterTitle);
            }

            // Get content chapter novel
            Element chapterContentDiv = doc.selectFirst("div#chapter-c");
            if (chapterContentDiv != null) {
                chapterContentDiv.select(".ads-responsive").remove();
                String chapterContent =chapterContentDiv.html();
                chapterNovel.setChapterContent(chapterContent);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        return chapterNovel;
    }

}
