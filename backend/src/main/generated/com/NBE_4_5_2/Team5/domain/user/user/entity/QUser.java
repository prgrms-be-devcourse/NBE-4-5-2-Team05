package com.NBE_4_5_2.Team5.domain.user.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 192303758L;

    public static final QUser user = new QUser("user");

    public final com.NBE_4_5_2.Team5.domain.base.entity.QBaseTime _super = new com.NBE_4_5_2.Team5.domain.base.entity.QBaseTime(this);

    public final StringPath address = createString("address");

    public final BooleanPath blocked = createBoolean("blocked");

    public final NumberPath<Integer> blockedCount = createNumber("blockedCount", Integer.class);

    public final NumberPath<Integer> cash = createNumber("cash", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileUrl = createString("profileUrl");

    public final ListPath<com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost, com.NBE_4_5_2.Team5.domain.post.post.entity.QProductPost> purchasedProducts = this.<com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost, com.NBE_4_5_2.Team5.domain.post.post.entity.QProductPost>createList("purchasedProducts", com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost.class, com.NBE_4_5_2.Team5.domain.post.post.entity.QProductPost.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath username = createString("username");

    public final ListPath<com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost, com.NBE_4_5_2.Team5.domain.post.post.entity.QProductPost> writtenProducts = this.<com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost, com.NBE_4_5_2.Team5.domain.post.post.entity.QProductPost>createList("writtenProducts", com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost.class, com.NBE_4_5_2.Team5.domain.post.post.entity.QProductPost.class, PathInits.DIRECT2);

    public final ListPath<com.NBE_4_5_2.Team5.domain.post.comment.entity.Comment, com.NBE_4_5_2.Team5.domain.post.comment.entity.QComment> wroteComments = this.<com.NBE_4_5_2.Team5.domain.post.comment.entity.Comment, com.NBE_4_5_2.Team5.domain.post.comment.entity.QComment>createList("wroteComments", com.NBE_4_5_2.Team5.domain.post.comment.entity.Comment.class, com.NBE_4_5_2.Team5.domain.post.comment.entity.QComment.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

