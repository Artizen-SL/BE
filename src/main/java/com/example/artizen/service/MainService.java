package com.example.artizen.service;

import com.example.artizen.dto.response.MainResponseDto;
import com.example.artizen.entity.Artizen;
import com.example.artizen.repository.ArtizenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ArtizenRepository artizenRepository;

    public ResponseEntity<?> getLocation(String latitude, String longitude) {

        //연극/뮤지컬 추천
        List<Artizen> artizenTheaters = artizenRepository.findAllByCategoryContains("연극");
        List<Artizen> artizenMusicals = artizenRepository.findAllByCategoryContains("뮤지컬");

        String placeName = findShortDist(artizenTheaters, artizenMusicals, latitude, longitude);

        List<Artizen> suggestion = new ArrayList<>();
        if (findSuggestion(placeName, "연극") != null) {
            suggestion.add(findSuggestion(placeName, "연극"));
        }

        if (findSuggestion(placeName, "뮤지컬") != null) {
            suggestion.add(findSuggestion(placeName, "뮤지컬"));
        }

        Collections.sort(suggestion, new artizenComparator());

        List<MainResponseDto> mainResponseDtoList = new ArrayList<>();
        mainResponseDtoList.add(new MainResponseDto(suggestion.get(0)));

        //콘서트 추천
        List<Artizen> artizenConcert = artizenRepository.findAllByCategoryContains("대중음악");

        String placeName2 = findShortDist(artizenConcert, latitude, longitude);

        List<Artizen> suggestion2 = new ArrayList<>();
        if (findSuggestion(placeName2, "대중음악") != null) {
            suggestion2.add(findSuggestion(placeName2, "대중음악"));
        }

        Collections.sort(suggestion2, new artizenComparator());

        mainResponseDtoList.add(new MainResponseDto(suggestion2.get(0)));

        //클래식/무용 추천
        List<Artizen> artizenClassic = artizenRepository.findAllByCategoryContains("클래식");
        List<Artizen> artizenDancing = artizenRepository.findAllByCategoryContains("무용");

        String placeName3 = findShortDist(artizenClassic, artizenDancing, latitude, longitude);

        List<Artizen> suggestion3 = new ArrayList<>();
        if (findSuggestion(placeName3, "클래식") != null) {
            suggestion3.add(findSuggestion(placeName3, "클래식"));
        }

        if (findSuggestion(placeName3, "무용") != null) {
            suggestion3.add(findSuggestion(placeName3, "무용"));
        }

        Collections.sort(suggestion3, new artizenComparator());

        mainResponseDtoList.add(new MainResponseDto(suggestion3.get(0)));

        //마술/서커스 추천
        List<Artizen> artizenCircus = artizenRepository.findAllByCategoryContains("서커스/마술");

        String placeName4 = findShortDist(artizenCircus, latitude, longitude);

        List<Artizen> suggestion4 = new ArrayList<>();
        if (findSuggestion(placeName4, "서커스/마술") != null) {
            suggestion4.add(findSuggestion(placeName4, "서커스/마술"));
        }

        Collections.sort(suggestion4, new artizenComparator());

        mainResponseDtoList.add(new MainResponseDto(suggestion4.get(0)));

        return new ResponseEntity<>(mainResponseDtoList, HttpStatus.OK);
    }

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

    class artizenComparator implements Comparator<Artizen> {
        @Override
        public int compare(Artizen a1, Artizen a2) {
            if (Integer.parseInt(a1.getDate().replace(".", "").substring(0,8)) > Integer.parseInt(a2.getDate().replace(".", "").substring(0,8))) {
                return 1;
            } else if (Integer.parseInt(a1.getDate().replace(".", "").substring(0,8)) < Integer.parseInt(a2.getDate().replace(".", "").substring(0,8))) {
                return -1;
            }
            return 0;
        }
    }

    public String findShortDist(List<Artizen> artizenList1, List<Artizen> artizenList2, String latitude, String longitude) {
        HashMap<String, String> show = new HashMap<>();

        for(Artizen artizen1 : artizenList1) {
            show.put(artizen1.getPlace(), Arrays.toString(artizen1.getLocation()));
        }

        for(Artizen artizen2 : artizenList2) {
            show.put(artizen2.getPlace(), Arrays.toString(artizen2.getLocation()));
        }

        double minDist = 0;
        String placeName = "";

        for(String s : show.keySet()){

            double horizontalDist = Math.abs(Double.parseDouble(latitude) - Double.parseDouble(show.get(s).split(",")[0].substring(1)));
            double verticalDist = Math.abs(Double.parseDouble(longitude) - Double.parseDouble(show.get(s).substring(0,show.get(s).length()-1).split(",")[1]));

            double totalDist = Math.sqrt(Math.pow(horizontalDist, 2) + Math.pow(verticalDist, 2));

            if(minDist == 0) {
                minDist = totalDist;
                placeName = s;
            } else if(minDist > totalDist) {
                minDist = totalDist;
                placeName = s;
            }
        }

        return placeName;
    }

    public String findShortDist(List<Artizen> artizenList1, String latitude, String longitude) {
        HashMap<String, String> show = new HashMap<>();

        for(Artizen artizen1 : artizenList1) {
            show.put(artizen1.getPlace(), Arrays.toString(artizen1.getLocation()));
        }

        double minDist = 0;
        String placeName = "";

        for(String s : show.keySet()){

            double horizontalDist = Math.abs(Double.parseDouble(latitude) - Double.parseDouble(show.get(s).split(",")[0].substring(1)));
            double verticalDist = Math.abs(Double.parseDouble(longitude) - Double.parseDouble(show.get(s).substring(0,show.get(s).length()-1).split(",")[1]));

            double totalDist = Math.sqrt(Math.pow(horizontalDist, 2) + Math.pow(verticalDist, 2));

            if(minDist == 0) {
                minDist = totalDist;
                placeName = s;
            } else if(minDist > totalDist) {
                minDist = totalDist;
                placeName = s;
            }
        }

        return placeName;
    }

    public Artizen findSuggestion(String placeName, String category) {
        List<Artizen> artizenSuggestion = artizenRepository.findByPlaceAndCategoryContains(placeName, category);

        List<Artizen> suggestion = new ArrayList<>();
        suggestion.addAll(artizenSuggestion);

        Collections.sort(suggestion, new artizenComparator());

        if (suggestion.isEmpty()){
            return null;
        } else return suggestion.get(0);
    }
}
