"use client";

import { v4 as uuidv4 } from "uuid";
export default function Page() {
  const a = async (orderId: string, amount: number) => {
    const result = await fetch(
      `http://localhost:8080/api/payments/metadata?id=${orderId}&amount=${amount}`
    );
    window.location.href = `/payment?orderId=${orderId}&amount=${amount}`;
  };
  return (
    <div>
      <input
        value={"결제하기"}
        type="button"
        onClick={(e) => {
          e.preventDefault();
          const orderId = "product-" + uuidv4();
          a(orderId, 5000);
        }}
      />
    </div>
  );
}
