package com.example.artizen.controller;

import com.example.artizen.dto.request.CommunityRequestDto;
import com.example.artizen.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/community")
public class CommunityController {
    private final CommunityService communityService;


    @PostMapping
    public ResponseEntity<?> createCommunity(@ModelAttribute CommunityRequestDto requestDto) throws IOException {

        return communityService.createCommunity(requestDto);
    }

    @GetMapping
    public ResponseEntity<?> getCommunityList(){

        return communityService.getCommunityList();
    }

    @GetMapping(value = "/{community_id}")
    public ResponseEntity<?> getCommunity(@PathVariable(name = "community_id") Long id){

        return communityService.getCommunity(id);
    }

    @PutMapping(value = "/{community_id}")
    public ResponseEntity<?> updateCommunity(@ModelAttribute CommunityRequestDto requestDto,
                                             @PathVariable(name = "community_id") Long id) throws IOException{

        return communityService.updateCommunity(requestDto, id);
    }

    @DeleteMapping(value = "/{community_id}")
    public ResponseEntity<?> deleteCommunity(@PathVariable(name = "community_id") Long id){

        return communityService.deleteCommunity(id);
    }
}
