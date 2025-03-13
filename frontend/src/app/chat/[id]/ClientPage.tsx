"use client";

import { components } from "@/lib/backend/apiV1/schema";

export default function ClientPage({
  messages,
  title,
}:{
  messages:components["schemas"]["MessageDto"][];
  title:string;
}) {
  return (
    <div>
        <h1 className="text-xl font-bold mb-4">{title}</h1>
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