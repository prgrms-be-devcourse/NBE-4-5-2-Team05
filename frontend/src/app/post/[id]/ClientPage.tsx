"use client"

import { components } from "@/lib/backend/apiV1/schema";

export default function ClientPage({post}:
     {post:components["schemas"]["ProductPostResponse"];}
    )
    {
    return <div>
            <div>id:{post.id} </div>
            <div>작성자 id:{post.writerId} </div>
            <div>작성자:{post.writerName} </div>
            <div>상품 이름:{post.productName} </div>
            <div>상품 가격:{post.productPrice} </div>
            <div>제목:{post.title} </div>
            <div>내용:{post.content} </div> 
            <div>이미지:{post.imageUrls} </div>
            <div><strong>위치</strong>
                <div>   위도:{post.latitude}</div>
                <div>   경도:{post.longitude}</div>
            </div>
            <div>카테고리:{post.categories} </div>


     </div>;
}
