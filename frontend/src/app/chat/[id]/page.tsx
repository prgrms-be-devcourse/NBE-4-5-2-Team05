import client from "@/lib/backend/client";
import ClientPage from "./ClientPage";
import { cookies } from "next/headers";

export default async function Page({
    params,
}: {
    params: {
        id: string;
    }
}) {
    const roomId = await (await params).id; 
    
    const token = (await cookies()).get("accessToken")?.value;
    console.log("token: ",token);
    const cookie= (await cookies()).toString();

    const messageResponse = await client.GET("/api/chat/message", {
        headers: {
          cookie:cookie,
        },
        params: {
          query:{
            roomId,
          },
        },
        credentials:"include",
    });
    
    const messageData = messageResponse.data!!; // 변환할 데이터
    const title = messageData.message;
    const messages = messageData.data; // 메시지 데이터
  
    return <ClientPage messages={messages} title={title} roomId={roomId} cookie={cookie}/>; // 메시지 데이터 클라이언트 페이지로 전달

}