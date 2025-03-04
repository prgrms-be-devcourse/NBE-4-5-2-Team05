package com.NBE_4_5_2.Team5.domain.post.post.controller;

import com.NBE_4_5_2.Team5.domain.post.post.dto.request.ProductPostModifyReqBody;
import com.NBE_4_5_2.Team5.domain.post.post.dto.response.ProductPostResponse;
import com.NBE_4_5_2.Team5.domain.post.post.dto.request.ProductPostWriteReqBody;
import com.NBE_4_5_2.Team5.domain.post.post.entity.ProductPost;
import com.NBE_4_5_2.Team5.domain.post.post.service.ProductPostService;
import com.NBE_4_5_2.Team5.global.dto.Empty;
import com.NBE_4_5_2.Team5.global.dto.PageDto;
import com.NBE_4_5_2.Team5.global.dto.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Reader;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class ProductPostController {
    private final ProductPostService productPostService;

    @PostMapping
    public RsData<ProductPostResponse> createPost(@Valid @RequestBody ProductPostWriteReqBody body) {

        // 작성자 체크 및 write에 넘겨주기 추가 필요
        ProductPost post = productPostService.write(body);

        return new RsData<>(
                "200",
                "글 작성 성공",
                ProductPostResponse.fromEntity(post)
        );
    }

    @GetMapping
    @Transactional(readOnly = true)
    public RsData<PageDto<ProductPostResponse>> getPosts(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int pageSize,
                                                         @RequestParam(defaultValue = "") String keyword,
                                                         @RequestParam(defaultValue = "desc") String sort) {
        PageDto<ProductPostResponse> postPage = productPostService.getPosts(page, pageSize, keyword, sort);

        return new RsData<>(
                "200",
                "글 목록 조회가 완료되었습니다.",
                postPage
        );
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public RsData<ProductPostResponse> getPost(@PathVariable String id, Reader reader) {
        ProductPost post = productPostService.getPost(id);

        return new RsData<>(
                "200",
                "게시물 조회가 완료되었습니다.",
                ProductPostResponse.fromEntity(post)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<ProductPostResponse> modify(
            @Valid @RequestBody ProductPostModifyReqBody body,
            @PathVariable String id) {
        ProductPost post = productPostService.getPost(id);

        // 수정 권한 체크 canModify
//        if (!post.canModify()) {
//
//        }

        productPostService.modify(post,body);

        return new RsData<>(
                "200",
                "글 수정 완료.",
                ProductPostResponse.fromEntity(post)
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public RsData<Empty> delete(@PathVariable String id) {
        ProductPost post = productPostService.getPost(id);

        // 삭제 권한 체크 canDelete
//        if (!post.canDelete()) {
//
//        }

        productPostService.delete(post);

        return new RsData<>(
                "200",
                "글 삭제 완료."
        );
    }

}
