package com.example.artizen.controller;

import com.example.artizen.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    //Artizen suggestion
    @GetMapping(value = "/suggest")
    public ResponseEntity<?> getLocation (@RequestParam String latitude,
                                          @RequestParam String longitude) {
        return mainService.getLocation (latitude, longitude);
    }

    //Best Artizen url Get.
    @GetMapping(value = "/best")
    public ResponseEntity<?> getBestArtizen() {
        return mainService.getBestArtizen();
    }

    //new Artizen url Get.
    @GetMapping(value = "/new")
    public ResponseEntity<?> getNewArtizen() {
        return mainService.getNewArtizen();
    }

}
