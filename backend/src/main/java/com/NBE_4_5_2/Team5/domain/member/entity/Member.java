package com.NBE_4_5_2.Team5.domain.member.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
	@Id
	private final String id = "user-" + UUID.randomUUID();
	private int cash = 0;

	/**
	 * {@code cash}에 {@code totalAmount} 만큼 추가합니다.
	 * @param totalAmount 충전할 금액
	 */
	public void chargeCash(Integer totalAmount) {
		this.cash += totalAmount;
	}
}
