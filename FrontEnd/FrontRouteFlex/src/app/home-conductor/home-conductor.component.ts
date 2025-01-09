/// <reference types="google.maps" />
import { Component, OnInit } from '@angular/core';
import { AssignRouteService } from '../services/assign-route.service';
import { assignRouteDto } from '../models/assignRouteDto';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-home-conductor',
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './home-conductor.component.html',
  styleUrls: ['./home-conductor.component.css'],
})

export class HomeConductorComponent implements OnInit {
  map!: google.maps.Map;
  asignaciones: assignRouteDto[] = [];
  selectedAsignacion!: assignRouteDto;
  currentView: 'map' | 'info' = 'map'; // Controlar la vista actual

  constructor(private assignRouteService: AssignRouteService) {}

  ngOnInit(): void {
    this.obtenerAsignaciones();
  }

  // Obtener las asignaciones del conductor
  obtenerAsignaciones(): void {
    const conductorData = JSON.parse(localStorage.getItem('conductor') || '{}');
    const conductorId: number = conductorData.idConductor; 
    if (!conductorId) {
      console.error('No se encontró el id del conductor.');
      return;
    }

    this.assignRouteService.listAssignRouteDriver(conductorId).subscribe(
      (data) => {
        this.asignaciones = data;
        if (this.currentView === 'map') {
          this.loadMap();
        }
      },
      (error) => {
        console.error('Error al obtener asignaciones', error);
      }
    );
  }

  // Cambiar de vista
  switchView(view: 'map' | 'info'): void {
    this.currentView = view;
    if (view === 'map') {
      // Esperar a que Angular termine de renderizar
      setTimeout(() => {
        this.loadMap();
      }, 0);
    }
  }
  

  // Eliminar asignación
  eliminarAsignacion(idAsignarRuta: number): void {
    if (confirm('¿Está seguro de que desea eliminar esta asignación?')) {
      this.assignRouteService.deleteAssignRoute(idAsignarRuta).subscribe(
        () => {
          alert('Asignación eliminada exitosamente');
          this.obtenerAsignaciones(); // Recargar las asignaciones
        },
        (error) => {
          console.error('Error al eliminar asignación', error);
          alert('Ocurrió un error al intentar eliminar la asignación.');
        }
      );
    }
  }

  // Cargar el mapa y graficar la ruta
  loadMap(): void {
    const encodedPath = this.asignaciones[0]?.ruta?.overviewPolyline;
    if (!encodedPath) {
      console.error('No se encontró información para la ruta.');
      return;
    }

    this.map = new google.maps.Map(document.getElementById('map') as HTMLElement, {
      zoom: 13,
      center: { lat: 7.1186677, lng: -73.1247597 },
    });

    const path = google.maps.geometry.encoding.decodePath(encodedPath);
    const polyline = new google.maps.Polyline({
      path: path,
      geodesic: true,
      strokeColor: '#FF0000',
      strokeOpacity: 1.0,
      strokeWeight: 2,
    });

    polyline.setMap(this.map);

    const bounds = new google.maps.LatLngBounds();
    path.forEach((latLng) => bounds.extend(latLng));
    this.map.fitBounds(bounds);

    const coordenadasPuntos = this.asignaciones[0]?.ruta.coordenadas;
    if (coordenadasPuntos) {
      coordenadasPuntos.forEach((coord: string) => {
        const [lat, lng] = coord.split(',').map(Number);
        new google.maps.Marker({
          position: { lat, lng },
          map: this.map,
          title: `Punto: ${lat}, ${lng}`,
          icon: {
            path: google.maps.SymbolPath.CIRCLE,
            fillColor: 'blue',
            fillOpacity: 1,
            scale: 5,
            strokeColor: 'blue',
            strokeWeight: 2,
          },
        });
      });
    }
  }
}
