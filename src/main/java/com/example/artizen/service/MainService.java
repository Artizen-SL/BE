package com.example.artizen.service;

import com.example.artizen.dto.response.MainResponseDto;
import com.example.artizen.entity.Artizen;
import com.example.artizen.repository.ArtizenHeartRepository;
import com.example.artizen.repository.ArtizenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ArtizenRepository artizenRepository;
    private final ArtizenHeartRepository artizenHeartRepository;

    public ResponseEntity<?> getLocation(double latitude, double longitude) {

//        List<MainResponseDto> mainResponseDtoList = new ArrayList<>();
//
//        //연극/뮤지컬 추천
//        if (genre.contains("연극/뮤지컬")) {
//            List<Artizen> theaterList = artizenRepository.findAllByCategoryContains("연극");
//            List<Artizen> musicalList = artizenRepository.findAllByCategoryContains("뮤지컬");
//
//            List<MainResponseDto> artizenList = new ArrayList<>();
//
//            addArtizen(theaterList, artizenList);
//            addArtizen(musicalList, artizenList);
//
//        } else if (genre.contains("콘서트")) {
//            List<Artizen> concertList = artizenRepository.findAllByCategoryContains("대중음악");
//
//            List<MainResponseDto> artizenList = new ArrayList<>();
//
//            addArtizen(concertList, artizenList);
//
//            return ResponseEntity.ok(artizenList);
//
//        } else if (genre.contains("클래식/무용")) {
//            List<Artizen> classicList = artizenRepository.findAllByCategoryContains("클래식");
//            List<Artizen> dancingList = artizenRepository.findAllByCategoryContains("무용");
//
//            List<MainResponseDto> artizenList = new ArrayList<>();
//
//            addArtizen(classicList, artizenList);
//            addArtizen(dancingList, artizenList);
//
//            return ResponseEntity.ok(artizenList);
//
//        } else {
//            List<Artizen> circusList = artizenRepository.findAllByCategoryContains("서커스/마술");
//
//            List<MainResponseDto> artizenList = new ArrayList<>();
//
//            addArtizen(circusList, artizenList);
//
//            return ResponseEntity.ok(artizenList);
//        }
//
//        //콘서트 추천
//        getDistance(x,y);
//
//        //클래식/무용 추천
//
//        //마술/서커스 추천
//
//
        return new ResponseEntity<>("미완성", HttpStatus.OK);
    }
//
//    /*
//     * 두 지점간의 거리 계산
//     *
//     * @param lat1 지점 1 위도
//     * @param lon1 지점 1 경도
//     * @param lat2 지점 2 위도
//     * @param lon2 지점 2 경도
//     * @param unit 거리 표출단위
//     * @return
//     */
//    private static double getDistance(double userLatitude, double userLongitude, double lat2, double lon2, String unit) {
//
//        double theta = userLongitude - lon2;
//        double dist = Math.sin(deg2rad(userLatitude)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(userLatitude)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//
//        if (unit == "kilometer") {
//            dist = dist * 1.609344;
//        } else if(unit == "meter"){
//            dist = dist * 1609.344;
//        }
//
//        return (dist);
//    }
//
//
//    // This function converts decimal degrees to radians
//    private static double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    // This function converts radians to decimal degrees
//    private static double rad2deg(double rad) {
//        return (rad * 180 / Math.PI);
//    }



    //Best artizen (4개)
    public ResponseEntity<?> getBestArtizen() {
        List<Artizen> artizens = artizenRepository.findTop4ByOrderByTotalHeartDesc();

        List<MainResponseDto> mainResponseDtoList = new ArrayList<>();
        for (Artizen artizen : artizens) {
            mainResponseDtoList.add(new MainResponseDto(artizen));
        }

        return new ResponseEntity<>(mainResponseDtoList, HttpStatus.OK);
    }

    //New artizen (3개)
    public ResponseEntity<?> getNewArtizen() {
        List<Artizen> newArtizen = artizenRepository.findTop3ByOrderByCreatedAtDesc();

        List<MainResponseDto> mainResponseDtoList = new ArrayList<>();
        for(Artizen artizen : newArtizen) {
            mainResponseDtoList.add(new MainResponseDto(artizen));
        }

        return new ResponseEntity<>(mainResponseDtoList, HttpStatus.OK);
    }
}
