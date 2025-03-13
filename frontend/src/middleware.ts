import { RequestCookie } from "next/dist/compiled/@edge-runtime/cookies";
import { cookies } from "next/headers";
import createClient from "openapi-fetch";
import { NextRequest, NextResponse } from "next/server";
import client from "./lib/client";
import { parseAccessToken } from "./app/util/auth";

export async function middleware(request: NextRequest) {
  const myCookie = await cookies();
  const accessTokenCookie = myCookie.get("accessToken");
  const refreshTokenCookie = myCookie.get("refreshToken");

  // ✅ 1. AccessToken이 없으면 강제 로그아웃
  if (!accessTokenCookie) {
    console.log("❌ AccessToken 없음 → 강제 로그아웃 실행");
    return forceLogout(request, "/user/login");
  }

  const { isLogin, isExpired } = parseAccessToken(accessTokenCookie);

  // ✅ 2. 로그인 상태이고, AccessToken이 유효하면 패스
  if (isLogin && !isExpired) {
    console.log("✅ AccessToken 유효 → 요청 패스");
    return NextResponse.next();
  }

  // ✅ 3. AccessToken이 만료됨 → RefreshToken 존재 여부 확인
  if (isExpired) {
    if (!refreshTokenCookie) {
      console.log("❌ RefreshToken 없음 → 강제 로그아웃 실행");
      return forceLogout(request, "/user/login");
    }

    console.log("🔄 AccessToken 만료 → RefreshToken으로 재발급 시도");
    return await refreshAccessToken(refreshTokenCookie);
  }
}

// ❌ 강제 로그아웃 + 쿠키 삭제 + 로그인 화면 이동 (헤더 UI 반영)
function forceLogout(request: NextRequest, redirectUrl: string = "/") {
  console.log("🔴 강제 로그아웃 실행");

  const nextResponse = NextResponse.redirect(new URL(redirectUrl, request.url));

  // 🟢 AccessToken 삭제
  nextResponse.headers.append(
    "Set-Cookie",
    `accessToken=; Path=/; HttpOnly; Secure; SameSite=Strict; Max-Age=0`
  );

  return nextResponse;
}

// 🔄 RefreshToken을 사용해 AccessToken 재발급
async function refreshAccessToken(refreshToken: RequestCookie) {
  console.log("🔄 AccessToken 만료 → RefreshToken으로 재발급 시도");

  const response = await client.POST("/api/users/refresh", {
    body: { refreshToken: refreshToken.value },
    credentials: "include",
  });

  console.log("🔍 Refresh API 응답 데이터:", response.data);
  const newAccessToken = response.data?.data;
  console.log("✅ AccessToken 재발급 성공:", newAccessToken);

  const nextResponse = NextResponse.next();

  // ✅ 새로운 AccessToken 쿠키 저장 (Max-Age 추가)
  nextResponse.headers.append(
    "Set-Cookie",
    `accessToken=${newAccessToken}; Path=/; HttpOnly; Secure; SameSite=Strict; Max-Age=3600`
  );

  // 🗑 RefreshToken 삭제 (1회용)
  nextResponse.headers.append(
    "Set-Cookie",
    `refreshToken=; Path=/; HttpOnly; Secure; SameSite=Strict; Max-Age=0`
  );

  return nextResponse;
}

// middleware 지날 곳을 향후에 더 추가
export const config = {
  matcher: ["/user/me/:path*"], // `/user/me` 및 모든 하위 경로에 적용
};
