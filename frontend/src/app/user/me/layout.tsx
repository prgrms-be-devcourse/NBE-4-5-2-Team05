"use client";

import { Button } from "@/components/ui/button";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUser,
  faStore,
  faShoppingCart,
  faSignOutAlt,
} from "@fortawesome/free-solid-svg-icons";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";

export default function UserLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const [showSellOptions, setShowSellOptions] = useState(false);
  const [showBuyOptions, setShowBuyOptions] = useState(false);
  const pathname = usePathname();

  return (
    <div className="flex min-h-screen p-6">
      {/* ✅ 왼쪽 레이아웃 */}
      <aside className="border-r p-4 space-y-2 flex flex-col">
        <h2 className="font-bold text-xl mb-2">내 정보</h2>

        <Link href="/user/me/info">
          <Button
            variant="ghost"
            className={`cursor-pointer w-full justify-start ${pathname === "/user/me/info" ? "bg-blue-100 text-blue-600" : ""}`}
          >
            <FontAwesomeIcon icon={faUser} className="mr-2" />
            프로필 수정
          </Button>
        </Link>

        {/* ✅ 판매 아코디언 */}
        <Accordion type="single" collapsible>
          <AccordionItem value="sell">
            <div className="flex items-center h-[40px] px-3">
              {/* ✅ 아이콘을 `AccordionTrigger` 바깥으로 이동 */}
              <FontAwesomeIcon icon={faStore} className="mr-2" />
              <AccordionTrigger className="flex-1 text-left">
                판매
              </AccordionTrigger>
            </div>
            <AccordionContent className="ml-4 space-y-2">
              <Link href="/user/me/sell/manage">
                <Button
                  variant="ghost"
                  className={`w-full justify-start ${pathname === "/user/me/sell/manage" ? "bg-blue-100 text-blue-600" : ""}`}
                >
                  내 상품 관리
                </Button>
              </Link>
              <Link href="/user/me/sell/history">
                <Button
                  variant="ghost"
                  className={`w-full justify-start ${pathname === "/user/me/sell/history" ? "bg-blue-100 text-blue-600" : ""}`}
                >
                  판매 내역
                </Button>
              </Link>
              <Link href="/user/me/sell/shipping">
                <Button
                  variant="ghost"
                  className={`w-full justify-start ${pathname === "/user/me/sell/shipping" ? "bg-blue-100 text-blue-600" : ""}`}
                >
                  배송 신청
                </Button>
              </Link>
            </AccordionContent>
          </AccordionItem>
        </Accordion>

        {/* ✅ 구매 메뉴 */}
        {/* ✅ 구매 아코디언 */}
        <Accordion type="single" collapsible>
          <AccordionItem value="purchase">
            <div className="flex items-center h-[40px] px-3">
              {/* ✅ 아이콘을 `AccordionTrigger` 바깥으로 이동 */}
              <FontAwesomeIcon icon={faShoppingCart} className="mr-2" />
              <AccordionTrigger className="flex-1 text-left">
                구매
              </AccordionTrigger>
            </div>

            <AccordionContent>
              <Link href="/user/me/purchase/history">
                <Button
                  variant="ghost"
                  className={`w-full justify-start ${
                    pathname === "/user/me/purchase/history"
                      ? "bg-blue-100 text-blue-600"
                      : ""
                  }`}
                >
                  구매 내역
                </Button>
              </Link>
              <Link href="/user/me/purchase/wishlist">
                <Button
                  variant="ghost"
                  className={`w-full justify-start ${
                    pathname === "/user/me/purchase/wishlist"
                      ? "bg-blue-100 text-blue-600"
                      : ""
                  }`}
                >
                  찜한 상품
                </Button>
              </Link>
            </AccordionContent>
          </AccordionItem>
        </Accordion>

        <Link href="/user/me/delete">
          <Button
            variant="ghost"
            className={`w-full justify-start h-[40px] px-3 ${
              pathname === "/user/me/delete" ? "bg-blue-100 text-blue-600" : ""
            }`}
          >
            <FontAwesomeIcon icon={faSignOutAlt} className="mr-2" />
            회원탈퇴
          </Button>
        </Link>
      </aside>

      {/* ✅ 오른쪽 컨텐츠 (8) */}
      <main className="w-4/5 p-4">{children}</main>
    </div>
  );
}
