package com.NBE_4_5_2.Team5.domain.post.post.entity;

import com.NBE_4_5_2.Team5.domain.post.post.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProductPost {

    @Id
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer productPrice;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image_urls; // 쉼표가 포함된 url 문자열

    @Column(nullable = false)
    private Integer likedCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.FOR_SALE;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public static ProductPost create(String productName,
                                     Integer productPrice,
                                     String title,
                                     String content,
                                     List<String> imageUrlList,
                                     Float latitude,
                                     Float longitude) {
        return ProductPost
                .builder()
                .id("ppost-" + UUID.randomUUID())
                .productName(productName)
                .productPrice(productPrice)
                .title(title)
                .content(content)
                .image_urls(String.join(",", imageUrlList))
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }


}
