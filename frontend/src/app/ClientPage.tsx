"use client";

import React, { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { useRouter } from "next/navigation";
import Link from "next/link";
import RecentlyViewedSection from "@/components/posts/RecentlyViewedSection";
import RecentlyUploadedSection from "@/components/posts/RecentlyUploadedSection";
import client from "@/lib/client";
import { LoginMemberContext } from "./stores/auth/loginMemberStore";

export default function ClientPage() {
  const [searchKeyword, setSearchKeyword] = useState("");
  const [categories, setCategories] = useState<{ id: string; name: string }[]>(
    []
  );
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
  const router = useRouter();
  const { isLogin } = React.useContext(LoginMemberContext);

  // 카테고리 목록 불러오기
  const fetchCategories = async () => {
    const res = await client.GET("/api/categories", {
      credentials: "include",
    });
    if (res.error) {
      console.error("카테고리 불러오기 실패", res.error);
      return;
    }
    setCategories(
      res.data.data.map((cat: { id?: number; name?: string }) => ({
        id: cat.id ? String(cat.id) : "",
        name: cat.name || "",
      }))
    );
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  // 검색 버튼 클릭 시 실행되는 함수
  const handleSearch = () => {
    let query = "/posts";
    const params = new URLSearchParams();

    // 검색어가 있을 때만 추가
    if (searchKeyword.trim() !== "") {
      params.set("keyword", searchKeyword);
    }

    // 선택한 카테고리가 있으면 추가 (여러 개의 경우 콤마로 구분)
    if (selectedCategories.length > 0) {
      params.set("categories", selectedCategories.join(","));
    }

    if (params.toString()) {
      query += "?" + params.toString();
    }

    router.push(query);
  };

  // 체크박스 변경 핸들러
  const handleCategoryChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    if (e.target.checked) {
      setSelectedCategories((prev) => [...prev, value]);
    } else {
      setSelectedCategories((prev) => prev.filter((cat) => cat !== value));
    }
  };

  return (
    <div className="flex flex-col gap-4 p-4 w-full">
      {/* 상단 섹션 */}
      <section className="flex items-start gap-4 border p-4">
        <div className="flex flex-col">
          <span>📍 현재 위치</span>
          <Button variant="outline">위치 수정</Button>
        </div>
        <div className="flex-grow">
          <div className="flex gap-2">
            <input
              type="text"
              placeholder="상품명 검색"
              value={searchKeyword}
              onChange={(e) => setSearchKeyword(e.target.value)}
              className="w-full p-2 border rounded-md"
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  e.preventDefault();
                  handleSearch();
                }
              }}
            />
            <Button onClick={handleSearch}>검색</Button>
          </div>
          <div className="mt-4">
            <h3 className="font-bold mb-2">📌 카테고리 필터</h3>
            <div className="flex flex-wrap gap-2">
              {categories.map((cat) => (
                <label
                  key={cat.id}
                  className="flex items-center space-x-2 border p-2 rounded-md cursor-pointer"
                >
                  <input
                    type="checkbox"
                    value={cat.id}
                    onChange={handleCategoryChange}
                  />
                  <span>{cat.name}</span>
                </label>
              ))}
            </div>
          </div>
        </div>
        <div className="flex gap-2">
          <Button variant="default">
            <Link href="/posts">판매하기</Link>
          </Button>
          <Button variant="default">
            <Link href="/user/me/sell/manage">내 상점</Link>
          </Button>
          <Button variant="outline">💬 채팅</Button>
        </div>
      </section>

      {/* 공지사항 섹션 */}
      <section className="border p-4">
        <Button className="flex justify-start text-lg font-bold">
          📢 공지사항
        </Button>
      </section>

      {/* 최근 본 상품 섹션 */}
      <section className="border p-4">
        <RecentlyViewedSection isLogin={isLogin} />
      </section>

      {/* 최근 올라온 상품 섹션 */}
      <section className="border p-4">
        <RecentlyUploadedSection />
      </section>
    </div>
  );
}
