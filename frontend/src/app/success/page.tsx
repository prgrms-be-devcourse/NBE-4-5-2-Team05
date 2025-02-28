import { useRouter } from "next/navigation";
import ClientPage from "./clientPage";

export default async function Page({
  searchParams,
}: {
  searchParams: {
    paymentType: string;
    orderId: string;
    paymentKey: string;
    amount: number;
  };
}) {
  const { paymentType, orderId, paymentKey, amount } = await searchParams;

  const response = await fetch(
    `http://localhost:8080/api/payments/request?orderId=${orderId}&paymentKey=${paymentKey}&amount=${amount}`
  );
  const json = await response.json();

  console.log(json);

  return <ClientPage></ClientPage>;
}
