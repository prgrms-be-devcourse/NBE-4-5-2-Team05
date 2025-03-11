"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import axios from "axios";

// AWS S3에 파일을 업로드하는 API 엔드포인트는 "/api/uploadFile" 입니다.
// 이 엔드포인트는 파일을 MultipartFile로 받고, 업로드된 파일 URL을 String으로 반환합니다.
const uploadFile = async (file: File): Promise<string> => {
  const formData = new FormData();
  formData.append("file", file);
  const response = await axios.post("/api/uploadFile", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  // response.data는 업로드된 파일의 URL을 포함합니다.
  return response.data;
};

export default function PostCreatePage() {
  const router = useRouter();

  // 입력값 state
  const [productName, setProductName] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [category, setCategory] = useState("");
  const [price, setPrice] = useState<number | "">("");
  const [location, setLocation] = useState("");
  const [latitude, setLatitude] = useState<number | "">("");
  const [longitude, setLongitude] = useState<number | "">("");
  // 이미지 파일 선택 state
  const [selectedFiles, setSelectedFiles] = useState<FileList | null>(null);

  // 백엔드는 이미지 URL 리스트(imageUrlList)를 받도록 설계되어 있음
  // 여기서는 S3 업로드 후 받은 URL들을 이 배열에 저장합니다.
  const [imageUrlList, setImageUrlList] = useState<string[]>([]);

  // 간단한 카테고리 매핑 예시 (문자열을 카테고리 ID 배열로 변환)
  const getCategoryIds = (category: string): number[] => {
    const mapping: Record<string, number> = {
      전자제품: 1,
      가구: 2,
      의류: 3,
      // 필요한 카테고리 추가
    };
    return category ? [mapping[category]] : [];
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      let uploadedUrls: string[] = [];
      if (selectedFiles && selectedFiles.length > 0) {
        // 선택된 파일들을 S3에 업로드하고 URL 배열 생성
        uploadedUrls = await Promise.all(
          Array.from(selectedFiles).map((file) => uploadFile(file))
        );
      }
      setImageUrlList(uploadedUrls);

      // 게시글 생성 API로 보낼 JSON 데이터 구성
      const data = {
        productName,
        productPrice: price === "" ? 0 : Number(price),
        title,
        content,
        categoryIds: getCategoryIds(category),
        imageUrlList: uploadedUrls,
        latitude: latitude === "" ? 0 : Number(latitude),
        longitude: longitude === "" ? 0 : Number(longitude),
      };

      await axios.post("/api/posts", data, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      // 게시글 생성 성공 시 판매목록 페이지로 이동
      router.push("/posts");
    } catch (err) {
      console.error("게시글 작성 실패", err);
      alert("게시글 작성 중 오류가 발생했습니다.");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="flex space-x-4 p-4">
      {/* 왼쪽 영역: 물품 이름, 게시글 제목, 본문 */}
      <div className="w-2/3">
        <h1 className="text-2xl font-bold mb-4">판매 게시글 작성</h1>
        <div className="mb-4">
          <label className="block mb-1 font-semibold">물품 이름</label>
          <input
            type="text"
            className="border w-full p-2"
            value={productName}
            onChange={(e) => setProductName(e.target.value)}
            placeholder="예) 아이폰 14 프로"
          />
        </div>
        <div className="mb-4">
          <label className="block mb-1 font-semibold">게시글 제목</label>
          <input
            type="text"
            className="border w-full p-2"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="예) 아이폰 14 프로 팝니다"
          />
        </div>
        <div className="mb-4">
          <label className="block mb-1 font-semibold">본문</label>
          <textarea
            className="border w-full p-2 h-48"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            placeholder="상품 설명, 상태 등을 자세히 적어주세요."
          />
        </div>
      </div>

      {/* 오른쪽 영역: 카테고리, 가격, 거래 위치, 좌표, 사진 추가, 게시하기 버튼 */}
      <div className="w-1/3">
        <div className="mb-4">
          <label className="block mb-1 font-semibold">카테고리</label>
          <select
            className="border w-full p-2"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          >
            <option value="">카테고리 선택</option>
            <option value="전자제품">전자제품</option>
            <option value="가구">가구</option>
            <option value="의류">의류</option>
            {/* 필요한 카테고리 추가 */}
          </select>
        </div>

        <div className="mb-4">
          <label className="block mb-1 font-semibold">가격</label>
          <input
            type="number"
            className="border w-full p-2"
            value={price}
            onChange={(e) =>
              setPrice(e.target.value === "" ? "" : Number(e.target.value))
            }
          />
        </div>

        <div className="mb-4">
          <label className="block mb-1 font-semibold">거래 위치</label>
          <input
            type="text"
            className="border w-full p-2"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          />
        </div>

        <div className="mb-4">
          <label className="block mb-1 font-semibold">위도</label>
          <input
            type="number"
            className="border w-full p-2"
            value={latitude}
            onChange={(e) =>
              setLatitude(e.target.value === "" ? "" : Number(e.target.value))
            }
          />
        </div>

        <div className="mb-4">
          <label className="block mb-1 font-semibold">경도</label>
          <input
            type="number"
            className="border w-full p-2"
            value={longitude}
            onChange={(e) =>
              setLongitude(e.target.value === "" ? "" : Number(e.target.value))
            }
          />
        </div>

        <div className="mb-4">
          <label className="block mb-1 font-semibold">사진 추가</label>
          <input
            type="file"
            multiple
            onChange={(e) => setSelectedFiles(e.target.files)}
          />
        </div>

        <button
          type="submit"
          className="mt-4 w-full bg-blue-500 text-white p-2 rounded"
        >
          게시하기
        </button>
      </div>
    </form>
  );
}
