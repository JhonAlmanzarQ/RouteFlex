import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { route } from '../models/route';
import { RouteService } from '../services/route.service';
import { paquete } from '../models/paquete';
import { PaqueteService } from '../services/paquete.service';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-route',
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './route.component.html',
  styleUrls: ['./route.component.css']
})
export class RouteComponent implements OnInit {

  routes: route[] = [];
  paquetes: paquete[] = [];
  selectedPaquetes: paquete[] = []; 
  showForm: boolean = false;

  constructor(
    private routeService: RouteService, 
    private paqueteService: PaqueteService, 
    private router: Router
  ) {}

  ngOnInit(): void {
    this.listRoutes();
    this.listPaquete();
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

  listPaquete(): void {
    const idUsuario = 1; // Cambia este ID según sea necesario
    this.paqueteService.listPaquete(idUsuario).subscribe({
      next: (response) => {
        this.paquetes = response;
      },
      error: () => {
        window.alert('Error al listar paquetes');
      }
    });
  }

  deleteRoute(idRuta: number): void {
    if (confirm('¿Estás seguro de eliminar esta ruta?')) {
      this.routeService.deleteRoute(idRuta).subscribe({
        next: () => {
          this.routes = this.routes.filter(route => route.idRuta !== idRuta);
        },
        error: () => {
          window.alert('Error al eliminar ruta');
        }
      });
    }
  }

  createRoute(): void {
    if (this.selectedPaquetes.length <= 1) {
      window.alert('Selecciona al menos dos paquetes para crear una ruta.');
      return;
    }

    this.routeService.createRoute(this.selectedPaquetes).subscribe({
      next: (newRoute) => {
        this.routes.push(newRoute); 
        this.showForm = false; 
      },
      error: () => {
        window.alert('Error al crear la ruta');
      }
    });

    this.selectedPaquetes = [];
  }

  togglePaqueteSelection(paquete: paquete): void {
    const index = this.selectedPaquetes.findIndex(select => select.idPaqueteEnvio === paquete.idPaqueteEnvio);
    if (index > -1) {
      this.selectedPaquetes.splice(index, 1); // Quitar si ya estaba seleccionado
    } else {
      this.selectedPaquetes.push(paquete); // Agregar si no estaba seleccionado
    }
  }
}
