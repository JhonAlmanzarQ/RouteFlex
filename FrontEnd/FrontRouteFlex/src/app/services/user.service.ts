import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl ="http://localhost:8080/usuario";

  constructor(private http: HttpClient) { }

  //Listar por id
  listUsuarioId(idUsuario: number): Observable<usuario> {
    return this.http.get<usuario>(`${this.apiUrl}/listar/${idUsuario}`);
  }

  //Actualizar
  updateUsuario(usuario: usuario): Observable<usuario> {
    return this.http.put<usuario>(`${this.apiUrl}/actualizar`, usuario);
  }

  //Eliminar
  deleteUsuario(idUsuario: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${idUsuario}`);
  }

}
