"use client";
import { components, paths } from "@/lib/backend/apiV1/schema";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function ClientPage({
    rsData,
    pageSize,
    page,
    keyword,
}:{
    rsData:components["schemas"]["RsDataPageDtoPreviewPostResponse"];
    keyword: string;
    pageSize:number;
    page:number;
}) {
    const router=useRouter();
    const pageDto=rsData.data;

    // useEffect(() => {
    //     if (!rsData) {
    //         alert("데이터를 가져오는 중 오류가 발생했습니다.");
    //         router.push("/post");
    //     }
    // }, [rsData, router]);

    // if (!pageDto || !pageDto.totalPages) {
    //     return <div>페이지 정보를 불러오는 중 오류가 발생했습니다.</div>;
    // }

    return (
      <div>
        <h1>글 목록</h1>
  
        <div>응답 코드1 : {rsData?.code}</div>
        <div>결과 메시지 : {rsData?.message}</div>

        <div>
            <strong>Total Pages: </strong> {pageDto.totalPages}
        </div>
        <div>
            <strong>Total Items: </strong> {pageDto.totalItems}
        </div>
        <div>
            <strong>Current Page: </strong> {pageDto.curPageNo}
        </div>
        <hr />
        

          <form 
          onSubmit={(e) =>{
              e.preventDefault();
              const formData=new FormData(e.target as HTMLFormElement);
              const searchKeyword= (formData.get("keyword") as String).trim();
              const page=1;
              // const page=formData.get("page") as String
              const pageSize = parseInt(formData.get("pageSize") as string);

              router.push(
                `/post?page=${page}&keyword=${searchKeyword}&pageSize=${pageSize}` // 공백 제거
              );
          }} >
            <input placeholder="제목" type="text" name="keyword"/>
            <input type="submit" value="검색"/>         
            <label className="ml-5" htmlFor="">페이지 사이즈:</label>
            <select name="pageSize" defaultValue={pageSize}>
              <option value="3">3</option>
              <option value="5">5</option>
              <option value="10">10</option>
            </select>
          </form>

          <div className="flex gap-3">
            {
              Array.from({length:pageDto.totalPages},(_,i) => i+1).map((pageNo)=>{
                return <Link
                key={pageNo}
                className={pageNo==Number(page) ? `text-red-500` : ""} 
                href={`/post?page=${pageNo}&keyword=${keyword.trim()}&pageSize=${pageSize}`}>
                  {pageNo}
                </Link>
              })
            }
          </div>
          
        <ul>
          {pageDto.items.map((item) => {
            return (
              <li className="border-3 border-red-500 my-2 p-2" key={item.id}>
                <div>id : {item.id}</div>
                <div>productName : {item.productName}</div>
                <div>title : {item.title}</div>
                <div>price : {item.productPrice}</div>
                <div>writerName : {item.writerName}</div>
                <div>writerId : {item.writerId}</div>
                <div>
                    <strong>Location</strong>
                     <div>latitude: {item.latitude}</div>
                     <div>longitude: {item.longitude}</div>
                </div>
                <div>thumbNail : {item.thumbNail}</div>
                <div>createdAt : {item.createdAt}</div>

              </li>
            );
          })}
        </ul>
      </div>
    );
}