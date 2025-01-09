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

  idUser!: number;

  userName!: string;

  routes: route[] = [];
  paquetes: paquete[] = [];
  selectedPaquetes: paquete[] = []; 
  showForm: boolean = false;

  constructor(
    private routeService: RouteService, 
    private paqueteService: PaqueteService, 
    private router: Router
  ) {}

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
    this.listRoutes();
    this.listPaquete();
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

  listPaquete(): void {
    this.paqueteService.listPaquete(this.idUser).subscribe({
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
        this.selectedPaquetes.forEach(paquete => {
          this.updatePaqueteEstado(paquete.idPaqueteEnvio, 'En camino');
        });
        this.routes.push(newRoute); 
        this.showForm = false; 
        this.selectedPaquetes = [];
      },
      error: () => {
        window.alert('Error al crear la ruta');
      }
    });
  }
  

  updatePaqueteEstado(idPaquete: number, estadoUpdate: String) {
    const paqueteUpdate = this.paquetes.find(paquete => paquete.idPaqueteEnvio === idPaquete);
    if (!paqueteUpdate) {
      window.alert('Error al encontrar el paquete');
      return;
    }

    const updatedPaquete = {
      ...paqueteUpdate,
      estado: estadoUpdate 
    };

    this.paqueteService.updatePaquete(updatedPaquete).subscribe({
      next: () => {
      },
      error: () => {
        window.alert('Error al actualizar el estado del paquete');
      }
    });
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
