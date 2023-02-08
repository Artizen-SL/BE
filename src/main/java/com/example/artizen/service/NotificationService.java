package com.example.artizen.service;

import com.example.artizen.dto.request.NotificationRequestDto;
import com.example.artizen.entity.Notification;
import com.example.artizen.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> getTop5() {

        List<Notification> importanceList = notificationRepository.findTop5ByImportanceIsTrueOrderByCreatedAtDesc();

        return new ResponseEntity<>(importanceList, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getNotificationList() {

        List<Notification> notificationList = notificationRepository.findAllByImportanceIsFalseOrderByCreatedAtDesc();

        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> getNotification(Long id) {

        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지글입니다."));

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> updateNotification(Long id, NotificationRequestDto requestDto) {

        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지글입니다."));
        notification.update(requestDto);

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> deleteNotification(Long id) {

        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지글입니다."));
        notificationRepository.delete(notification);

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }
}
