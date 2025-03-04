package com.NBE_4_5_2.Team5.global.init;

import com.NBE_4_5_2.Team5.domain.category.entity.Category;
import com.NBE_4_5_2.Team5.domain.category.repository.CategoryRepository;
import com.NBE_4_5_2.Team5.domain.post.post.entity.ProductCategory;
import com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost;
import com.NBE_4_5_2.Team5.domain.post.post.repository.ProductCategoryRepository;
import com.NBE_4_5_2.Team5.domain.post.post.repository.ProductPostRepository;
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
    private final ProductPostRepository postRepository;
    private final ProductCategoryRepository productCategoryRepository;

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

    @Bean
    @Order(2)
    public ApplicationRunner applicationRunner2() {
        return args -> {
            self.postInit();
        };
    }

    @Transactional
    public void postInit() {
        if (postRepository.count() > 0) {
            return;
        }

        List<Category> categories = categoryRepository.findAllById(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L));

        List<ProductPost> posts = List.of(
                ProductPost.create(
                        "아이폰 14 프로",
                        1350000,
                        "아이폰 14 프로 판매합니다.",
                        "3개월 사용, 상태 최상",
                        "https://example.com/image1.jpg,https://example.com/image2.jpg",
                        37.5665f, 126.9780f
                ),
                ProductPost.create(
                        "게이밍 노트북",
                        2000000,
                        "고사양 게이밍 노트북 팝니다.",
                        "RTX 3070 탑재, 박스 포함",
                        "https://example.com/laptop1.jpg,https://example.com/laptop2.jpg",
                        37.1234f, 127.5678f
                ),
                ProductPost.create(
                        "원목 책상",
                        100000,
                        "튼튼한 원목 책상 판매",
                        "생활 기스 조금 있음, 직접 가져가야 함.",
                        "https://example.com/desk1.jpg,https://example.com/desk2.jpg",
                        36.9876f, 127.3456f
                ),
                ProductPost.create(
                        "나이키 운동화",
                        70000,
                        "나이키 에어포스 1 판매",
                        "사이즈 270, 거의 새 것",
                        "https://example.com/shoes1.jpg,https://example.com/shoes2.jpg",
                        37.0000f, 127.0000f
                ),
                ProductPost.create(
                        "헬스 아령 세트",
                        50000,
                        "20kg 헬스 아령 세트 판매",
                        "거의 사용 안 함",
                        "https://example.com/dumbbell1.jpg,https://example.com/dumbbell2.jpg",
                        36.8888f, 127.1111f
                ),
                ProductPost.create(
                        "소설책 5권",
                        25000,
                        "추리 소설 5권 세트 판매",
                        "읽던 책, 상태 양호",
                        "https://example.com/book1.jpg,https://example.com/book2.jpg",
                        37.2222f, 126.5555f
                ),
                ProductPost.create(
                        "냄비 세트",
                        40000,
                        "스테인리스 냄비 3종 세트",
                        "사용감 있지만 상태 좋음",
                        "https://example.com/pot1.jpg,https://example.com/pot2.jpg",
                        37.7777f, 127.3333f
                ),
                ProductPost.create(
                        "중고 타이어 4개",
                        150000,
                        "자동차 타이어 4개 중고 판매",
                        "트레드 80% 이상 남음",
                        "https://example.com/tire1.jpg,https://example.com/tire2.jpg",
                        36.6666f, 127.2222f
                ),
                ProductPost.create(
                        "일렉 기타",
                        300000,
                        "펜더 일렉 기타 판매",
                        "사용감 있지만 소리 좋음",
                        "https://example.com/guitar1.jpg,https://example.com/guitar2.jpg",
                        37.4444f, 126.9999f
                ),
                ProductPost.create(
                        "고양이 사료 10kg",
                        35000,
                        "고양이 사료 대용량 판매",
                        "유통기한 넉넉함",
                        "https://example.com/catfood1.jpg,https://example.com/catfood2.jpg",
                        36.5555f, 127.7777f
                )
        );

        postRepository.saveAll(posts);



        // ✅ `ProductCategory` 생성하여 게시글과 카테고리 연결 (Builder 사용)
        List<ProductCategory> productCategories = List.of(
                ProductCategory.builder().productPost(posts.get(0)).category(categories.get(0)).build(), // 아이폰 → 전자제품
                ProductCategory.builder().productPost(posts.get(1)).category(categories.get(0)).build(), // 게이밍 노트북 → 전자제품
                ProductCategory.builder().productPost(posts.get(2)).category(categories.get(1)).build(), // 원목 책상 → 가구
                ProductCategory.builder().productPost(posts.get(3)).category(categories.get(2)).build(), // 운동화 → 의류
                ProductCategory.builder().productPost(posts.get(4)).category(categories.get(3)).build(), // 헬스 아령 → 스포츠 용품
                ProductCategory.builder().productPost(posts.get(5)).category(categories.get(4)).build(), // 소설책 → 도서
                ProductCategory.builder().productPost(posts.get(6)).category(categories.get(5)).build(), // 냄비 세트 → 생활용품
                ProductCategory.builder().productPost(posts.get(7)).category(categories.get(6)).build(), // 중고 타이어 → 자동차 용품
                ProductCategory.builder().productPost(posts.get(8)).category(categories.get(8)).build(), // 일렉 기타 → 악기
                ProductCategory.builder().productPost(posts.get(9)).category(categories.get(9)).build()  // 고양이 사료 → 반려동물 용품
        );

        productCategoryRepository.saveAll(productCategories);
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
        );

        categoryRepository.saveAll(categories);
    }
}
