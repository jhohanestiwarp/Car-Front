"use client";
import { useRouter } from "next/navigation";
import { LogOut, Car } from "lucide-react";
import { useAuthStore } from "@/store/auth-store";
import { useToast } from "@/hooks/use-toast";
import { Button } from "@/components/ui/button";
import constants from "@/lib/constants";

export function DashboardHeader() {
  const { user, clearAuth } = useAuthStore();
  const router = useRouter();
  const { toast } = useToast();

  const handleLogout = () => {
    clearAuth();
    toast({ title: constants.TITLE_LOGGED, description: constants.MSG_LOGGED });
    router.push("/login");
  };

  return (
    <header className="border-b">
      <div className="container flex h-16 px-8 items-center justify-between">
        <div className="flex items-center gap-2">
          <Car className="h-6 w-6" />
          <span className="text-xl font-bold">Car Front</span>
        </div>
        <div className="flex items-center gap-4">
          {user && (
            <span className="text-sm text-muted-foreground">
              Bienvenido, {user.name}
            </span>
          )}
          <Button variant="outline" size="sm" onClick={handleLogout}>
            <LogOut className="mr-2 h-4 w-4" />
            Cerrar sesión
          </Button>
        </div>
      </div>
    </header>
  );
}
