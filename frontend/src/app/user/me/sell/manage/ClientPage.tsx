"use client";

import { Button } from "@/components/ui/button";
import { components } from "@/lib/backend/apiV1/schema";
import { useRouter, useSearchParams } from "next/navigation";
import { useState } from "react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import Link from "next/link";

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

  const currentSort = searchParams.get("sort") || "desc";

  const handleSortChange = (newSort: string) => {
    const params = new URLSearchParams(searchParams.toString());

    params.set("sort", newSort);
    router.push(`/user/me/sell/manage?${params.toString()}`);
  };

  const handleFilterChange = (newStatus?: string) => {
    setStatus(newStatus);
    const params = new URLSearchParams(searchParams.toString());
    if (newStatus) {
      params.set("status", newStatus);
    } else {
      params.delete("status");
    }

    router.push(`/user/me/sell/manage?${params.toString()}`);
  };

  return (
    <div className="space-y-1">
      <h2>내 상품 관리</h2>

      {/* 필터 */}
      <div className="flex flex-col mb-4">
        {/* 전체, 판매중, 예약중, 판매완료 */}
        <div className="flex space-x-2">
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
          {/* 최신순, 오래된순 */}
          <div className="flex ml-auto">
            <Select onValueChange={handleSortChange} defaultValue={currentSort}>
              <SelectTrigger className="w-[150px]">
                <SelectValue placeholder="정렬 기준" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="desc">최신순</SelectItem>
                <SelectItem value="asc">오래된순</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>
      </div>

      {/* 상품 목록 */}
      {/* 썸네일, 상품명, 가격, 찜, 판매상태, 최근 수정일, 수정 */}
      <div>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>사진</TableHead>
              <TableHead>상품명</TableHead>
              <TableHead>가격</TableHead>
              <TableHead>찜</TableHead>
              <TableHead>판매 상태</TableHead>
              <TableHead>최근 수정일</TableHead>
              <TableHead>수정</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {postInfo.length > 0 ? (
              postInfo.map((post) => (
                <TableRow key={post.id}>
                  <TableCell>
                    <Link href={`/post/${post.id}`}>
                      <img src={post.thumbNail} alt={post.title} />
                    </Link>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell>등록된 상품이 없습니다.</TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>

      {/* 페이징 번호 */}
    </div>
  );
}
