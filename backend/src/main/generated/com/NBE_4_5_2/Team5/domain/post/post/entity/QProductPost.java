package com.NBE_4_5_2.Team5.domain.post.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPost is a Querydsl query type for ProductPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductPost extends EntityPathBase<ProductPost> {

    private static final long serialVersionUID = -1697070868L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPost productPost = new QProductPost("productPost");

    public final com.NBE_4_5_2.Team5.domain.base.entity.QBaseTime _super = new com.NBE_4_5_2.Team5.domain.base.entity.QBaseTime(this);

    public final com.NBE_4_5_2.Team5.domain.user.user.entity.QUser buyer;

    public final ListPath<com.NBE_4_5_2.Team5.domain.post.comment.entity.Comment, com.NBE_4_5_2.Team5.domain.post.comment.entity.QComment> commentList = this.<com.NBE_4_5_2.Team5.domain.post.comment.entity.Comment, com.NBE_4_5_2.Team5.domain.post.comment.entity.QComment>createList("commentList", com.NBE_4_5_2.Team5.domain.post.comment.entity.Comment.class, com.NBE_4_5_2.Team5.domain.post.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath id = createString("id");

    public final StringPath image_urls = createString("image_urls");

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<ProductCategory, QProductCategory> productCategories = this.<ProductCategory, QProductCategory>createList("productCategories", ProductCategory.class, QProductCategory.class, PathInits.DIRECT2);

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> productPrice = createNumber("productPrice", Integer.class);

    public final EnumPath<com.NBE_4_5_2.Team5.domain.post.post.enums.ProductStatus> status = createEnum("status", com.NBE_4_5_2.Team5.domain.post.post.enums.ProductStatus.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final com.NBE_4_5_2.Team5.domain.user.user.entity.QUser writer;

    public QProductPost(String variable) {
        this(ProductPost.class, forVariable(variable), INITS);
    }

    public QProductPost(Path<? extends ProductPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPost(PathMetadata metadata, PathInits inits) {
        this(ProductPost.class, metadata, inits);
    }

    public QProductPost(Class<? extends ProductPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.NBE_4_5_2.Team5.domain.user.user.entity.QUser(forProperty("buyer")) : null;
        this.writer = inits.isInitialized("writer") ? new com.NBE_4_5_2.Team5.domain.user.user.entity.QUser(forProperty("writer")) : null;
    }

}

