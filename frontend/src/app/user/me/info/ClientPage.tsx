"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import client from "@/lib/client";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function ClientPage() {
  const router = useRouter();
  const [formData, setFormData] = useState({
    email: "",
    nickname: "",
    address: "",
    profileUrl: "",
  });

  useEffect(() => {
    async function fetchUserData() {
      const response = await client.GET("/api/users/me", {
        credentials: "include",
      });

      if (response.error) {
        alert("사용자 정보를 불러오지 못했습니다.");
        return;
      }

      const rsData = response.data.data;

      setFormData({
        nickname: rsData.nickname || "",
        email: rsData.email || "",
        address: rsData.address || "",
        profileUrl: rsData.profileUrl || "",
      });
    }

    fetchUserData();
  }, []);

  function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  }

  async function updateInfo(e: React.FormEvent) {
    e.preventDefault();

    if (formData.nickname.length < 2 || formData.nickname.length > 20) {
      alert("닉네임은 2~20자 사이여야 합니다.");
      return;
    }

    const response = await client.PUT("/api/users/me", {
      body: formData,
      credentials: "include",
    });

    if (response.error) {
      alert(response.error.message);
      return;
    }

    alert("사용자 정보가 성공적으로 수정되었습니다.");
    router.push("/user/me");
  }

  return (
    <>
      <div className="flex h-full">
        <div className="flex flex-col gap-2">
          <h2 className="text-xl font-bold text-center">회원 정보 수정</h2>
          <form onSubmit={updateInfo} className="flex flex-col w-[350px] gap-2">
            <div>
              <label
                htmlFor="email"
                className="block text-sm font-medium text-gray-700"
              >
                {" "}
                이메일:
              </label>
              <Input
                type="email"
                name="email"
                placeholder="이메일"
                className="border-2 border-black"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>
            <div>
              <label
                htmlFor="nickname"
                className="block text-sm font-medium text-gray-700"
              >
                {" "}
                닉네임:
              </label>
              <Input
                type="text"
                name="nickname"
                placeholder="닉네임"
                className="border-2 border-black"
                value={formData.nickname}
                onChange={handleChange}
                required
              />
            </div>
            <div>
              <label
                htmlFor="address"
                className="block text-sm font-medium text-gray-700"
              >
                주소:
              </label>
              <Input
                type="text"
                name="address"
                placeholder="주소"
                className="border-2 border-black w-[500px]"
                value={formData.address}
                onChange={handleChange}
              />
            </div>
            <div>
              <label
                htmlFor="address"
                className="block text-sm font-medium text-gray-700"
              >
                프로필URL:
              </label>
              <Input
                type="url"
                name="profileUrl"
                placeholder="프로필URL"
                className="border-2 border-black w-[500px]"
                value={formData.profileUrl}
                onChange={handleChange}
              />
            </div>

            <Button type="submit" className="bg-blue-500 text-white p-2 mt-4">
              수정
            </Button>
          </form>
        </div>
      </div>
    </>
  );
}
