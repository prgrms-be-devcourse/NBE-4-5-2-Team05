"use client";

import { components } from "@/lib/backend/apiV1/schema";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"; 
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import { Button } from "@/components/ui/button";


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
  const [searchTerm, setSearchTerm] = useState(receiver); 
  const [isSearchVisible, setIsSearchVisible] = useState(false);

  const handleSearch = (event: React.FormEvent) => {
    event.preventDefault(); // 기본 폼 제출 방지
    router.push(`/chat?page=1&receiver=${searchTerm}`);
  };

  const handleKeyPress = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter') {
      handleSearch(event as React.FormEvent); // 엔터 시 검색 실행
    }
  };

  return (
    <div>
    <h1 className="text-xl font-bold mb-4">채팅방 목록</h1>
    

    <form onSubmit={handleSearch} className="mb-4 flex items-center">
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)} // 검색어 상태 업데이트
          placeholder="검색어를 입력하세요"
          onKeyPress={handleKeyPress} // 엔터 키 인식
          className="border-2 border-gray-300 rounded-lg p-2 flex-grow"
        />
        <Button 
          type="submit" 
          variant="outline" 
          className="ml-1 px-5 py-5 rounded-lg">
          <FontAwesomeIcon icon={faMagnifyingGlass} />  
          검색
        </Button>
    </form>

    {receiver && (
        <div>
          <h2 className="text-lg font-semibold mb-2">검색 결과: {receiver}</h2>
          {searchChatRoomDto.length > 0 ? (
            <div className="border-2 border-gray-300 rounded-lg p-4 bg-white shadow-md">
              {searchChatRoomDto.map((room) => (
                <Link key={room.roomId} href={`/chat/${room.roomId}`} className="block">
                  <div><strong>{room.name}</strong></div>
                  <div className="text-gray-600 text-sm">{room.lastMessage}</div> 
                  <div className="text-gray-500 text-xs text-right"> 
                    {room.lastTimestamp}
                  </div>
                </Link>
              ))}
            </div>
          ) : (
            <div>검색 결과가 없습니다.</div>
          )}
        </div>
      )}

      {!receiver && (
        <ul className="space-y-4">
          {chatRoom.map((room) => (
            <li key={room.roomId} className="border-2 border-gray-300 rounded-lg p-4 bg-white shadow-md">
              <Link href={`/chat/${room.roomId}`} className="block">
                <div>
                  <strong className="text-lg">{room.other}</strong> 
                </div>
                <div className="text-gray-600 text-sm">{room.lastMessage}</div> 
                <div className="text-gray-500 text-xs text-right"> 
                  {room.lastTimestamp}
                </div>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}