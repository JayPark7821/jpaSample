package com.example.giggaldemo.service;

import com.example.giggaldemo.common.DefaultRes;
import com.example.giggaldemo.common.Pagination;
import com.example.giggaldemo.dto.GiGgalDto;
import com.example.giggaldemo.dto.GiGgalMemberResponseDto;
import com.example.giggaldemo.dto.MemberDto;
import com.example.giggaldemo.entity.GiGgal;
import com.example.giggaldemo.entity.Member;
import com.example.giggaldemo.repository.GiGgalRepository;
import com.example.giggaldemo.repository.MemberRepository;
import com.example.giggaldemo.repository.SampleQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleService {

    private final SampleQueryRepository sampleQueryRepository;
    private final GiGgalRepository giGgalRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Map<String, Long> saveMember(MemberDto requestDto) {
        // id 유무로 update / insert 분기
        if (requestDto.getId() != null) {
            // id가 null이 아니면 update
            Long memberId = requestDto.getId();
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException(memberId + "에 해당하는 member를 찾을 수 없습니다."));

            member.update(requestDto,
                    // giggal id가 넘어왔으면 db에서 giggal 조회후 update 없으면 null로 update
                    requestDto.getGiGgalId() != null ?
                            giGgalRepository.findById(requestDto.getGiGgalId())
                                    .orElseThrow(() -> new IllegalArgumentException(requestDto.getGiGgalId() + "에 해당하는 giggal을 찾을 수 없습니다."))
                            : null);

            return Map.of("updated", memberId);

        } else {
            // id가 null이면 insert
            Member member = memberRepository.save(requestDto.toEntity(requestDto.getGiGgalId() != null ?
                    giGgalRepository.findById(requestDto.getGiGgalId())
                            .orElseThrow(() -> new IllegalArgumentException(requestDto.getGiGgalId() + "에 해당하는 giggal을 찾을 수 없습니다."))
                    : null));

            return Map.of("inserted", member.getId());

        }
    }

    @Transactional
    public Map<String, Long> saveGiGgal(GiGgalDto requestDto) {
        if (requestDto.getId() != null) {
            // giggal id값이 있으면 db에서 조회
            Long giGgalId = requestDto.getId();
            GiGgal giGgal = giGgalRepository.findById(giGgalId).orElseThrow(() -> new IllegalArgumentException(giGgalId + "에 해당하는 giggal을 찾을 수 없습니다."));
           // 넘어온 맴버 아이디 리스트로 맴버 조회
            List<Member> memberList = validateAndGetMember(requestDto);
            // 넘어온 giggal id에 매핑되어있던 맴버 조회
            List<Member> originalMember = memberRepository.findByGiGgal(giGgal);

            originalMember.stream().filter(om -> !memberList.contains(om)).collect(Collectors.toList()).forEach(om->om.setGiGgal(null));
            memberList.forEach(m -> m.setGiGgal(giGgal));

            return Map.of("updated", giGgalId);
        }else{
            // giggal id 값이 없으면 giggal entity 새로 생성후 저장
            List<Member> memberList = validateAndGetMember(requestDto);
            GiGgal giGgal = requestDto.toEntity(requestDto.getName(), memberList);
            GiGgal savedGiGgal = giGgalRepository.save(giGgal);
            return Map.of("inserted", savedGiGgal.getId());
        }
    }


    private List<Member> validateAndGetMember(GiGgalDto requestDto) {
        if (requestDto.getMemberIds() == null) {
            return null;
        }
        // requestBody로 받은 idList를 넘겨 한번에 member조회
        List<Member> memberList = memberRepository.findByIds(requestDto.getMemberIds());

        // 넘어온 맴버 id수와 db에서 조회된 맴버 리스트의 수 비교
        if (memberList.size() != requestDto.getMemberIds().size()) {
            throw new IllegalArgumentException("넘어온 맴버id중 없는 맴버id가 있습니다.");
        }
        return memberList;

    }

    public DefaultRes<List<GiGgalMemberResponseDto>> searchAll(String name, Pageable pageable) {
        Page<GiGgal> results = sampleQueryRepository.searchAll(name, pageable);
        return DefaultRes.response(200, "ok", results.map(GiGgalMemberResponseDto::new).getContent(),
                Pagination.builder()
                        .currentPage(results.getNumber())
                        .totalPages(results.getTotalPages())
                        .totalElements(results.getTotalElements())
                        .build());
    }
}
