  import { Injectable } from '@angular/core';
  import { HttpClient } from '@angular/common/http';
  import { Observable } from 'rxjs';
  import { paquete } from '../models/paquete';

  @Injectable({
    providedIn: 'root'
  })
  export class PaqueteService {

    private apiUrl ="http://localhost:8080/paquete";

    constructor(private http:HttpClient) { }

    //Listar
    listPaquete(idUsuario: number): Observable<paquete[]> {
      return this.http.get<paquete[]>(`${this.apiUrl}/listar/${idUsuario}`);
    }

    //Crear
    createPaquete(paquete: paquete): Observable<paquete> {
      return this.http.post<paquete>(`${this.apiUrl}/crear`, paquete);
    }

    //Actualizar
    updatePaquete(paquete: paquete): Observable<paquete> {
      return this.http.put<paquete>(`${this.apiUrl}/actualizar`, paquete);
    }

    //Eliminar
    deletePaquete(idPaquete: number): Observable<any> {
      return this.http.delete(`${this.apiUrl}/eliminar/${idPaquete}`);
    }

  }
