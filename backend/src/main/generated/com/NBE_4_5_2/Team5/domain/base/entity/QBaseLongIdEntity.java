package com.NBE_4_5_2.Team5.domain.base.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseLongIdEntity is a Querydsl query type for BaseLongIdEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseLongIdEntity extends EntityPathBase<BaseLongIdEntity> {

    private static final long serialVersionUID = -873413823L;

    public static final QBaseLongIdEntity baseLongIdEntity = new QBaseLongIdEntity("baseLongIdEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBaseLongIdEntity(String variable) {
        super(BaseLongIdEntity.class, forVariable(variable));
    }

    public QBaseLongIdEntity(Path<? extends BaseLongIdEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseLongIdEntity(PathMetadata metadata) {
        super(BaseLongIdEntity.class, metadata);
    }

}

