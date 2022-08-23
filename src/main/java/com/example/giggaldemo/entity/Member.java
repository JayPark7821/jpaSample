package com.example.giggaldemo.entity;

import com.example.giggaldemo.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    // 기본이 숫자로 들어가는데 나중에 enum이 추가되면
    // 기존에 저장되어있는 데이터가 꼬일수 있기때문에....
    // ㄴtring으로 사용하는것을 권장
    @Column(name = "charge")
    @Enumerated(EnumType.STRING)
    private Charge charge;


    // XToOne관계는 기본 FetchType이 EAGER
    // 성능 최적화를 위해선 FetchType을 일단 전부 LAZY로 세팅후 개발자가 필요에따라
    // 직접 최적화진행
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giggal_id")
    private GiGgal giGgal;

    @Builder
    public Member(Long id, String name, Charge charge, GiGgal giGgal) {
        this.id = id;
        this.name = name;
        this.charge = charge;
        this.giGgal = giGgal;
    }

    public void update(MemberDto requestDto,GiGgal giGgal) {
        this.name = requestDto.getName();
        this.charge = requestDto.getCharge();
        this.giGgal = giGgal;
    }

    // 연관관계 메서드 GiGgal과 member의 연관관계 세팅
    public void setGiGgal(GiGgal giGgal) {
        this.giGgal = giGgal;
    }
}
