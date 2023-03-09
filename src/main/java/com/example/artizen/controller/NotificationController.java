package com.example.artizen.controller;

import com.example.artizen.dto.request.NotificationRequestDto;
import com.example.artizen.security.MemberDetailsImpl;
import com.example.artizen.service.NotificationService;
import io.swagger.annotations.Api;
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

    @PostMapping
    public ResponseEntity<?> createNotification(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                @ModelAttribute NotificationRequestDto requestDto) throws IOException {

        return notificationService.createNotification(memberDetails.getMember(), requestDto);
    }


    @GetMapping(value = "/importance")
    public ResponseEntity<?> getTop5(){

        return notificationService.getTop5();
    }


    @GetMapping
    public ResponseEntity<?> getNotificationList(){

        return notificationService.getNotificationList();
    }


    @GetMapping(value = "/{notification_id}")
    public ResponseEntity<?> getNotification(@PathVariable(name = "notification_id") Long id){

        return notificationService.getNotification(id);
    }


    @PutMapping(value = "/{notification_id}")
    public ResponseEntity<?> updateNotification(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                @PathVariable(name = "notification_id") Long id,
                                                @ModelAttribute NotificationRequestDto requestDto) throws IOException{

        return notificationService.updateNotification(memberDetails.getMember(), id, requestDto);
    }


    @DeleteMapping(value = "/{notification_id}")
    public ResponseEntity<?> deleteNotification(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                @PathVariable(name = "notification_id") Long id){

        return notificationService.deleteNotification(memberDetails.getMember(), id);
    }
}
