package com.example.artizen.dto.response;

import lombok.Getter;
import java.util.List;

@Getter
public class MypageListResponseDto {

    private List<MypageResponseDto> mypageList;
    private Boolean isLast;

    public MypageListResponseDto(List<MypageResponseDto> mypageList, Boolean isLast){
        this.mypageList = mypageList;
        this.isLast = isLast;
    }
}
