import { RequestCookie } from "next/dist/compiled/@edge-runtime/cookies";
import { cookies } from "next/headers";
import createClient from "openapi-fetch";
import { NextRequest, NextResponse } from "next/server";
import client from "./lib/client";
import { parseAccessToken } from "./app/util/auth";

export async function middleware(request: NextRequest) {
  const myCookie = await cookies();
  const { isLogin, isExpired, payload } = parseAccessToken(
    myCookie.get("accessToken")
  );

  if (isLogin && isExpired) {
    return refreshAccessToken();
  }

  if (!isLogin && isProtectedRoute(request.nextUrl.pathname)) {
    return createUnauthorizedResponse();
  }
}

async function refreshAccessToken() {
  const nextResponse = NextResponse.next();

  /* TODO : 리프레시 설정 후 set-cookie 설정 필요 */

  // const response = await client.GET("/api/users/me", {
  //   headers: {
  //     cookie: (await cookies()).toString(),
  //   },
  // });

  // const spirngCookie = response.response.headers.getSetCookie();
  // nextResponse.headers.set("set-cookie", String(spirngCookie));

  return nextResponse;
}

function createUnauthorizedResponse() {
  return new NextResponse("로그인이 필요합니다.", {
    status: 401,
    headers: {
      "Content-Type": "text/html; charset=utf-8",
    },
  });
}

function isProtectedRoute(pathname: string) {
  return (
    pathname.startsWith("/post/write") || pathname.startsWith("/post/edit")
  );
}
