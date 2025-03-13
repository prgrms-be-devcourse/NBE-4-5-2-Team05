"use client";

import { components } from "@/lib/backend/apiV1/schema";
import client from "@/lib/backend/client";

export default function ClientPage({
  messages,
  title,
  roomId,
  cookie,
}:{
  messages:components["schemas"]["MessageDto"][];
  title:string;
  roomId: string;
  cookie: string;
}) {
    const deleteRoom = async () => {
        const deleteResponse = await fetch(`http://localhost:8080/api/chat/message/${roomId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                cookie: cookie,
            },
            credentials: 'include'
        });
        
        if (!deleteResponse.ok) {
            const errorData=await deleteResponse.json();
            console.error("삭제 실패:", errorData);
            alert("채팅방 삭제에 실패했습니다.");
        } else {
            alert("채팅방 삭제가 완료되었습니다.");
            window.location.href = "/chat"; // 메인 페이지로 리다이렉트
        }
    };
    
  return (
    <div>
        <h1 className="text-xl font-bold mb-4">{title}</h1>
        <button 
          onClick={deleteRoom} // 삭제 함수 호출
          className="mb-4 px-4 py-2 bg-red-600 text-white rounded"
        >
          채팅방 나가기
        </button>
        <ul className="space-y-4">
            {messages.slice().reverse().map((message) => (
                <li key={message.messageId} className="border p-2 bg-white rounded shadow-sm">
                    <div>
                        <strong>보낸 이:</strong> {message.sender}
                    </div>
                    <div>
                        <strong>메시지 내용:</strong> {message.message}
                    </div>
                    {message.image && (
                        <div>
                            <strong>이미지:</strong> <img src={message.image} alt="메시지 첨부 이미지" className="max-w-full h-auto" />
                        </div>
                    )}
                    <div>
                        <strong>보낸 시간:</strong> {message.timestamp} {/* 포맷 할 경우 */}
                    </div>
                </li>
            ))}
        </ul>
    </div>
);
}