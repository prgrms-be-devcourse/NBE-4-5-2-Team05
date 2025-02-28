import CheckoutPage from "./ClientPage";

export default async function Page({searchParams}:{
  searchParams:{
    orderId:string,
    amount:number
  }
}){
  const {orderId, amount} = await searchParams;
  return <CheckoutPage orderId={orderId} totalAmount={amount}></CheckoutPage>
}