"use client";

import { Button } from "@/components/ui/button";
import { components } from "@/lib/backend/apiV1/schema";
import { useRouter, useSearchParams } from "next/navigation";
import { useState } from "react";

export default function ClientPage({
  postInfo,
  pageInfo,
}: {
  postInfo: components["schemas"]["PreviewPostResponse"][];
  pageInfo: {
    totalPages: number;
    totalItems: number;
    currentPage: number;
    pageSize: number;
  };
}) {
  const router = useRouter();
  const searchParams = useSearchParams();
  const [status, setStatus] = useState<string | undefined>(
    searchParams.get("status") || undefined
  );

  const handleFilterChange = (newStatus?: string) => {
    setStatus(newStatus);
    const params = new URLSearchParams();
    if (newStatus) {
      params.set("status", newStatus);
    } else {
      params.delete("status");
    }

    router.push(`/user/me/sell/manage?${params.toString()}`);
  };

  return (
    <div>
      <h2>내 상품 관리</h2>
      {/* 필터 */}
      {/* 전체, 판매중, 예약중, 판매완료 */}
      <div className="flex">
        <div>
          {[
            { label: "전체", value: undefined },
            { label: "판매중", value: "AVAILABLE" },
            { label: "예약중", value: "RESERVED" },
            { label: "판매완료", value: "PURCHASED" },
          ].map(({ label, value }) => (
            <Button
              key={value || ""}
              className={`px-3 py-1 border ${
                status === (value === "" ? undefined : value)
                  ? "bg-gray-800 text-white"
                  : "bg-white text-black"
              }`}
              onClick={() => handleFilterChange(value)}
            >
              {label}
            </Button>
          ))}
        </div>
      </div>

      <div></div>

      {/* 최신순, 오래된순 */}

      {/* 상품 목록 */}
      {/* 썸네일, 상품명, 가격, 찜, 판매상태, 최근 수정일, 수정 */}

      {/* 페이징 번호 */}
    </div>
  );
}
