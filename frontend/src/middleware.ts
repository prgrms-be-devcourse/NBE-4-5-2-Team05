// middleware.ts
// middleware.ts
import { NextRequest, NextResponse } from "next/server";

export function middleware(request: NextRequest) {
  // 개발 환경이면 인증 검사를 건너뜁니다.
  if (process.env.NODE_ENV === "development") {
    return NextResponse.next();
  }

  // /posts/new 경로에 대해 인증 체크
  if (request.nextUrl.pathname.startsWith("/posts/new")) {
    const accessToken = request.cookies.get("accessToken");
    if (!accessToken) {
      const loginUrl = request.nextUrl.clone();
      loginUrl.pathname = "/login";
      loginUrl.searchParams.set("redirect", request.nextUrl.pathname);
      return NextResponse.redirect(loginUrl);
    }
  }
  return NextResponse.next();
}

export const config = {
  matcher: ["/posts/new/:path*"],
};
