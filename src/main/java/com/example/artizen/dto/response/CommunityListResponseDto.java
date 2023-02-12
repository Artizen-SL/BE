package com.example.artizen.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CommunityListResponseDto {
    private List<CommunityResponseDto> communityList;
    private Boolean isLast;

    public CommunityListResponseDto(List<CommunityResponseDto> communityList, Boolean isLast){
        this.communityList = communityList;
        this.isLast = isLast;
    }
}
