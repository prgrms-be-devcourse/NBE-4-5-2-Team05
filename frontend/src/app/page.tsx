import CheckoutPage from "./clientPage"

export default async function Page({searchParams}:{
  searchParams:{
    
    amount:number
  }
}){
  const { amount} = await searchParams;
  return <CheckoutPage  totalAmount={amount}></CheckoutPage>
}