import { cookies } from "next/headers";
import ClientPage from "./ClientPage";
import client from "@/lib/backend/client";

export default async function Page(){
  const response= await client.GET("/api/chat/rooms",{
    headers:{
      cookie:(await cookies()).toString(),
    },
    credentials:"include",
  });

  if(response.error){
    return <div>{response.error.message}</div>
  }
  console.log("response",response);
  
  const rsData=response.data!!;
  const chatRoom=rsData.data;  

  return (<ClientPage chatRoom={chatRoom}/>);
}
