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

    @GetMapping(value = "/search/shows")
    public ResponseEntity<?> searchShow(@RequestParam int startDate,
                                        @RequestParam int endDate,
                                        @RequestParam int page,
                                        @RequestParam int rows,
                                        @RequestParam int prfState) {

        return artizenService.searchShow(startDate, endDate, page, rows, prfState);
    }


    @GetMapping(value = "/artizens")
    public ResponseEntity<?> getArtizenList(@RequestParam String genre) {

        return artizenService.getArtizenList(genre);
    }


    @GetMapping(value = "/artizens/{artizen_id}")
    public ResponseEntity<?> getArtizen(@PathVariable(name = "artizen_id") String id) {

        return artizenService.getArtizen(id);
    }
}
