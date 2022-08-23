package com.example.giggaldemo.dto;

import com.example.giggaldemo.entity.Charge;
import com.example.giggaldemo.entity.GiGgal;
import com.example.giggaldemo.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private Charge charge;
    private Long giGgalId;

    @Builder
    public MemberDto(Long id, String name, Charge charge, Long giGgalId) {
        this.id = id;
        this.name = name;
        this.charge = charge;
        this.giGgalId = giGgalId;
    }

    public Member toEntity(GiGgal giGgal) {
        return Member.builder()
                .name(this.name)
                .charge(this.charge)
                .giGgal(giGgal)
                .build();
    }
}
