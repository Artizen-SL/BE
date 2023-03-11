package com.example.artizen.controller;

import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.HeartService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/hearts")
@Api(tags = "Artizen heart (좋아요)")
public class HeartController {
    private final HeartService heartService;

    @Operation(summary = "아티즌 좋아요", description = "아티즌 각 문화 컨텐츠별 좋아요 기능")
    @PostMapping(value = "/artizens/{artizen_id}")
    public ResponseEntity<?> checkArtizenHeart(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                        @PathVariable(name = "artizen_id") String id){

        return heartService.checkArtizenHeart(memberDetails.getMember(), id);
    }


    @Operation(summary = "커뮤니티 좋아요", description = "각 커뮤니티 글별 좋아요 기능")
    @PostMapping(value = "/community/{community_id}")
    public ResponseEntity<?> checkCommunityHeart(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                 @PathVariable(name = "community_id") Long id){

        return heartService.checkCommunityHeart(memberDetails.getMember(), id);
    }
}
