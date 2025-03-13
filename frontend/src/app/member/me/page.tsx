import client from "@/lib/backend/client";
import ClientPage from "./ClientPage";
import { cookies } from "next/headers";

export default async function LoginPage() {
  const response= await client.GET("/api/users/me",{
    headers:{
      cookie: (await cookies()).toString(),
    }
  });

  if(response.error){
    return <div>{response.error.message}</div>
  }

  const rsData=response.data;
  const userDto=rsData.data;

  return <ClientPage me={userDto}/>;
}