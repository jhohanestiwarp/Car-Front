"use client";

import type React from "react";

import { useState, useEffect } from "react";
import { useAuthStore } from "@/store/auth-store";
import { useToast } from "@/hooks/use-toast";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { CarModal } from "@/components/car-modal";
import { DeleteConfirmDialog } from "@/components/delete-confirm-dialog";
import { Plus, Search } from "lucide-react";
import { useIsMobile } from "@/hooks/use-mobile";
import { CarTable } from "@/components/car-table";
import { CarCards } from "@/components/car-cards";
import constants from "@/lib/constants";
import { deleteCar, getCars } from "@/lib/http";
import type { Car } from "@/types/car";

export function CarList() {
  const [cars, setCars] = useState<Car[]>([]);
  const [filteredCars, setFilteredCars] = useState<Car[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");
  const [brandFilter, setBrandFilter] = useState("all");
  const [yearFilter, setYearFilter] = useState("all");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCar, setSelectedCar] = useState<Car | null>(null);
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
  const [carToDelete, setCarToDelete] = useState<Car | null>(null);

  const { token } = useAuthStore();
  const { toast } = useToast();
  const isMobile = useIsMobile();

  const uniqueBrands = Array.from(new Set(cars.map((car) => car.brand)));
  const uniqueYears = Array.from(new Set(cars.map((car) => car.year))).sort(
    (a, b) => b - a
  );

  const fetchCars = async () => {
    setIsLoading(true);
    try {
      const response = await getCars(searchTerm);
      setCars(response.data);
      setFilteredCars(response.data);
    } catch (error) {
      toast({
        title: constants.TITLE_OPERATION_FAILED,
        description: constants.MSG_OPERATION_FAILED,
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (token) {
      fetchCars();
    }
  }, [token, searchTerm]);

  useEffect(() => {
    let filtered = [...cars];

    if (brandFilter && brandFilter !== "all") {
      filtered = filtered.filter((car) => car.brand === brandFilter);
    }

    if (yearFilter && yearFilter !== "all") {
      filtered = filtered.filter(
        (car) => car.year === Number.parseInt(yearFilter)
      );
    }

    setFilteredCars(filtered);
  }, [cars, brandFilter, yearFilter]);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    fetchCars();
  };

  const handleAddCar = () => {
    setSelectedCar(null);
    setIsModalOpen(true);
  };

  const handleEditCar = (car: Car) => {
    setSelectedCar(car);
    setIsModalOpen(true);
  };

  const handleDeleteClick = (car: Car) => {
    setCarToDelete(car);
    setIsDeleteDialogOpen(true);
  };

  const handleDeleteCar = async () => {
    if (!carToDelete) return;

    try {
      const response = await deleteCar(carToDelete.carId);

      toast({
        title: response.status
          ? constants.TITLE_OPERATION_SUCCESSFUL
          : constants.TITLE_OPERATION_FAILED,
        description: response.message,
      });

      fetchCars();
    } catch (error) {
      toast({
        title: constants.TITLE_OPERATION_FAILED,
        description: constants.MSG_OPERATION_FAILED,
        variant: "destructive",
      });
    } finally {
      setIsDeleteDialogOpen(false);
      setCarToDelete(null);
    }
  };

  const handleCarSaved = () => {
    setIsModalOpen(false);
    fetchCars();
  };

  return (
    <div className="space-y-6">
      <div className="p-4 bg-card rounded-lg shadow-sm border">
        <div className="flex flex-col sm:flex-row sm:justify-between items-start sm:items-center gap-4 items-center">
          <Button onClick={handleAddCar}>
            <Plus className="mr-2 h-4 w-4" />
            Nuevo item
          </Button>
          <form onSubmit={handleSearch} className="flex gap-2">
            <Input
              placeholder="Buscar por placa o modelo"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full sm:w-64"
            />
            <Button type="submit" variant="outline">
              <Search className="h-4 w-4" />
            </Button>
          </form>

          <div className="flex gap-2">
            <Select value={brandFilter} onValueChange={setBrandFilter}>
              <SelectTrigger className="w-full sm:w-32">
                <SelectValue placeholder="Brand" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">Todas las marcas</SelectItem>
                {uniqueBrands.map((brand) => (
                  <SelectItem key={crypto.randomUUID()} value={brand}>
                    {brand}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            <Select value={yearFilter} onValueChange={setYearFilter}>
              <SelectTrigger className="w-full sm:w-32">
                <SelectValue placeholder="Year" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">Todos los años</SelectItem>
                {uniqueYears.map((year) => (
                  <SelectItem key={crypto.randomUUID()} value={year.toString()}>
                    {year}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </div>
      </div>

      {isMobile ? (
        <CarCards
          cars={filteredCars}
          isLoading={isLoading}
          onEdit={handleEditCar}
          onDelete={handleDeleteClick}
        />
      ) : (
        <CarTable
          cars={filteredCars}
          isLoading={isLoading}
          onEdit={handleEditCar}
          onDelete={handleDeleteClick}
        />
      )}

      <CarModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        car={selectedCar}
        onSave={handleCarSaved}
      />

      <DeleteConfirmDialog
        isOpen={isDeleteDialogOpen}
        onClose={() => setIsDeleteDialogOpen(false)}
        onConfirm={handleDeleteCar}
        title="Eliminar Vehículo"
        description={`¿Estás seguro de que quieres borrar ${carToDelete?.brand} ${carToDelete?.model} (${carToDelete?.plate})? Esta acción no puede deshacerse.`}
      />
    </div>
  );
}
