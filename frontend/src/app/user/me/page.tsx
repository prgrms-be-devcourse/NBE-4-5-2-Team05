import client from "@/lib/client";
import ClientPage from "./ClientPage";

export default async function Page() {
  const response = await client.GET("/api/users/me", {
    credentials: "include",
  });

  if (response.error) {
    console.log(response.error.message);
    return;
  }

  return <ClientPage />;
}
