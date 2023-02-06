package com.example.artizen.service;

import com.example.artizen.dto.response.ArtizenResponseDto;
import com.example.artizen.entity.Artizen;
import com.example.artizen.repository.ArtizenRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArtizenService {
    private final ArtizenRepository artizenRepository;

    @Value("${secretKey}")
    String key;

    @Transactional
    public ResponseEntity<?> searchShow(int stDate, int edDate, int page, int rows, int prfState) {

        List<Map<String, Object>> showList = new ArrayList<>();

        try {

            String realState = convertState(prfState);

            String url = "https://www.kopis.or.kr/openApi/restful/pblprfr?service=" + key + "&stdate=" + stDate + "&eddate=" + edDate + "&cpage=" + page + "&rows=" + rows + "&prfstate=" + realState;

            RestTemplate restTemplate = new RestTemplate();
            String xmlResult = restTemplate.getForObject(url, String.class);

            JSONObject jsonObject = XML.toJSONObject(xmlResult);

            JSONArray jsonArray = jsonObject.getJSONObject("dbs").getJSONArray("db");

            for (int i = 0; i < jsonArray.length(); i++) {
                String showId = jsonArray.getJSONObject(i).get("mt20id").toString();
                String name = jsonArray.getJSONObject(i).get("prfnm").toString();
                String startDate = jsonArray.getJSONObject(i).get("prfpdfrom").toString();
                String endDate = jsonArray.getJSONObject(i).get("prfpdto").toString();
                String facility = jsonArray.getJSONObject(i).get("fcltynm").toString();
                String posterUrl = jsonArray.getJSONObject(i).get("poster").toString();
                String genre = jsonArray.getJSONObject(i).get("genrenm").toString();
                String state = jsonArray.getJSONObject(i).get("prfstate").toString();
                String openrun = jsonArray.getJSONObject(i).get("openrun").toString();

                ArtizenResponseDto artizenResponseDto = new ArtizenResponseDto(showId, name, genre, state, startDate, endDate, posterUrl, facility);
                existArtizen(artizenResponseDto);

                Map<String, Object> show = new HashMap<>();

                show.put("showId", showId);
                show.put("name", name);
                show.put("startDate", startDate);
                show.put("endDate", endDate);
                show.put("facility", facility);
                show.put("posterUrl", posterUrl);
                show.put("genre", genre);
                show.put("state", state);
                show.put("openrun", openrun);

                showList.add(show);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(showList);
    }


    @Transactional
    public void updateDetailOne(String id) {

        try {

            String url = "https://www.kopis.or.kr/openApi/restful/pblprfr/" + id + "?service=" + key;

            RestTemplate restTemplate = new RestTemplate();
            String xmlResult = restTemplate.getForObject(url, String.class);

            JSONObject jsonObject = XML.toJSONObject(xmlResult);

            JSONObject obj2 = jsonObject.getJSONObject("dbs").getJSONObject("db");

            String showId = obj2.get("mt20id").toString();
            String name = obj2.get("prfnm").toString();
            String startDate = obj2.get("prfpdfrom").toString();
            String endDate = obj2.get("prfpdto").toString();
            String facility = obj2.get("fcltynm").toString();
            String staff = obj2.get("prfcast").toString();
            String price = obj2.get("pcseguidance").toString();
            String content = obj2.get("sty").toString();
            String posterUrl = obj2.get("poster").toString();
            String genre = obj2.get("genrenm").toString();
            String state = obj2.get("prfstate").toString();
//            String runTime = obj2.get("prfruntime").toString();
//            String ageRange = obj2.get("prfage").toString();
//            String performanceTime = obj2.get("dtguidance").toString();
//            String openrun = obj2.get("openrun").toString();

            ArtizenResponseDto artizenResponseDto = new ArtizenResponseDto(showId, name, genre, state, startDate, endDate, posterUrl, facility, price, content, staff);

            Artizen artizen = artizenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공연입니다."));
            artizen.update(artizenResponseDto);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Transactional(readOnly = true)
    public ResponseEntity<?> getArtizenList(String genre) {

        List<Artizen> artizenList = artizenRepository.findAllByCategoryContains(genre);
        List<ArtizenResponseDto> artizenResponseDtoList = new ArrayList<>();

        for (Artizen artizen : artizenList) {

            artizenResponseDtoList.add(new ArtizenResponseDto(artizen));
        }

        return ResponseEntity.ok(artizenResponseDtoList);
    }


    @Transactional
    public ResponseEntity<?> getArtizen(String id) {

        Artizen artizen = artizenRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 공연이 존재하지 않습니다."));

        if (artizen.getPrice() == null) {
            updateDetailOne(id);
        }

        return ResponseEntity.ok(artizen);
    }


    @Transactional
    public ResponseEntity<?> searchArtizen(String keyword, int page, int size){

        Pageable pageable = PageRequest.of(page, size);

        Page<Artizen> artizenList1 = artizenRepository.findAllByNameContainsOrderByCreatedAt(keyword, pageable);
        Page<Artizen> aritzenList2 = artizenRepository.findAllByCategoryContainsOrderByCreatedAt(keyword,pageable);
        Page<Artizen> artizenList3 = artizenRepository.findAllByPlaceContainsOrderByCreatedAt(keyword, pageable);
        Page<Artizen> artizenList4 = artizenRepository.findAllByContentContainsOrderByCreatedAt(keyword, pageable);

        List<ArtizenResponseDto> artizenResponseDtoList = new ArrayList<>();

        addElements(artizenList1, artizenResponseDtoList);
        addElements(aritzenList2, artizenResponseDtoList);
        addElements(artizenList3, artizenResponseDtoList);
        addElements(artizenList4, artizenResponseDtoList);

        return ResponseEntity.ok(artizenResponseDtoList);
    }


    public String convertState(int state) {
        if (state == 1) {
            return "01";
        } else if (state == 2) {
            return "02";
        } else {
            return "03";
        }
    }

    public void existArtizen(ArtizenResponseDto artizenResponseDto) {
        if (!artizenRepository.existsById(artizenResponseDto.getId())) {
            Artizen artizen = new Artizen(artizenResponseDto);
            artizenRepository.save(artizen);
        }
    }

    public void addElements(Page<Artizen> pageList, List<ArtizenResponseDto> list){
        for (Artizen artizen : pageList){
            list.add(new ArtizenResponseDto(artizen));
        }
    }

}
