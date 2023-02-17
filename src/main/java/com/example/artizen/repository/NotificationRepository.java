package com.example.artizen.repository;

import com.example.artizen.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findTop5ByImportanceIsTrueOrderByCreatedAtDesc();

    List<Notification> findAllByImportanceIsFalseOrderByCreatedAtDesc();
}
