package com.duckdzung.novelcrawler.controller;

import com.duckdzung.novelcrawler.common.PageableData;
import com.duckdzung.novelcrawler.common.ResponseObject;
import com.duckdzung.novelcrawler.entity.ChapterNovel;
import com.duckdzung.novelcrawler.entity.Novel;
import com.duckdzung.novelcrawler.entity.SearchObject;
import com.duckdzung.novelcrawler.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/novels")
@CrossOrigin
public class NovelController {

    @Autowired
    private  NovelService novelService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllNovels(
            @RequestParam(defaultValue = "truyen-hot") String filter,
            @RequestParam(defaultValue = "1") int page
    ) {
        PageableData<Novel> pageableData = novelService.getAllNovels(filter, page);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get all novels successfully.")
                        .data(pageableData)
                        .build()
        );
    }

    @PostMapping("/search")
    public ResponseEntity<ResponseObject> searchNovel(@RequestBody SearchObject searchObject) {
        PageableData<Novel> pageableData = novelService.searchNovel(searchObject.getSearchText(), searchObject.getPage());
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Search novel by search text successfully.")
                        .data(pageableData)
                        .build()
        );
    }

    @GetMapping("/detail")
    public ResponseEntity<ResponseObject> getNovelDetail(
            @RequestParam(defaultValue = "") String novelName,
            @RequestParam(defaultValue = "1") int page
    ) {
        PageableData<Novel> pageableData = novelService.getNovelDetail(novelName, page);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get novel detail successfully.")
                        .data(pageableData)
                        .build()
        );
    }

    @GetMapping("/chapter")
    public ResponseEntity<ResponseObject> getChapterNovel(
            @RequestParam(defaultValue = "") String novelName,
            @RequestParam(defaultValue = "1") int chapterNumber
    ) {
        ChapterNovel chapterNovel = novelService.getChapterNovel(novelName, chapterNumber);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get chapter novel successfully.")
                        .data(chapterNovel)
                        .build()
        );
    }
}

