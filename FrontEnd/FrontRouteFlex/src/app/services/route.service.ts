import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { route } from '../models/route';
import { paquete } from '../models/paquete';

@Injectable({
  providedIn: 'root'
})
export class RouteService {

  private apiUrl ="http://localhost:8080/ruta";

  constructor(private http: HttpClient) { }

  //Listar
  listRoute(idUsuario: number): Observable<route[]> {
    return this.http.get<route[]>(`${this.apiUrl}/listar/${idUsuario}`);
  }

  //Crear
  createRoute(paquetes: paquete[]): Observable<route> {
    return this.http.post<route>(`${this.apiUrl}/crear`, paquetes);
  }

  //Eliminar
  deleteRoute(idRuta: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${idRuta}`);
  }

}
