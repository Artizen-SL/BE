package com.example.artizen.controller;

import com.example.artizen.dto.request.NotificationRequestDto;
import com.example.artizen.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> createNotification(@ModelAttribute NotificationRequestDto requestDto) throws IOException {

        return notificationService.createNotification(requestDto);
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
    public ResponseEntity<?> updateNotification(@PathVariable(name = "notification_id") Long id,
                                                @ModelAttribute NotificationRequestDto requestDto){

        return notificationService.updateNotification(id, requestDto);
    }


    @DeleteMapping(value = "/{notification_id}")
    public ResponseEntity<?> deleteNotification(@PathVariable(name = "notification_id") Long id){

        return notificationService.deleteNotification(id);
    }
}
