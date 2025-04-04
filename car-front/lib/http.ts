import axios from "axios";
import { Car } from "@/types/car";
import { HTTPBaseResponse, HTTPResponse } from "@/types/http";
import { useAuthStore } from "@/store/auth-store";
import { UserRegister, UserLogin, Token } from "@/types/user";

const API_URL = "https://apimocha.com/tendo";

const axiosInstance = axios.create({
  baseURL: API_URL,
  headers: { "Content-Type": "application/json" },
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().token;
    if (token && !config.url?.includes("/auth/")) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const getCars = async (search: string = "") => {
  const { data } = await axiosInstance.get<HTTPResponse<Car[]>>("/cars", {
    params: { search },
  });
  return data;
};

export const getCarById = async (carId: number) => {
  const { data } = await axiosInstance.get<HTTPResponse<Car>>(`/cars/${carId}`);
  return data;
};

export const createCar = async <T>(carData: T) => {
  const { data } = await axiosInstance.post<HTTPBaseResponse>("/cars", carData);
  return data;
};

export const updateCar = async (carData: Car) => {
  const { data } = await axiosInstance.put<HTTPBaseResponse>(
    `/cars/${carData.carId}`,
    carData
  );
  return data;
};

export const deleteCar = async (carId: number) => {
  const { data } = await axiosInstance.delete<HTTPBaseResponse>(
    `/cars/${carId}`
  );
  return data;
};

export const registerUser = async (userData: UserRegister) => {
  const { data } = await axiosInstance.post<HTTPBaseResponse>(
    "/auth/register",
    userData
  );
  return data;
};

export const loginUser = async (loginData: UserLogin) => {
  const { data } = await axiosInstance.post<HTTPResponse<Token>>(
    "/auth/login",
    loginData
  );
  return data;
};

export const refreshToken = async () => {
  try {
    const { data } = await axiosInstance.post<HTTPResponse<Token>>(
      "/auth/refresh-token",
      { refresh_token: sessionStorage.getItem("rt") || "" }
    );
    return data;
  } catch (error) {
    return null;
  }
};

export const getUserData = async () => {
  const { data } = await axiosInstance.get<HTTPResponse<Token>>("/user-data");
  return data;
};
