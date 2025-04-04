"use client";

import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { useToast } from "@/hooks/use-toast";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import constants from "@/lib/constants";
import { createCar, updateCar } from "@/lib/http";
import type { Car } from "@/types/car";

const carSchema = z.object({
  brand: z.string().min(1, { message: "La marca es obligatoria" }),
  model: z.string().min(1, { message: "El modelo es obligatorio" }),
  year: z.coerce
    .number()
    .min(1900, { message: "El año debe ser al menos 1886" })
    .max(new Date().getFullYear() + 1, {
      message: "El año no puede estar en el futuro",
    }),
  plate: z.string().min(1, { message: "Número de matrícula obligatorio" }),
  color: z.string().min(1, { message: "El color es obligatorio" }),
  photo: z.string().optional(),
});

type CarFormValues = z.infer<typeof carSchema>;

interface CarModalProps {
  isOpen: boolean;
  onClose: () => void;
  car: Car | null;
  onSave: () => void;
}

export function CarModal({ isOpen, onClose, car, onSave }: CarModalProps) {
  const [isLoading, setIsLoading] = useState(false);
  const { toast } = useToast();

  const form = useForm<CarFormValues>({
    resolver: zodResolver(carSchema),
    defaultValues: {
      brand: car?.brand || "",
      model: car?.model || "",
      year: car?.year || new Date().getFullYear(),
      plate: car?.plate || "",
      color: car?.color || "#000000",
      photo: car?.photo || "",
    },
  });

  useState(() => {
    if (isOpen) {
      form.reset({
        brand: car?.brand || "",
        model: car?.model || "",
        year: car?.year || new Date().getFullYear(),
        plate: car?.plate || "",
        color: car?.color || "#000000",
        photo: car?.photo || "",
      });
    }
  });

  async function onSubmit(data: CarFormValues) {
    setIsLoading(true);
    try {
      if (car) {
        const response = await updateCar(car);
        toast({
          title: response.status
            ? constants.TITLE_OPERATION_SUCCESSFUL
            : constants.TITLE_OPERATION_FAILED,
          description: response.message,
        });
      } else {
        const response = await createCar<CarFormValues>(data);
        toast({
          title: response.status
            ? constants.TITLE_OPERATION_SUCCESSFUL
            : constants.TITLE_OPERATION_FAILED,
          description: response.message,
        });
      }
      onSave();
    } catch (error) {
      toast({
        title: constants.TITLE_OPERATION_FAILED,
        description: constants.MSG_OPERATION_FAILED,
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-[500px]">
        <DialogHeader>
          <DialogTitle>{car ? "Editar Vehículo" : "Agregar Vehículo"}</DialogTitle>
          <DialogDescription>
            {car
              ? "Actualizar los datos del coche seleccionado."
              : "Rellena los datos para añadir un coche nuevo."}
          </DialogDescription>
        </DialogHeader>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <FormField
                control={form.control}
                name="brand"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Marca</FormLabel>
                    <FormControl>
                      <Input placeholder="Toyota" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="model"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Modelo</FormLabel>
                    <FormControl>
                      <Input placeholder="Corolla" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            <div className="grid grid-cols-2 gap-4">
              <FormField
                control={form.control}
                name="year"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Año</FormLabel>
                    <FormControl>
                      <Input type="number" placeholder="2023" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="plate"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Número de Placa</FormLabel>
                    <FormControl>
                      <Input placeholder="ABC-1234" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            <div className="grid grid-cols-2 gap-4">
              <FormField
                control={form.control}
                name="color"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Color</FormLabel>
                    <FormControl>
                      <div className="flex gap-2">
                        <Input
                          type="color"
                          className="w-12 p-1 h-10"
                          {...field}
                        />
                        <Input
                          type="text"
                          placeholder="#000000"
                          value={field.value}
                          onChange={field.onChange}
                        />
                      </div>
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="photo"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Foto URL (opcional)</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="https://example.com/car.jpg"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            <DialogFooter>
              <Button type="button" variant="outline" onClick={onClose}>
                Cancelar
              </Button>
              <Button type="submit" disabled={isLoading}>
                {isLoading ? "Guardando..." : car ? "Actualizar" : "Guardar"}
              </Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
}
