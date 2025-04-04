"use client";

import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Edit, Trash, CarIcon } from "lucide-react";
import type { Car } from "@/types/car";
import type { User } from "@/types/user";

interface CarCardsProps {
  cars: Car[];
  isLoading: boolean;
  onEdit: (car: Car) => void;
  onDelete: (car: Car) => void;
}

export function CarCards({ cars, isLoading, onEdit, onDelete }: CarCardsProps) {
  if (isLoading) {
    return (
      <div className="flex justify-center py-10">
        <div className="animate-pulse text-center">
          <div className="h-8 w-32 bg-muted rounded mb-4 mx-auto"></div>
          <div className="h-4 w-48 bg-muted rounded mx-auto"></div>
        </div>
      </div>
    );
  }

  if (cars.length === 0) {
    return (
      <div className="text-center py-10 bg-card rounded-lg shadow-sm border">
        <CarIcon className="h-12 w-12 mx-auto text-muted-foreground mb-4" />
        <h3 className="text-lg font-medium">No cars found</h3>
        <p className="mt-1 text-muted-foreground">
          Try adjusting your search or filters.
        </p>
      </div>
    );
  }

  return (
    <div className="grid gap-4">
      {cars.map((car) => (
        <Card
          key={crypto.randomUUID()}
          className="overflow-hidden border hover:border-primary/20 transition-colors"
        >
          <CardContent className="p-0">
            <div className="bg-primary p-4 text-primary-foreground">
              <div className="flex justify-between items-center">
                <h3 className="font-bold">
                  {car.brand} {car.model}
                </h3>
                <div className="text-sm bg-primary-foreground/20 px-2 py-1 rounded">
                  {car.year}
                </div>
              </div>
            </div>
            <div className="p-4 space-y-3">
              <div className="grid grid-cols-2 gap-2 text-sm">
                <div>
                  <p className="text-muted-foreground">Plate</p>
                  <p className="font-medium">{car.plate}</p>
                </div>
                <div>
                  <p className="text-muted-foreground">Color</p>
                  <div className="flex items-center gap-2">
                    <div
                      className="w-4 h-4 rounded-full"
                      style={{ backgroundColor: car.color }}
                    />
                    <span>{car.color}</span>
                  </div>
                </div>
              </div>
              <div className="flex justify-end gap-2 pt-2">
                <Button variant="outline" size="sm" onClick={() => onEdit(car)}>
                  <Edit className="h-4 w-4 mr-1" />
                  Edit
                </Button>
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => onDelete(car)}
                >
                  <Trash className="h-4 w-4 mr-1" />
                  Delete
                </Button>
              </div>
            </div>
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
