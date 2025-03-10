"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import Link from "next/link";
import Script from "next/script";

export default function ClientPage() {
  return (
    <>
      <div className="flex items-center justify-center">
        <div className="flex flex-col gap-2">
          <form className="flex flex-col w-full gap-2">
            <Input
              type="text"
              name="username"
              placeholder="아이디"
              className="border-2 border-black w-full"
            />
            <Input
              type="text"
              name="password"
              placeholder="비밀번호"
              className="border-2 border-black w-full"
            />
            <Button type="submit">로그인</Button>
          </form>
          <Button>
            <Link href="/user/signup">회원가입</Link>
          </Button>
          <Button variant="ghost" className="w-full mt-4 p-0">
            <Link
              href="http://localhost:8080/oauth2/authorization/kakao?redirectUrl=localhost:3000"
              className="w-full"
            >
              <img src="/kakao_login.png" className="w-full" />
            </Link>
          </Button>
        </div>
      </div>
    </>
  );
}
