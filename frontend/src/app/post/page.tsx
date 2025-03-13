import { components, paths } from "@/lib/backend/apiV1/schema";
import { SearchParamsContext } from "next/dist/shared/lib/hooks-client-context.shared-runtime";
import Link from "next/link";
import createClient from "openapi-fetch";
import ClientPage from "./ClientPage";
import client from "@/lib/backend/client";

type PageDto=components["schemas"]["PageDtoPreviewPostResponse"]
type PostDto=components["schemas"]["PreviewPostResponse"]

export default async function Page({searchParams}: 
  {
    searchParams:{
        page: string,
        pageSize: string,
        keyword: string
    };
  }){
    const { 
      page="1",
      pageSize="10",
      keyword=""
    } = await searchParams;
    
    const response = await client.GET("/api/posts",{
        params:{
          query:{
            keyword: keyword,
            page: Number(page),
            pageSize: Number(pageSize),
          },
        },
        credentials:"include",
      }
    )
    // const response = await fetch(`http://localhost:8080/api/posts?page=${page}&pageSize=${pageSize}&keyword=${keyword}`);
    
    const rsData = response.data!!;    

    return (<ClientPage 
      rsData={rsData}
      pageSize={Number(pageSize)} 
      page={Number(page)} 
      keyword={keyword} 
    />);
}
