import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { assignRoute } from '../models/assignRoute';
import { assignRouteDto } from '../models/assignRouteDto';

@Injectable({
  providedIn: 'root'
})
export class AssignRouteService {

  private apiUrl ="http://localhost:8080/asignar";

  constructor(private http: HttpClient) {}

  //Listar por usuario
  listAssignRoute(idUser: number): Observable<assignRouteDto[]> {
    return this.http.get<assignRouteDto[]>(`${this.apiUrl}/listarUsuario/${idUser}`);
  }

  //Listar por conductor
  listAssignRouteDriver(idDriver: number): Observable<assignRouteDto[]> {
    return this.http.get<assignRouteDto[]>(`${this.apiUrl}/listar/${idDriver}`);
  }

  //Crear
  createAssignRoute(assignRoute: assignRoute): Observable<assignRoute> {
    return this.http.post<assignRoute>(`${this.apiUrl}/crear`, assignRoute);
  }

  //Eliminar
  deleteAssignRoute(idAsignarRuta: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${idAsignarRuta}`);
  }

}
