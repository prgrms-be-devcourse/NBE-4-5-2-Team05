"use client";

import { paths } from "@/lib/backend/apiV1/schema";
import client from "@/lib/backend/client";
import { useRouter } from "next/navigation";
import createClient from "openapi-fetch";

export default function ClientPage() {

    const router=useRouter();
    
    async function login(e: React.FormEvent<HTMLFormElement>){

        e.preventDefault();
        const form=e.target as HTMLFormElement;
        const username=form.username.value;
        const password=form.password.value;

        const response=await client.POST("/api/users/login",{
            body:{
                username,
                password,
            },
            credentials:"include"
        });

        if(response.error){
            alert(response.error.message);
            return;
        }

        router.push(`/post`);

    }

    return (
        <>
            <div>로그인 페이지</div>
            <form onSubmit={login} className="flex flex-col w-1/5 gap-3" >
                <input type="text" name="username" placeholder="아이디 입력" className="border-2 border-black"/>
                <input type="password" name="password" placeholder="비밀번호 입력" className="border-2 border-black"/>
                <input type="submit" value="로그인" className="border-2 border-black" />
            </form>

        </>
    );
    
}