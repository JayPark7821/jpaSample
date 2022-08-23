package com.example.giggaldemo.dto;

import com.example.giggaldemo.entity.GiGgal;
import com.example.giggaldemo.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GiGgalMemberResponseDto {

    private Long id;
    private String name;
    private List<MemberResponseDto> memberList = new ArrayList<>();



    public void addMemberToResponseDto(MemberResponseDto member) {
        this.memberList.add(member);
    }


    public GiGgalMemberResponseDto(GiGgal entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        if (entity.getMember().size() == 0) {
            this.memberList = new ArrayList<>();
        }else{
            for (Member member : entity.getMember()) {
                this.addMemberToResponseDto(new MemberResponseDto(member));
            }
        }
    }
}
