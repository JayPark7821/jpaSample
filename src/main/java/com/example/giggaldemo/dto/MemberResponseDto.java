package com.example.giggaldemo.dto;

import com.example.giggaldemo.entity.Charge;
import com.example.giggaldemo.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberResponseDto {

    private Long memberId;
    private String memberName;
    private Charge charge;

    public MemberResponseDto(Member entity) {
        this.memberId = entity.getId();
        this.memberName = entity.getName();
        this.charge = entity.getCharge();
    }
}
