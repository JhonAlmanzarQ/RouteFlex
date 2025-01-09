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

  idUser!: number;

  userName!: string;

  showForm: boolean = false;

  assignRoutesDt: assignRouteDto[] = [];
  routes: route[] = [];
  vehicles: vehicle[] = [];
  drivers: driver[] = [];

  newAssignRoute: assignRoute = {
    usuario: { idUsuario: this.idUser }, 
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
    this.getId();
    this.getName();
    this.listAssignRoute();
    this.listRoutes();
    this.listVehicles();
    this.listDrivers();
    
  }

  //Listar todo
  listAssignRoute(): void {
    this.assignRouteService.listAssignRoute(this.idUser).subscribe({
      next: (response) => {
        this.assignRoutesDt = response;
      },
      error: (err) => {
        window.alert('Error al listar Asignar Ruta');
      }
    })
  }

  listRoutes(): void {
    this.routeService.listRoute(this.idUser).subscribe({
      next: (response) => {
        this.routes = response;
      },
      error: () => {
        window.alert('Error al listar rutas');
      }
    });
  }

  listVehicles(): void {
    this.vehicleService.listVehicle(this.idUser).subscribe({
      next: (response) => {
        this.vehicles = response;
      },
      error: () => {
        window.alert('Error al listar vehículos');
      }
    });
  }

  listDrivers(): void {
    this.driverService.listDriver(this.idUser).subscribe({
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
    const assignRouteToCreate: assignRoute = {
      usuario: { idUsuario: this.idUser },
      ruta: { idRuta: this.newAssignRoute.ruta.idRuta },
      vehiculo: { idVehiculo: this.newAssignRoute.vehiculo.idVehiculo },
      conductor: { idConductor: this.newAssignRoute.conductor.idConductor },
    };
  
    this.assignRouteService.createAssignRoute(assignRouteToCreate).subscribe({
      next: () => {
        window.alert('Ruta asignada exitosamente');
        console.log(assignRouteToCreate.vehiculo.idVehiculo)
        this.updateVehicleState(assignRouteToCreate.vehiculo.idVehiculo, true)
        this.listAssignRoute(); // Actualizar la lista
        this.showForm = false; // Ocultar el formulario
      },
      error: () => {
        window.alert('Error al asignar la ruta');
        console.log(assignRouteToCreate);
      },
    });
  }

  updateVehicleState(idVehiculo: number, estadoUpdate: boolean): void {
    // Buscar el vehículo en la lista de vehículos cargados
    const vehicleToUpdate = this.vehicles.find(vehicle => vehicle.idVehiculo === Number(idVehiculo));
    if (!vehicleToUpdate) {
      window.alert('Vehículo no encontrado');
      return;
    }

    const updatedVehicle: vehicle = {
      ...vehicleToUpdate,  
      estado: estadoUpdate,
    };

    this.vehicleService.updateVehicle(updatedVehicle).subscribe({
      next: () => {
      },
      error: (err) => {
        window.alert('Error al actualizar el estado del vehículo');
      },
    });
  }


  //Eliminar
  deleteAssignRoute(idAsignarRuta: number): void {
    const assignRouteToDelete = this.assignRoutesDt.find(route => route.idAsignarRuta === idAsignarRuta);
  
    if (assignRouteToDelete) {
      // Obtener el ID del vehículo 
      const idVehiculo = assignRouteToDelete.vehiculo.idVehiculo;
  
      if (confirm('¿Estás seguro de eliminar este Asignar Ruta?')) {
        this.assignRouteService.deleteAssignRoute(idAsignarRuta).subscribe({
          next: () => {
            this.updateVehicleState(idVehiculo, false);
            this.assignRoutesDt = this.assignRoutesDt.filter(assignRouteDt => assignRouteDt.idAsignarRuta !== idAsignarRuta);
          },
          error: () => {
            window.alert('Error al eliminar Asignar Ruta');
          }
        });
      }
    } else {
      window.alert('Asignación de ruta no encontrada');
    }
  }

}
