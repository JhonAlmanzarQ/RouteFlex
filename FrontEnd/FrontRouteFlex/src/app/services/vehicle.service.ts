import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { vehicle } from '../models/vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private apiUrl ="http://localhost:8080/vehiculo";

  constructor(private http: HttpClient) { }

  // Listar
  listVehicle(idUsuario: number): Observable<vehicle[]> {
    return this.http.get<vehicle[]>(`${this.apiUrl}/listar/${idUsuario}`);
  }

  //Crear
  createVehicle(vehicle: vehicle): Observable<vehicle> {
    return this.http.post<vehicle>(`${this.apiUrl}/crear`, vehicle);
  }

  //Actualizar
  updateVehicle(vehicle: vehicle): Observable<vehicle> {
    return this.http.put<vehicle>(`${this.apiUrl}/actualizar`, vehicle);
  }

  //Eliminar
  deleteVehicle(idVehiculo: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${idVehiculo}`);
  }

}
