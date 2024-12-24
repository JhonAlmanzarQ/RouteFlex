import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VehicleService } from '../services/vehicle.service';
import { vehicle } from '../models/vehicle';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-vehicle',
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './vehicle.component.html',
  styleUrl: './vehicle.component.css'
})

export class VehicleComponent implements OnInit {
  vehicles: vehicle[] = [];

  newVehicle: any = {
    usuario: {
      idUsuario: 1,
    },
    placa: '',
    peso: 0,
    estado: true,
    marca: '',
    tipoVehiculo: ''
  };

  // Variable para controlar la visibilidad del formulario
  showForm: boolean = false;

  // Variable para controlar si es un nuevo vehículo o una actualización
  isEdit: boolean = false;

  // Variable para almacenar el vehículo en edición
  currentVehicle: any = null;

  constructor(private vehicleService: VehicleService, private router: Router) {}

  ngOnInit(): void {
    const idUsuario = 1;
    this.vehicleService.listVehicle(idUsuario).subscribe({
      next: (response) => {
        this.vehicles = response;
      },
      error: (err) => {
        window.alert('Error al listar vehículo');
      }
    });
  }

  crearVehicle() {
    if (this.isEdit) {
      this.updateVehicle();
    } else {
      this.vehicleService.createVehicle(this.newVehicle).subscribe({
        next: (response) => {
          this.vehicles.push(response); // Agrega el nuevo vehículo a la lista actual
          this.resetForm(); // Restablecer formulario
          this.showForm = false; // Oculta el formulario
        },
        error: (err) => {
          window.alert('Error al crear vehículo');
        }
      });
    }
  }
  

  cancelForm() {
    this.resetForm();
    this.showForm = false;
  }

  resetForm() {
    this.isEdit = false;
    this.newVehicle = {
      usuario: {
        idUsuario: 1,
      },
      placa: '',
      peso: 0,
      estado: true,
      marca: '',
      tipoVehiculo: ''
    };
  }

  editVehicle(vehicle: any) {
    this.isEdit = true;
    this.currentVehicle = { ...vehicle }; // Copia los datos del vehículo en edición
    this.showForm = true;
    this.newVehicle = { ...vehicle }; // Rellena el formulario con los datos
  }

  updateVehicle() {
    this.vehicleService.updateVehicle(this.newVehicle).subscribe({
      next: (response) => {
        this.showForm = false; // Oculta el formulario después de actualizar
        this.isEdit = false; // Cambia el estado a creación
        this.ngOnInit(); // Refresca la lista de vehículos
      },
      error: (err) => {
        window.alert('Error al actualizar vehiculo');
      }
    });
  }

  deleteVehicle(idVehiculo: number): void {
    if (confirm('¿Estás seguro de eliminar este vehículo?')) {
      this.vehicleService.deleteVehicle(idVehiculo).subscribe({
        next: (response) => {
          this.vehicles = this.vehicles.filter(vehicle => vehicle.idVehiculo !== idVehiculo);
        },
        error: (err) => {
          window.alert('Error al eliminar vehiculo');
        }
      });
    }
  }

}
