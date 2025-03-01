package com.NBE_4_5_2.Team5.domain.product.dto;

public enum ProductStatus {
	RESERVED("예약됨"), AVAILABLE("판매중"), PURCHASED("판매됨");

	private final String description;

	ProductStatus(String description) {
		this.description = description;
	}
}
