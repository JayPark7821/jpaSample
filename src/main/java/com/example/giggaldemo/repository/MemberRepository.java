package com.example.giggaldemo.repository;

import com.example.giggaldemo.entity.GiGgal;
import com.example.giggaldemo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.id in :ids")
    List<Member> findByIds(@Param("ids") Collection<Long> ids);

    List<Member> findByGiGgal(GiGgal giGgal);
}
