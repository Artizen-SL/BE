package com.example.artizen.controller;

import com.example.artizen.service.MainService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "Artizen main (메인페이지)")
public class MainController {

    private final MainService mainService;

    @Operation(summary = "Artizen suggestion", description = "아티즌 문화컨텐츠 카테고리별 추천 기능")
    @GetMapping(value = "/suggest")
    public ResponseEntity<?> getLocation (@RequestParam String latitude,
                                          @RequestParam String longitude) {
        return mainService.getLocation (latitude, longitude);
    }

    @Operation(summary = "Best Artizen", description = "아티즌 인기 문화컨텐츠 TOP 4 추천 기능")
    @GetMapping(value = "/best")
    public ResponseEntity<?> getBestArtizen() {
        return mainService.getBestArtizen();
    }

    @Operation(summary = "New Artizen", description = "아티즌 신규 문화컨텐츠 TOP 3 추천 기능")
    @GetMapping(value = "/new")
    public ResponseEntity<?> getNewArtizen() {
        return mainService.getNewArtizen();
    }

}
