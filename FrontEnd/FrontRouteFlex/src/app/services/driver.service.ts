import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { driver } from '../models/driver';

@Injectable({
  providedIn: 'root'
})
export class DriverService {

  private apiUrl ="http://localhost:8080/conductor";

  constructor(private http: HttpClient) { }

  //Listar
  listDriver(idUsuario:number): Observable<driver[]> {
    return this.http.get<driver[]>(`${this.apiUrl}/listar/${idUsuario}`);
  }

  //Crear
  createDriver(driver: driver): Observable<driver> {
    return this.http.post<driver>(`${this.apiUrl}/crear`, driver);
  }

  //Actualizar
  updateDriver(driver: driver): Observable<driver> {
    return this.http.put<driver>(`${this.apiUrl}/actualizar`, driver);
  }

  //Eliminar
  deleteDriver(idConductor: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${idConductor}`);
  }

}
