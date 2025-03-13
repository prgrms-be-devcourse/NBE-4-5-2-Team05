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

// 공지사항 데이터 인터페이스
interface NoticeData {
  title: string;
  content: string;
}

// 카테고리 인터페이스 (백엔드 Category 엔티티와 일치)
interface Category {
  id: number;
  name: string;
}

export default function PostsPage() {
  // 최신 공지사항 5개 상태
  const [noticeList, setNoticeList] = useState<NoticeData[]>([]);
  const [posts, setPosts] = useState<Post[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [categories, setCategories] = useState<Category[]>([]);

  // 필터 상태
  const [filters, setFilters] = useState({
    keyword: "",
    minPrice: null as number | null,
    maxPrice: null as number | null,
    category: "",
    sort: "desc",
  });

  // 공지사항 로드 (관리자 공지사항 조회 엔드포인트)
  useEffect(() => {
    (async () => {
      try {
        const res = await axios.get<RsData<NoticeData[]>>(
          "/api/admin/notices/latest",
          {
            withCredentials: true,
          }
        );
        setNoticeList(res.data.data);
      } catch (err) {
        console.error("공지사항 로드 실패", err);
      }
    })();
  }, []);

  // 카테고리 목록 로드 (백엔드에서 전체 카테고리 조회)
  useEffect(() => {
    (async () => {
      try {
        const res = await axios.get<RsData<Category[]>>("/api/categories", {
          withCredentials: true,
        });
        setCategories(res.data.data);
      } catch (err) {
        console.error("카테고리 로드 실패", err);
      }
    })();
  }, []);

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
        withCredentials: true,
      });
      const { items, totalPages } = res.data.data;
      setPosts(items);
      setTotalPages(totalPages);
    } catch (err) {
      console.error("게시글 로드 실패", err);
    }
  };

  // currentPage 또는 필터 변경 시 게시글 로드
  useEffect(() => {
    fetchPosts();
  }, [currentPage, filters]);

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  return (
    <>
      <div className="flex space-x-4">
        {/* 왼쪽: 메인 영역 (공지사항 및 게시글 목록) */}
        <div className="flex-grow">
          {noticeList.length > 0 && (
            <div className="mb-4 space-y-4">
              {noticeList.map((notice, index) => (
                <Notice
                  key={index}
                  title={notice.title}
                  content={notice.content}
                />
              ))}
            </div>
          )}
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
            setCurrentPage(1);
          }}
          categories={categories}
        />
      </div>

      {/* 오른쪽 하단 "작성하기" 버튼 */}
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
