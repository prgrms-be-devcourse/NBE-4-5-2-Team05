package com.NBE_4_5_2.Team5.domain.post.post.dto.response;

import com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost;
import com.NBE_4_5_2.Team5.domain.post.post.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ProductPostResponse {
	private String id;
	private String writerId;
	private String writerName;
	private String productName;
	private Integer productPrice;
	private String title;
	private String content;
	private String imageUrls;
	private Float latitude;
	private Float longitude;
	private List<String> categories;
	private LocalDateTime createdAt;    // 생성일
	private LocalDateTime modifiedAt;   // 수정일

	private Integer viewCount; // 조회수
	private Integer likedCount;

	private ProductStatus status;

	public static ProductPostResponse fromEntity(ProductPost post) {
		return new ProductPostResponse(
			post.getId(),
			post.getWriter().getId(),
			post.getWriter().getNickname(),
			post.getProductName(),
			post.getProductPrice(),
			post.getTitle(),
			post.getContent(),
			post.getImage_urls(),
			post.getLatitude(),
			post.getLongitude(),
			post.getProductCategories().stream()
				.map(pc -> pc.getCategory().getName())
				.collect(Collectors.toList()),
			post.getCreatedAt(),
			post.getModifiedAt(),
			post.getViewCount(),
			0,
				post.getStatus()

		);
	}
	// 찜 개수를 외부에서 전달받는 메서드
	public static ProductPostResponse fromEntityWithLikeCount(ProductPost post, int likedCount) {
		return new ProductPostResponse(
				post.getId(),
				post.getWriter().getId(),
				post.getWriter().getNickname(),
				post.getProductName(),
				post.getProductPrice(),
				post.getTitle(),
				post.getContent(),
				post.getImage_urls(),
				post.getLatitude(),
				post.getLongitude(),
				post.getProductCategories().stream()
						.map(pc -> pc.getCategory().getName())
						.collect(Collectors.toList()),
				post.getCreatedAt(),
				post.getModifiedAt(),
				post.getViewCount(),
				likedCount,
				post.getStatus()
		);
	}

}
