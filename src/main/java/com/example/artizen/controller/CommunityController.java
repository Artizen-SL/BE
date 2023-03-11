package com.example.artizen.controller;

import com.example.artizen.dto.request.CommunityRequestDto;
import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.CommunityService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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


    @Operation(summary = "커뮤니티 작성", description = "커뮤니티 글 작성 기능")
    @PostMapping
    public ResponseEntity<?> createCommunity(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                             @ModelAttribute CommunityRequestDto requestDto) throws IOException {

        return communityService.createCommunity(memberDetails.getMember(), requestDto);
    }

    @Operation(summary = "커뮤니티 조회", description = "커뮤니티 전체 리스트 조회 기능")
    @GetMapping
    public ResponseEntity<?> getCommunityList(@RequestParam int page,
                                              @RequestParam int size) {

        return communityService.getCommunityList(page, size);
    }

    @Operation(summary = "커뮤니티 상세 조회", description = "각 커뮤니티 상세 조회 기능")
    @GetMapping(value = "/{community_id}")
    public ResponseEntity<?> getCommunity(@PathVariable(name = "community_id") Long id) {

        return communityService.getCommunity(id);
    }

    @Operation(summary = "커뮤니티 수정", description = "커뮤니티 글 수정 기능")
    @PutMapping(value = "/{community_id}")
    public ResponseEntity<?> updateCommunity(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                             @ModelAttribute CommunityRequestDto requestDto,
                                             @PathVariable(name = "community_id") Long id) throws IOException {

        return communityService.updateCommunity(memberDetails.getMember(), requestDto, id);
    }

    @Operation(summary = "커뮤니티 삭제", description = "커뮤니티 글 삭제 기능")
    @DeleteMapping(value = "/{community_id}")
    public ResponseEntity<?> deleteCommunity(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                             @PathVariable(name = "community_id") Long id) {

        return communityService.deleteCommunity(memberDetails.getMember(), id);
    }
}
