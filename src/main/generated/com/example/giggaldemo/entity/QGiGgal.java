package com.example.giggaldemo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGiGgal is a Querydsl query type for GiGgal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGiGgal extends EntityPathBase<GiGgal> {

    private static final long serialVersionUID = -1347903669L;

    public static final QGiGgal giGgal = new QGiGgal("giGgal");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Member, QMember> member = this.<Member, QMember>createList("member", Member.class, QMember.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QGiGgal(String variable) {
        super(GiGgal.class, forVariable(variable));
    }

    public QGiGgal(Path<? extends GiGgal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGiGgal(PathMetadata metadata) {
        super(GiGgal.class, metadata);
    }

}

