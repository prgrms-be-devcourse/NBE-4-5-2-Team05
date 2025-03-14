import client from "@/lib/client";
import ClientPage from "./ClientPage";
import { cookies } from "next/headers";
import { parseAccessToken } from "@/app/util/auth";
import { redirect } from "next/navigation";
import RequireAuthenticated from "@/components/auth/RequireAuthenticated";

export default async function Page() {
  return (
    <RequireAuthenticated>
      <ClientPage />
    </RequireAuthenticated>
  );
}
