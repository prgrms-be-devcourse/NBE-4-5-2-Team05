package com.NBE_4_5_2.Team5.domain.post.post.dto.request;

import com.NBE_4_5_2.Team5.domain.post.post.enums.ProductStatus;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

public record ProductPostModifyForm(
	@Parameter(name = "상품 이름")
	String productName,
	@Parameter(name = "상품 가격")
	Integer productPrice,
	@Parameter(name = "상품 게시글 제목")
	String title,
	@Parameter(name = "상품 게시글 내용")
	String content,
	@Parameter(name = "상품 카테고리")
	List<Long> categoryIds,
	@Parameter(name = "상품 이미지 url")
	List<String> imageUrlList,
	@Parameter(name = "위도")
	Float latitude,
	@Parameter(name = "경도")
	Float longitude,

	@Parameter(name = "판매 상태")
	ProductStatus status
) {
}
