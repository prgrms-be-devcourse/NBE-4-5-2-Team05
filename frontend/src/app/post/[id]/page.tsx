import createClient from "openapi-fetch";
import ClientPage from "./ClientPage";
import { paths } from "@/lib/backend/apiV1/schema";
import client from "@/lib/backend/client";
import { cookies } from "next/headers";


export default async function Page(
    {params,}: {
        params:
        {
            id:string;
        }
    }
) { 
    const id= await params.id;
    console.log("아이디: ",id);
    const token = (await cookies()).get("accessToken")?.value;
    console.log("token: ",token);
    const cookieHeader=(await cookies()).toString();

    const response=await client.GET("/api/posts/{id}",{
        headers: {
            cookie: cookieHeader,
        },
        params:{
            path: {
                id,
            },
        },
        credentials:"include",
    });

    console.log("API Response:", response);

    const rsData=response.data!!;
    const post=rsData.data;
    return <ClientPage post={post} />
    
}