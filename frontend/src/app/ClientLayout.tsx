"use client";

import { components } from "@/lib/backend/apiV1/schema";
import client from "@/lib/backend/client";
import { Geist, Geist_Mono } from "next/font/google";
import Link from "next/link";
import { useRouter } from "next/navigation";


const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export default function ClinetLayout({
  children,
  me,
}: Readonly<{
  children: React.ReactNode;
  me:components["schemas"]["UserDto"];
}>) {
  const router = useRouter();
  const isLogined = me.id !== "";

  return (
    <html lang="en">
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
      >
        <header className="flex gap-3">
          <Link href="/">메인</Link>
          {!isLogined && <Link href="/member/login">로그인</Link>}
          {isLogined &&(
            <Link href="" onClick={async (e)=>{
              e.preventDefault();
              const response=await client.POST("/api/users/logout",{
                credentials:"include",
              });
              if(response.error){
                alert(response.error.message);
                return;
              }
              
              router.push("/");
              }}>로그아웃</Link>
          )}          
          {isLogined && <Link href="/chat">채팅방</Link>}

          {isLogined && <Link href="/member/me">내정보</Link>}
        </header>
        <div>
          {children}
        </div>
      </body>
    </html>
  );
}