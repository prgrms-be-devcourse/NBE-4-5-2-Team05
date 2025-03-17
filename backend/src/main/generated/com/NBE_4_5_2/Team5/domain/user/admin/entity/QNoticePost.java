package com.NBE_4_5_2.Team5.domain.user.admin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticePost is a Querydsl query type for NoticePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticePost extends EntityPathBase<NoticePost> {

    private static final long serialVersionUID = 1673404489L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticePost noticePost = new QNoticePost("noticePost");

    public final com.NBE_4_5_2.Team5.domain.base.entity.QBaseTime _super = new com.NBE_4_5_2.Team5.domain.base.entity.QBaseTime(this);

    public final com.NBE_4_5_2.Team5.domain.user.user.entity.QUser admin;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath title = createString("title");

    public QNoticePost(String variable) {
        this(NoticePost.class, forVariable(variable), INITS);
    }

    public QNoticePost(Path<? extends NoticePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticePost(PathMetadata metadata, PathInits inits) {
        this(NoticePost.class, metadata, inits);
    }

    public QNoticePost(Class<? extends NoticePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new com.NBE_4_5_2.Team5.domain.user.user.entity.QUser(forProperty("admin")) : null;
    }

}

