import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DriverService } from '../services/driver.service';
import { driver } from '../models/driver';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-driver',
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './driver.component.html',
  styleUrl: './driver.component.css'
})
export class DriverComponent implements OnInit {

  idUser!: number;
  userName: string | null = null;

  drivers: driver[] = [];

  newDriver: any = {
    usuario: {
      idUsuario: this.idUser,
    },
    nombre: '',
    apellido: '',
    correo: '',
    telefono: '',
    contrasena: ''
  };

  isEdit: boolean = false;

  showForm: boolean = false;

  currentDriver: any = null;

  constructor(private driverService:DriverService, private router: Router) {}


  getId(): void {
    const usuario = localStorage.getItem('idUser');
    if (usuario) {
      this.idUser = parseInt(usuario, 10);  
    } else {
      window.alert('Error');
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

    this.driverService.listDriver(this.idUser).subscribe({
      next: (response) => {
        this.drivers = response;
      },
      error: (err) => {
        window.alert('Error'); 
      }
    })
  }

  createDriver() {
    if(this.isEdit){
      this.updateDriver();
    } else {
      this.driverService.createDriver(this.newDriver).subscribe({
        next: (response) => {
          this.drivers.push(response);
          this.resetForm();
          this.showForm = false;
        },
        error: (err) => {
          window.alert('Error al crear conductor');
        }
      })
    }
  }

  cancelForm() {
    this.resetForm();
    this.showForm = false;
  }

  resetForm() {
    this.isEdit = false;
    this.newDriver = {
      usuario: {
        idUsuario: this.idUser,
      },
      nombre: '',
      apellido: '',
      correo: '',
      telefono: '',
      contrasena: ''
    };
  }

  editDriver(driver: any) {
    this.isEdit = true;
    this.currentDriver = { ...driver }; // Copia los datos del driver en edición
    this.showForm = true;
    this.newDriver = { ...driver }; // Rellena el formulario con los datos
  }

  updateDriver() {
    this.driverService.updateDriver(this.newDriver).subscribe({
      next: (response) => {
        this.showForm = false;
        this.isEdit = false;
        this.ngOnInit();
      },
      error: (err) => {
        window.alert('Error al actualizar conductor');
        console.log(err);
      }
    });
  }

  deleteDriver(idConductor: number): void {
    if (confirm('¿Estás seguro de eliminar este conductor?')) {
      this.driverService.deleteDriver(idConductor).subscribe({
        next: (response) => {
          this.drivers = this.drivers.filter(driver => driver.idConductor!== idConductor);
        },
        error: (err) => {
          window.alert('Error al eliminar conductor');
        }
      });
    }
  }


}
