package com.example.artizen.service;

import com.example.artizen.dto.request.NotificationRequestDto;
import com.example.artizen.dto.response.MessageDto;
import com.example.artizen.dto.response.NotificationResponseDto;
import com.example.artizen.entity.Notification;
import com.example.artizen.entity.ResponseCode;
import com.example.artizen.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${cloud.aws.s3.dir}")
    private String dir;
    private final NotificationRepository notificationRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public ResponseEntity<?> createNotification(NotificationRequestDto requestDto) throws IOException {

        Notification notification = new Notification(requestDto, s3UploadService, dir);
        notificationRepository.save(notification);

        return ResponseEntity.status(ResponseCode.POST_SUCCESS.getStatus()).body(new MessageDto(ResponseCode.POST_SUCCESS.getCode(), ResponseCode.POST_SUCCESS.getMsg() ));
    }


    @Transactional
    public ResponseEntity<?> getTop5() {

        List<Notification> notificationList = notificationRepository.findTop5ByImportanceIsTrueOrderByCreatedAtDesc();
        List<Notification> importanceList = new ArrayList<>();

        for (Notification notification : notificationList){
            importanceList.add(notification);
        }

        return new ResponseEntity<>(importanceList, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getNotificationList() {

        List<Notification> notificationList = notificationRepository.findAllByImportanceIsFalseOrderByCreatedAtDesc();
        List<Notification> notificationList2 = new ArrayList<>();

        for (Notification notification: notificationList){
            notificationList2.add(notification);
        }

        return new ResponseEntity<>(notificationList2, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> getNotification(Long id) {

        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지글입니다."));

        return new ResponseEntity<>(new NotificationResponseDto(notification), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> updateNotification(Long id, NotificationRequestDto requestDto) throws IOException {

        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지글입니다."));
        notification.update(requestDto, s3UploadService, dir);

        return ResponseEntity.status(ResponseCode.PUT_SUCCESS.getStatus()).body(new MessageDto(ResponseCode.PUT_SUCCESS.getCode(), ResponseCode.PUT_SUCCESS.getMsg()));
    }


    @Transactional
    public ResponseEntity<?> deleteNotification(Long id) {

        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지글입니다."));
        notificationRepository.delete(notification);

        return ResponseEntity.status(ResponseCode.DELETE_SUCCESS.getStatus()).body(new MessageDto(ResponseCode.DELETE_SUCCESS.getCode(), ResponseCode.DELETE_SUCCESS.getMsg()));
    }
}
