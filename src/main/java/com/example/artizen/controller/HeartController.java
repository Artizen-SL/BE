package com.example.artizen.controller;

import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/hearts")
public class HeartController {
    private final HeartService heartService;

    @PostMapping(value = "/artizens/{artizen_id}")
    public ResponseEntity<?> checkArtizenHeart(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                        @PathVariable(name = "artizen_id") String id){

        return heartService.checkArtizenHeart(memberDetails.getMember(), id);
    }


    @PostMapping(value = "/community/{community_id}")
    public ResponseEntity<?> checkCommunityHeart(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                 @PathVariable(name = "community_id") Long id){

        return heartService.checkCommunityHeart(memberDetails.getMember(), id);
    }
}
