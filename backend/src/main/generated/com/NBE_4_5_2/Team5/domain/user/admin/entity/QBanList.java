package com.NBE_4_5_2.Team5.domain.user.admin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBanList is a Querydsl query type for BanList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBanList extends EntityPathBase<BanList> {

    private static final long serialVersionUID = 1113488956L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBanList banList = new QBanList("banList");

    public final com.NBE_4_5_2.Team5.domain.user.user.entity.QUser bannedUser;

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final StringPath reason = createString("reason");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public QBanList(String variable) {
        this(BanList.class, forVariable(variable), INITS);
    }

    public QBanList(Path<? extends BanList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBanList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBanList(PathMetadata metadata, PathInits inits) {
        this(BanList.class, metadata, inits);
    }

    public QBanList(Class<? extends BanList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bannedUser = inits.isInitialized("bannedUser") ? new com.NBE_4_5_2.Team5.domain.user.user.entity.QUser(forProperty("bannedUser")) : null;
    }

}

