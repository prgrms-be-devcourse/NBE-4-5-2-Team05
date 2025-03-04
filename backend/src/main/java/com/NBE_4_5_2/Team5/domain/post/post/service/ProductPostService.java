package com.NBE_4_5_2.Team5.domain.post.post.service;

import com.NBE_4_5_2.Team5.domain.category.entity.Category;
import com.NBE_4_5_2.Team5.domain.category.repository.CategoryRepository;
import com.NBE_4_5_2.Team5.domain.post.post.dto.request.ProductPostWriteReqBody;
import com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost;
import com.NBE_4_5_2.Team5.domain.post.post.repository.ProductPostRepository;
import com.NBE_4_5_2.Team5.global.dto.PageDto;
import com.NBE_4_5_2.Team5.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPostService {
    private final ProductPostRepository productPostRepository;
    //    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public ProductPost write(ProductPostWriteReqBody body) {
        String imageUrlStr = String.join(",", body.imageUrlList());

        // 글 작성
        ProductPost productPost = ProductPost.create(
                body.productName(),
                body.productPrice(),
                body.title(),
                body.content(),
//                writer,
                imageUrlStr,
                body.latitude(),
                body.longitude()
        );
        productPostRepository.save(productPost);

        // 상품글에 카테고리 체크 및 추가
        List<Long> reqCategoryIdList = body.categoryIds();
        List<Category> realCategoryList = categoryRepository.findAllById(reqCategoryIdList);
        if (realCategoryList.size() != reqCategoryIdList.size()) {
            throw new ServiceException("400", "존재하지 않는 카테고리가 포함되어있습니다.");
        }
        productPost.addCategories(realCategoryList);

        productPostRepository.save(productPost);

        return productPost;
    }


    public PageDto<ProductPost> getPosts(int page, int pageSize, String keyword, String sort) {
        Pageable pageable = PageRequest.of(page - 1, pageSize,
                Sort.by(sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "id"));
        String likeKeyword = "%" + keyword + "%";

        // keyword가 제목에 들어간 게시물 목록
        Page<ProductPost> mappedPosts = productPostRepository.findByTitleLike(likeKeyword, pageable);

        return new PageDto<>(mappedPosts);
    }
}
