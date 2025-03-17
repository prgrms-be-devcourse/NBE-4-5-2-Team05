"use client";

import React, { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import client from "@/lib/client";
import type { components } from "@/lib/backend/apiV1/schema";

type ProductPostResponse = components["schemas"]["ProductPostResponse"];

export default function PostModifyPage() {
  const { postId } = useParams<{ postId: string }>();
  const router = useRouter();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>("");

  // 기존 게시글 데이터를 불러와 폼에 채워넣기
  useEffect(() => {
    async function fetchPost() {
      if (!postId) return;
      try {
        const response = await client.GET("/api/posts/{id}", {
          withCredentials: true,
          params: { path: { id: postId } },
        });
        if (response.error) {
          setError("게시글 정보를 불러올 수 없습니다.");
        } else {
          const postData = response.data.data;
          setTitle(postData.title ?? "");
          setContent(postData.content ?? "");
        }
      } catch (err) {
        setError("게시글 정보를 불러오는 중 오류가 발생했습니다.");
      } finally {
        setLoading(false);
      }
    }
    fetchPost();
  }, [postId]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await client.PUT("/api/posts/{id}", {
        withCredentials: true,
        params: { path: { id: postId } },
        body: { title, content },
      });
      if (response.error) {
        alert("수정에 실패했습니다: " + response.error.message);
        return;
      }
      alert("수정이 완료되었습니다.");
      router.push(`/posts/${postId}`);
    } catch (err) {
      alert("수정 중 오류가 발생했습니다.");
    }
  };

  if (loading) return <div className="p-4">로딩 중...</div>;
  if (error) return <div className="p-4 text-red-500">{error}</div>;

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">판매글 수정</h1>
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <div>
          <label className="block mb-1 font-semibold">제목</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="border p-2 w-full"
          />
        </div>
        <div>
          <label className="block mb-1 font-semibold">본문</label>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="border p-2 w-full h-40"
          />
        </div>
        <button type="submit" className="bg-green-500 text-white p-2 rounded">
          수정 완료
        </button>
      </form>
    </div>
  );
}
