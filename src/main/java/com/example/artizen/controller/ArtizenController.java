package com.example.artizen.controller;

import com.example.artizen.service.ArtizenService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "Artizen detail (검색, 상세)")
public class ArtizenController {
    private final ArtizenService artizenService;

    @Operation(summary = "Kopis update", description = "Kopis 데이터 업데이트하는 기능")
    @GetMapping(value = "/search/kopis")
    public ResponseEntity<?> searchKopis(@RequestParam int startDate,
                                        @RequestParam int endDate,
                                        @RequestParam int page,
                                        @RequestParam int rows,
                                        @RequestParam int prfState) {

        return artizenService.searchKopis(startDate, endDate, page, rows, prfState);
    }

    @Operation(summary = "아티즌 통합 검색", description = "메인페이지 통합 검색창 기능")
    @GetMapping(value = "/search/artizens")
    public ResponseEntity<?> searchArtzien(@RequestParam String keyword,
                                           @RequestParam int page,
                                           @RequestParam int size){

        return artizenService.searchArtizen(keyword, page, size);
    }

    @Operation(summary = "장르별 리스트 조회", description = "각 카테고리별 전체 리스트 조회 기능")
    @GetMapping(value = "/artizens")
    public ResponseEntity<?> getArtizenList(@RequestParam String genre,
                                            @RequestParam int page,
                                            @RequestParam int size) {

        return artizenService.getArtizenList(genre, page, size);
    }

    @Operation(summary = "아티즌 상세 조회", description = "각 문화컨텐츠별 상세 조회 기성")
    @GetMapping(value = "/artizens/{artizen_id}")
    public ResponseEntity<?> getArtizen(@PathVariable(name = "artizen_id") String id) {

        return artizenService.getArtizen(id);
    }
}
