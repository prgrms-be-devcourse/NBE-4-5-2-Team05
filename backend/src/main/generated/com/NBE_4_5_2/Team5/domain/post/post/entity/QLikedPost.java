package com.NBE_4_5_2.Team5.domain.post.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLikedPost is a Querydsl query type for LikedPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikedPost extends EntityPathBase<LikedPost> {

    private static final long serialVersionUID = -1559153686L;

    public static final QLikedPost likedPost = new QLikedPost("likedPost");

    public final com.NBE_4_5_2.Team5.domain.base.entity.QBaseLongIdEntity _super = new com.NBE_4_5_2.Team5.domain.base.entity.QBaseLongIdEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath productPostId = createString("productPostId");

    public final StringPath userId = createString("userId");

    public QLikedPost(String variable) {
        super(LikedPost.class, forVariable(variable));
    }

    public QLikedPost(Path<? extends LikedPost> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLikedPost(PathMetadata metadata) {
        super(LikedPost.class, metadata);
    }

}

