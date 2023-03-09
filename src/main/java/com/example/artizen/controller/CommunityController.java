package com.example.artizen.controller;

import com.example.artizen.dto.request.CommunityRequestDto;
import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.CommunityService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/community")
@Api(tags = "Artizen community (커뮤니티)")
public class CommunityController {
    private final CommunityService communityService;


    @PostMapping
    public ResponseEntity<?> createCommunity(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                             @ModelAttribute CommunityRequestDto requestDto) throws IOException {

        return communityService.createCommunity(memberDetails.getMember(), requestDto);
    }

    @GetMapping
    public ResponseEntity<?> getCommunityList(@RequestParam int page,
                                              @RequestParam int size) {

        return communityService.getCommunityList(page, size);
    }

    @GetMapping(value = "/{community_id}")
    public ResponseEntity<?> getCommunity(@PathVariable(name = "community_id") Long id) {

        return communityService.getCommunity(id);
    }

    @PutMapping(value = "/{community_id}")
    public ResponseEntity<?> updateCommunity(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                             @ModelAttribute CommunityRequestDto requestDto,
                                             @PathVariable(name = "community_id") Long id) throws IOException {

        return communityService.updateCommunity(memberDetails.getMember(), requestDto, id);
    }

    @DeleteMapping(value = "/{community_id}")
    public ResponseEntity<?> deleteCommunity(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                             @PathVariable(name = "community_id") Long id) {

        return communityService.deleteCommunity(memberDetails.getMember(), id);
    }
}
