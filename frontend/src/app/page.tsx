"use client";

import Link from "next/link";

export default function Page() {
  return (
    <div>
      <button>
        <div>
        <a href="http://localhost:8080/oauth2/authorization/kakao?redirectUrl=localhost:3000">
          카카오 로그인 ㅋㅋ
        </a>
        </div>
        <div>
          <Link href="/post">게시글 정보</Link>
        </div>
        <div>
          <Link href="/login">로그인</Link>
        </div>
        <div>
          <Link href="/chat">채팅방</Link>
        </div>
      </button>
    </div>
  );
}
