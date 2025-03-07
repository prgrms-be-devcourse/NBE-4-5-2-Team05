"use client";

import { Button } from "@/components/ui/button";
import Link from "next/link";

export default function ClientLayout({
  children,
  fontVariable,
  fontClassName,
}: Readonly<{
  children: React.ReactNode;
  fontVariable: string;
  fontClassName: string;
}>) {
  return (
    <html lang="en" className={`${fontVariable}`}>
      <body className={`min-h-[100dvh] flex flex-col ${fontClassName}`}>
        <header className="flex justify-between">
          <Button>
            <Link href="/">중고장터</Link>
          </Button>
          <Button>
            {/* 로그인 되면 로그아웃 버튼 & 내 정보*/}
            <Link href="/login">로그인 및 회원가입</Link>
          </Button>
        </header>
        <div className="flex-grow">{children}</div>
        <footer>푸터</footer>
      </body>
    </html>
  );
}
