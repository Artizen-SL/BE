package com.example.artizen.controller;

import com.example.artizen.dto.request.NotificationRequestDto;
import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notification")
@Api(tags = "Artizen notification (공지)")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "공지 작성", description = "공지 작성 기능")
    @PostMapping
    public ResponseEntity<?> createNotification(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                @ModelAttribute NotificationRequestDto requestDto) throws IOException {

        return notificationService.createNotification(memberDetails.getMember(), requestDto);
    }

    @Operation(summary = "중요 공지 리스트 조회", description = "중요 공지 TOP 5 리스트 조회 기능")
    @GetMapping(value = "/importance")
    public ResponseEntity<?> getTop5(){

        return notificationService.getTop5();
    }

    @Operation(summary = "일반 공지 리스트 조회", description = "일반 공지 리스트 조회 기능")
    @GetMapping
    public ResponseEntity<?> getNotificationList(){

        return notificationService.getNotificationList();
    }

    @Operation(summary = "공지 상세 조회", description = "각 공지별 상세 내용 조회 기능")
    @GetMapping(value = "/{notification_id}")
    public ResponseEntity<?> getNotification(@PathVariable(name = "notification_id") Long id){

        return notificationService.getNotification(id);
    }

    @Operation(summary = "공지 수정", description = "공지 수정 기능")
    @PutMapping(value = "/{notification_id}")
    public ResponseEntity<?> updateNotification(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                @PathVariable(name = "notification_id") Long id,
                                                @ModelAttribute NotificationRequestDto requestDto) throws IOException{

        return notificationService.updateNotification(memberDetails.getMember(), id, requestDto);
    }

    @Operation(summary = "공지 삭제", description = "공지 삭제 기능")
    @DeleteMapping(value = "/{notification_id}")
    public ResponseEntity<?> deleteNotification(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                @PathVariable(name = "notification_id") Long id){

        return notificationService.deleteNotification(memberDetails.getMember(), id);
    }
}
