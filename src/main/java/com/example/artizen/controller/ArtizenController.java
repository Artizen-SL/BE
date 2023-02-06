package com.example.artizen.controller;

import com.example.artizen.service.ArtizenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArtizenController {
    private final ArtizenService artizenService;

    // kopis 에서 받아오는 경로
    @GetMapping(value = "/search/kopis")
    public ResponseEntity<?> searchKopis(@RequestParam int startDate,
                                        @RequestParam int endDate,
                                        @RequestParam int page,
                                        @RequestParam int rows,
                                        @RequestParam int prfState) {

        return artizenService.searchKopis(startDate, endDate, page, rows, prfState);
    }


    // 메인페이지 검색할 Api
    @GetMapping(value = "/search/artizens")
    public ResponseEntity<?> searchArtzien(@RequestParam String keyword,
                                           @RequestParam int page,
                                           @RequestParam int size){

        return artizenService.searchArtizen(keyword, page, size);
    }


    // 장르별 전체리스트 조회
    @GetMapping(value = "/artizens")
    public ResponseEntity<?> getArtizenList(@RequestParam String genre) {

        return artizenService.getArtizenList(genre);
    }


    // 상세 조회
    @GetMapping(value = "/artizens/{artizen_id}")
    public ResponseEntity<?> getArtizen(@PathVariable(name = "artizen_id") String id) {

        return artizenService.getArtizen(id);
    }
}
