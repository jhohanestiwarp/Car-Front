"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { useAuthStore } from "@/store/auth-store";
import { CarList } from "@/components/car-list";
import { DashboardHeader } from "@/components/dashboard-header";
import { refreshToken } from "@/lib/http";

export default function DashboardPage() {
  const { token } = useAuthStore();
  const { setToken, setUser } = useAuthStore();
  const router = useRouter();

  useEffect(() => {
    const checkToken = async () => {
      const res = await refreshToken();
      console.log({ res });

      if (!res || !res.status) return router.push("/login");

      setToken(res.data.token, res.data.refresh_token);
      setUser(res.data.user);
    };
    if (!token) checkToken();
  }, [token, router]);

  if (!token) return null;

  return (
    <div className="flex flex-col min-h-screen">
      <DashboardHeader />
      <main className="flex-1 container px-8 py-6">
        <div className="mb-8">
          <h1 className="text-3xl font-bold mb-2">
            Panel de Gestión de Vehículos
          </h1>
          <p className="text-muted-foreground">
            Gestione sus vehículos con facilidad
          </p>
        </div>
        <CarList />
      </main>
    </div>
  );
}
