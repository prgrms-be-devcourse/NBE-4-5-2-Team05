"use client";

import { Button } from "@/components/ui/button";
import { components } from "@/lib/backend/apiV1/schema";
import client from "@/lib/client";
import { cookies } from "next/headers";
import client from "@/lib/backend/client";
import { faBookBookmark, faThumbsUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Geist, Geist_Mono } from "next/font/google";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { FaStore } from "react-icons/fa";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"


export default function ClientLayout({
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
  fontVariable,
  fontClassName,
  me,
}: Readonly<{
  children: React.ReactNode;
  fontVariable: string;
  fontClassName: string;
  me: components["schemas"]["UserDto"];
}>) {
  const isLogin = me.id !== "";
  const router = useRouter();
  const isLogined = me.id !== "";

  async function handleLogout(e: React.MouseEvent<HTMLButtonElement>) {
    e.preventDefault();
    const response = await client.POST("/api/users/logout", {
      credentials: "include",
    });

    if (response.error) {
      alert(response.error.message);
      return;
    }

    window.location.href = "/";
  }

  return (
    <html lang="en" className={`${fontVariable}`}>
      <body className={`min-h-[100dvh] flex flex-col ${fontClassName}`}>
        <header className="flex justify-between">
          <Link href="/" className="flex items-center gap-2">
            <Button>
              <FaStore className="text-lg" />
              길게 볼 장터
            </Button>
          </Link>
          {isLogin ? (
            <div className="flex items-center gap-2">
              <Link href="/user/me">
                <Button>내 정보</Button>
              </Link>
              <Button className="cursor-pointer" onClick={handleLogout}>
                로그아웃
              </Button>
            </div>
          ) : (
            <Link href="/user/login">
              <Button>로그인 및 회원가입</Button>
            </Link>
          )}
        </header>
        <div className="flex-grow">{children}</div>
        <footer>푸터</footer>
      </body>
    </html>
  );
}