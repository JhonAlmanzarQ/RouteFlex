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

  idUser!: number;

  userName: string | null = null;

  vehicles: vehicle[] = [];

  newVehicle: any = {
    usuario: {
      idUsuario: this.idUser,
    },
    placa: '',
    estado: true,
    marca: '',
    tipoVehiculo: ''
  };

  showForm: boolean = false;

  isEdit: boolean = false;

  currentVehicle: any = null;

  constructor(private vehicleService: VehicleService, private router: Router) {}

  getId(): void {
    const usuario = localStorage.getItem('idUser');
    if (usuario) {
      this.idUser = parseInt(usuario, 10);  
    } else {
      window.alert('Error');;
    }
  }

  getName(): void {
    const user = localStorage.getItem('nameUser');
    if (user) {
      this.userName = user;
    } else {
      window.alert('Error');
    }
  }

  ngOnInit(): void {
    this.getName();
    this.getId();
    this.vehicleService.listVehicle(this.idUser).subscribe({
      next: (response) => {
        this.vehicles = response;
      },
      error: (err) => {
        window.alert('Error al listar vehículo');
        console.log(err);
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
        idUsuario: this.idUser,
      },
      placa: '',
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
