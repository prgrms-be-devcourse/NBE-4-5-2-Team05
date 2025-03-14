"use client";

import { components } from "@/lib/backend/apiV1/schema";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function ClientPage({
  chatRoom,
  searchChatRoomDto,
  receiver,
}:{
  chatRoom:components["schemas"]["ChatRoomDto"][];
  searchChatRoomDto:components["schemas"]["ChatRoomDto"][];
  receiver:string;
}) {

  const router = useRouter();
  const [searchTerm, setSearchTerm] = useState(receiver); // 검색어 상태 관리

  // 검색 기능: 입력한 검색어로 URL을 변경
  const handleSearch = (event: React.FormEvent) => {
    event.preventDefault(); // 기본 폼 제출 방지
    router.push(`/chat?page=1&receiver=${searchTerm}`); // 검색어를 쿼리파라미터로 전달
  };

  return (
    <div>
    <h1 className="text-xl font-bold mb-4">채팅방 목록</h1>
    
    <form onSubmit={handleSearch} className="mb-4">
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)} // 검색어 상태 업데이트
          placeholder="검색어를 입력하세요"
          className="border-2 border-gray-300 rounded-lg p-2"
        />
        <button type="submit" className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-lg">
          검색
        </button>
    </form>

    {receiver && (
        <div>
          <h2 className="text-lg font-semibold mb-2">검색 결과: {receiver}</h2>
          {searchChatRoomDto.length==1 ? (
            // 반복문을 한 번만 사용하여 첫 번째 항목만 출력
            <div className="border-2 border-gray-300 rounded-lg p-4 bg-white shadow-md">
              {searchChatRoomDto.map((room) => (
                <Link key={room.roomId} href={`/chat/${room.roomId}`} className="block">
                  <div className="font-semibold">채팅방 정보</div>
                  <div>Post ID: {room.postId}</div>
                  <div>Room ID: {room.roomId}</div>
                  <div>Name: {room.name}</div>
                  <div>User Count: {room.userCount}</div>
                </Link>
              ))}
            </div>
          ) : (
            <div>검색 결과가 없습니다.</div>
          )}
        </div>
      )}

    {!receiver && (
      <ul className="space-y-4"> {/* 각 항목 사이 공백 추가 */}
          {chatRoom.map((room) => (
              <li
                  key={room.roomId}
                  className="border-2 border-gray-300 rounded-lg p-4 bg-white shadow-md"
              >
                  <Link href={`/chat/${room.roomId}`} className="block">
                      <div className="font-semibold">채팅방 정보</div>
                      <div>Post ID: {room.postId}</div>
                      <div>Room ID: {room.roomId}</div>
                      <div>Name: {room.name}</div>
                      <div>User Count: {room.userCount}</div>
                  </Link>
              </li>
          ))}
      </ul>
    )}

</div>
  );
}