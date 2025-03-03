package com.NBE_4_5_2.Team5.global.init;

import com.NBE_4_5_2.Team5.domain.product.entity.Product;
import com.NBE_4_5_2.Team5.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final ProductRepository productRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> productInit();
    }

    @Transactional
    public void productInit() {
        if (productRepository.count() > 0) {
            return; // 기존 데이터가 있으면 초기화하지 않음
        }

        List<Product> products = List.of(
                // ✅ 구매 내역 데이터
                Product.builder()
                        .id("ppost-11111")
                        .productName("Laptop")
                        .productPrice(1200000)
                        .title("Gaming Laptop for sale")
                        .content("Brand new gaming laptop with RTX 3060.")
                        .likedCount(5)
                        .status("purchased") // 구매 완료
                        .sellerId("user-001") // 판매자 ID 추가
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build(),
                Product.builder()
                        .id("ppost-22222")
                        .productName("Smartphone")
                        .productPrice(800000)
                        .title("Used iPhone 12 Pro")
                        .content("Lightly used iPhone 12 Pro, 256GB storage.")
                        .likedCount(10)
                        .status("purchased")
                        .sellerId("user-002")
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build(),

                // ✅ 판매 내역 데이터 (판매 완료)
                Product.builder()
                        .id("ppost-33333")
                        .productName("Monitor")
                        .productPrice(300000)
                        .title("27-inch 4K Monitor")
                        .content("Perfect condition 27-inch 4K monitor.")
                        .likedCount(3)
                        .status("sold") // 판매 완료
                        .sellerId("user-001") // 판매자 ID 추가
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build(),

                // ✅ 판매 진행 중 데이터
                Product.builder()
                        .id("ppost-44444")
                        .productName("Mechanical Keyboard")
                        .productPrice(150000)
                        .title("Cherry MX Mechanical Keyboard")
                        .content("Barely used Cherry MX keyboard, great for gaming.")
                        .likedCount(7)
                        .status("selling") // 판매 진행 중
                        .sellerId("user-002")
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build(),

                // ✅ 예약된 상품 데이터
                Product.builder()
                        .id("ppost-55555")
                        .productName("Gaming Chair")
                        .productPrice(250000)
                        .title("Ergonomic Gaming Chair")
                        .content("High-quality ergonomic gaming chair, almost new.")
                        .likedCount(9)
                        .status("reserved") // 예약됨
                        .sellerId("user-003")
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build()
        );

        productRepository.saveAll(products);
        System.out.println("✅ 초기 상품 데이터 (구매 내역 & 판매 내역) 삽입 완료!");
    }
}
