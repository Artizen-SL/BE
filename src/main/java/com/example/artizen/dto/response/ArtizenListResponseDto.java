package com.example.artizen.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ArtizenListResponseDto {
    private List<ArtizenResponseDto> artizenList;
    private Boolean isLast;

    public ArtizenListResponseDto(List<ArtizenResponseDto> artizenList, Boolean isLast){
        this.artizenList = artizenList;
        this.isLast = isLast;
    }
}
