package com.example.giggaldemo.repository;

import com.example.giggaldemo.common.DefaultRes;
import com.example.giggaldemo.dto.GiGgalDto;
import com.example.giggaldemo.dto.GiGgalMemberResponseDto;
import com.example.giggaldemo.entity.GiGgal;
import com.example.giggaldemo.entity.QGiGgal;
import com.example.giggaldemo.entity.QMember;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.example.giggaldemo.entity.QGiGgal.*;
import static com.example.giggaldemo.entity.QMember.*;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Repository
public class SampleQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public SampleQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<GiGgal> searchAll(String name, Pageable pageable) {

        List<GiGgal> results = queryFactory
                .selectFrom(giGgal)
                .leftJoin(giGgal.member,member).fetchJoin()
                .where(giGgalNameContains(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<GiGgal> countQuery = queryFactory
                .selectFrom(giGgal)
                .where(giGgalNameContains(name));

        return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetch().size());

    }

    private BooleanExpression giGgalNameContains(String name) {
        return hasText(name) ? giGgal.name.containsIgnoreCase(name) : null;
    }
}
