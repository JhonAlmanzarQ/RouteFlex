import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { assignRoute } from '../models/assignRoute';
import { assignRouteDto } from '../models/assignRouteDto';
import { AssignRouteService } from '../services/assign-route.service';
import { route } from '../models/route';
import { RouteService } from '../services/route.service';
import { vehicle } from '../models/vehicle';
import { VehicleService } from '../services/vehicle.service';
import { driver } from '../models/driver';
import { DriverService } from '../services/driver.service';

@Component({
  selector: 'app-assign-route',
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './assign-route.component.html',
  styleUrl: './assign-route.component.css'
})
export class AssignRouteComponent implements OnInit {

  showForm: boolean = false;

  assignRoutesDt: assignRouteDto[] = [];
  routes: route[] = [];
  vehicles: vehicle[] = [];
  drivers: driver[] = [];

  newAssignRoute: assignRoute = {
    usuario: { idUsuario: 1 }, // Suponiendo que el ID del usuario siempre será el mismo
    ruta: { idRuta: 0 },
    vehiculo: { idVehiculo: 0 },
    conductor: { idConductor: 0 },
  };

  constructor(
    private assignRouteService: AssignRouteService,
    private routeService: RouteService,
    private vehicleService: VehicleService,
    private driverService: DriverService,
    private router: Router) {}

  ngOnInit(): void {
    this.listAssignRoute();
    this.listRoutes();
    this.listVehicles();
    this.listDrivers();
    
  }

  //Listar todo
  listAssignRoute(): void {
    const idUser = 1;
    this.assignRouteService.listAssignRoute(idUser).subscribe({
      next: (response) => {
        this.assignRoutesDt = response;
      },
      error: (err) => {
        window.alert('Error al listar Asignar Ruta');
      }
    })
  }

  listRoutes(): void {
    const idUser = 1; // Cambia este ID según sea necesario
    this.routeService.listRoute(idUser).subscribe({
      next: (response) => {
        this.routes = response;
      },
      error: () => {
        window.alert('Error al listar rutas');
      }
    });
  }

  listVehicles(): void {
    const idUser = 1;
    this.vehicleService.listVehicle(idUser).subscribe({
      next: (response) => {
        this.vehicles = response;
      },
      error: () => {
        window.alert('Error al listar vehículos');
      }
    });
  }

  listDrivers(): void {
    const idUser = 1;
    this.driverService.listDriver(idUser).subscribe({
      next: (response) => {
        this.drivers = response;
      },
      error: () => {
        window.alert('Error al listar conductores');
      }
    });
  }
  

  //Crear
  createAssignRoute(): void {
  this.assignRouteService.createAssignRoute(this.newAssignRoute).subscribe({
      next: () => {
        window.alert('Ruta asignada exitosamente');
        this.listAssignRoute(); // Actualizar la lista
        this.showForm = false; // Ocultar el formulario
      },
      error: () => {
        window.alert('Error al asignar la ruta');
        console.log(this.newAssignRoute)
      },
    });
  }


  //Eliminar
  deleteAssignRoute(idAsignarRuta: number): void {
    if (confirm('¿Estás seguro de eliminar este Asignar Ruta?')) {
      this.assignRouteService.deleteAssignRoute(idAsignarRuta).subscribe({
        next: () => {
          this.assignRoutesDt = this.assignRoutesDt.filter(assignRouteDt => assignRouteDt.idAsignarRuta!== idAsignarRuta);
        },
        error: () => {
          window.alert('Error al eliminar Asignar Ruta');
        }
      });
    }
  }


}
