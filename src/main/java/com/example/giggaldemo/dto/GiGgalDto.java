package com.example.giggaldemo.dto;

import com.example.giggaldemo.entity.GiGgal;
import com.example.giggaldemo.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GiGgalDto {

    private Long id;
    private String name;
    private List<Long> memberIds;

    @Builder
    public GiGgalDto(Long id, String name, List<Long> memberIds) {
        this.id = id;
        this.name = name;
        this.memberIds = memberIds;
    }

    public GiGgal toEntity(String name, List<Member> member) {
        return GiGgal.builder()
                .name(name)
                .member(member)
                .build();
    }
}
