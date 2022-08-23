package com.example.giggaldemo.controller;

import com.example.giggaldemo.common.DefaultRes;
import com.example.giggaldemo.dto.GiGgalDto;
import com.example.giggaldemo.dto.GiGgalMemberResponseDto;
import com.example.giggaldemo.dto.MemberDto;
import com.example.giggaldemo.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/check")
    public DefaultRes<String> appHealthCheck() {
        return  DefaultRes.response(200, "ok","ok");
    }

    @PostMapping("/member")
    public DefaultRes<Map<String,Long>> saveMember(@RequestBody MemberDto requestDto) {
        return DefaultRes.response(200, "ok",sampleService.saveMember(requestDto)) ;
    }

    @PostMapping("/giggal")
    public DefaultRes<Map<String,Long>> saveGiGgal(@RequestBody GiGgalDto requestDto) {
        return DefaultRes.response(200, "ok",sampleService.saveGiGgal(requestDto)) ;
    }

    @GetMapping("/all")//ex) /all?name=gi&page=1&size=3     name like 검색, page 조회할 page, size 한페이지당 보여줄 데이터 크기
    public DefaultRes<List<GiGgalMemberResponseDto>> searchAll(String name, Pageable pageable) {
        return sampleService.searchAll(name, pageable);
    }
 }
