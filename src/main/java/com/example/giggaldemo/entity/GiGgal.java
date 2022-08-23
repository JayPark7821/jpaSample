package com.example.giggaldemo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class GiGgal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// id db자동 생성 전략
    @Column(name = "giggal_id")// db 컬럼명 지정
    private Long id;

    private String name;

    // 1개의 기깔팀 ( One ) 에 여러맴버 (Many)  Member의 giGgal에 mapping
    // giggal 과 member의 연관관계에서 연관관계의 주인은 member
    @OneToMany(mappedBy = "giGgal")
    private List<Member> member = new ArrayList<>(); // null 방지를 위해 new ArrayList로 초기화

    @Builder
    public GiGgal(String name, List<Member> member) {
        this.name = name;
        if (member != null) {
            for (Member m : member) {
                this.setMember(m);
            }
        }

    }

    public void update(String name, List<Member> member) {

    }

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member.add(member);
        member.setGiGgal(this);
    }
}
