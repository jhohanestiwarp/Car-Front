"use client";

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { Edit, Trash, CarIcon } from "lucide-react";
import type { Car } from "@/types/car";

interface CarTableProps {
  cars: Car[];
  isLoading: boolean;
  onEdit: (car: Car) => void;
  onDelete: (car: Car) => void;
}

export function CarTable({ cars, isLoading, onEdit, onDelete }: CarTableProps) {
  return (
    <div className="rounded-md border bg-card shadow-sm">
      <Table>
        <TableHeader className="bg-muted">
          <TableRow>
            <TableHead>Marca</TableHead>
            <TableHead>Modelo</TableHead>
            <TableHead>Año</TableHead>
            <TableHead>Placa</TableHead>
            <TableHead>Color</TableHead>
            <TableHead className="text-right">Opciones</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {isLoading ? (
            <TableRow>
              <TableCell colSpan={8} className="text-center py-10">
                <div className="animate-pulse flex flex-col items-center">
                  <div className="h-8 w-32 bg-muted rounded mb-4"></div>
                  <div className="h-4 w-48 bg-muted rounded"></div>
                </div>
              </TableCell>
            </TableRow>
          ) : cars.length === 0 ? (
            <TableRow>
              <TableCell colSpan={8} className="text-center py-10">
                <CarIcon className="h-12 w-12 mx-auto text-muted-foreground mb-4" />
                <h3 className="text-lg font-medium">No se encontraron datos</h3>
                <p className="mt-1 text-muted-foreground">
                  Prueba a ajustar la búsqueda o los filtros.
                </p>
              </TableCell>
            </TableRow>
          ) : (
            cars.map((car) => (
              <TableRow key={crypto.randomUUID()} className="hover:bg-muted/50">
                <TableCell>{car.brand}</TableCell>
                <TableCell>{car.model}</TableCell>
                <TableCell>{car.year}</TableCell>
                <TableCell>{car.plate}</TableCell>
                <TableCell>
                  <div className="flex items-center gap-2">
                    <div
                      className="w-4 h-4 rounded-full"
                      style={{ backgroundColor: car.color }}
                    />
                    {car.color}
                  </div>
                </TableCell>
                <TableCell className="text-right">
                  <div className="flex justify-end gap-2">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => onEdit(car)}
                    >
                      <Edit className="h-4 w-4" />
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => onDelete(car)}
                    >
                      <Trash className="h-4 w-4" />
                    </Button>
                  </div>
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
    </div>
  );
}
