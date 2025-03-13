"use client";

import { components } from "@/lib/backend/apiV1/schema";

export default function ClinetPage(
    {me,}:{
        me:components["schemas"]["UserDto"];
    }
) {
  
  return (
    <>
        <h1 className="mb-2"><strong>내 정보 페이지</strong></h1>
        <div className="mb-1"><strong>id: </strong>${me.id}</div>
        <div className="mb-1"><strong>username: </strong>${me.username}</div>
        <div className="mb-1"><strong>email: </strong>${me.email}</div>
        <div className="mb-1"><strong>nickname: </strong>${me.nickname}</div>
        <div className="mb-1"><strong>role: </strong>${me.role}</div>

    </>
   )
}