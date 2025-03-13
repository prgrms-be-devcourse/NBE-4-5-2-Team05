"use client";

import { components } from "@/lib/backend/apiV1/schema";
import Link from "next/link";

export default function ClientPage({chatRoom}:
{
  chatRoom:components["schemas"]["ChatRoomDto"][];
}) {
  return (
    <div>
    <h1 className="text-xl font-bold mb-4">채팅방 목록</h1>
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
</div>
  );
}