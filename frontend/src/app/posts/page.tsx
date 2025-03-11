// app/posts/page.tsx
"use client";

import React, { useEffect, useState } from "react";
import axios from "axios";
import Link from "next/link";
import Notice from "@/components/posts/Notice";
import FilterSidebar from "@/components/posts/FilterSiderbar";
import PostList, { Post } from "@/components/posts/PostList";
import Pagination from "@/components/posts/Pagination";

interface PostsResponseData {
  items: Post[];
  totalPages: number;
  totalItems: number;
  curPageNo: number;
  pageSize: number;
}

interface RsData<T> {
  code: string;
  message: string;
  data: T;
}

export default function PostsPage() {
  const [notice, setNotice] = useState<{
    title: string;
    content: string;
  } | null>(null);
  const [posts, setPosts] = useState<Post[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  // 필터 상태 (검색 키워드, 금액, 카테고리, 정렬)
  const [filters, setFilters] = useState({
    keyword: "",
    minPrice: null as number | null,
    maxPrice: null as number | null,
    category: "",
    sort: "desc",
  });

  /* // 공지사항 로드 (미구현 시 주석 처리)
  useEffect(() => {
    (async () => {
      try {
        const res = await axios.get<RsData<{ title: string; content: string }>>(
          "/api/notices/latest"
        );
        setNotice(res.data.data);
      } catch (err) {
        console.error("공지사항 로드 실패", err);
      }
    })();
  }, []); */

  // 게시글 로드 함수
  const fetchPosts = async () => {
    try {
      const res = await axios.get<RsData<PostsResponseData>>("/api/posts", {
        params: {
          page: currentPage,
          pageSize: 10,
          keyword: filters.keyword || undefined,
          sort: filters.sort,
          minPrice: filters.minPrice || undefined,
          maxPrice: filters.maxPrice || undefined,
          category: filters.category || undefined,
        },
      });
      const { items, totalPages } = res.data.data;
      setPosts(items);
      setTotalPages(totalPages);
    } catch (err) {
      console.error("게시글 로드 실패", err);
    }
  };

  // currentPage 또는 필터가 바뀔 때마다 게시글 로드
  useEffect(() => {
    fetchPosts();
  }, [currentPage, filters]);

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  return (
    <>
      <div className="flex space-x-4">
        {/* 왼쪽: 메인 영역 (검색 결과) */}
        <div className="flex-grow">
          {notice && <Notice title={notice.title} content={notice.content} />}
          <h1 className="text-2xl font-bold mb-4">판매물품 리스트</h1>
          <PostList posts={posts} />
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={handlePageChange}
          />
        </div>

        {/* 오른쪽: 상세 검색(필터) */}
        <FilterSidebar
          onFilterChange={(newFilters) => {
            setFilters(newFilters);
            setCurrentPage(1); // 필터 변경 시 첫 페이지로 이동
          }}
        />
      </div>

      {/* 오른쪽 하단에 "작성하기" 버튼 추가 */}
      <div className="fixed bottom-4 right-4">
        <Link href="/posts/new">
          <button className="px-4 py-2 bg-blue-600 text-white rounded shadow-lg">
            작성하기
          </button>
        </Link>
      </div>
    </>
  );
}
