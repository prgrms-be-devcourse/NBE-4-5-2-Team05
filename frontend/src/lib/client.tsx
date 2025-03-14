import createClient from "openapi-fetch";
import { paths } from "./backend/apiV1/schema";

const client = createClient<paths>({
  baseUrl: "http://3.35.108.184:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

export default client;
