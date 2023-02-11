package com.example.artizen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    POST_SUCCESS(HttpStatus.OK,"POST_SUCCESS","게시글 등록완료."),
    PUT_SUCCESS(HttpStatus.OK,"UPDATE_SUCCESS","게시글 수정완료."),
    DELETE_SUCCESS(HttpStatus.OK,"DELETE_SUCCESS","게시글 삭제완료."),
    CHECK_HEART(HttpStatus.OK, "PUSH_LIKE","좋아요를 눌렀습니다."),
    CANCEL_HEART(HttpStatus.OK,"CANCEL_LIKE","좋아요를 취소하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
