package com.NBE_4_5_2.Team5.global.init;

import com.NBE_4_5_2.Team5.domain.category.entity.Category;
import com.NBE_4_5_2.Team5.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final CategoryRepository categoryRepository;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    @Order(1)
    public ApplicationRunner applicationRunner1() {
        return args -> {
            self.categoryInit();
        };
    }

    @Transactional
    public void categoryInit() {
        if (categoryRepository.count() > 0) {
            return; // 이미 카테고리가 존재하면 초기화하지 않음
        }

        List<Category> categories = List.of(
                new Category(null, "전자제품"),
                new Category(null, "가구"),
                new Category(null, "의류"),
                new Category(null, "스포츠 용품"),
                new Category(null, "도서"),
                new Category(null, "생활용품"),
                new Category(null, "자동차 용품"),
                new Category(null, "식품"),
                new Category(null, "악기"),
                new Category(null, "반려동물 용품"),
                new Category(null, "뷰티/미용"),
                new Category(null, "티켓/쿠폰"),
                new Category(null, "수집/예술"),
                new Category(null, "게임"),
                new Category(null, "기타")
        );        categoryRepository.saveAll(categories);
    }
}
