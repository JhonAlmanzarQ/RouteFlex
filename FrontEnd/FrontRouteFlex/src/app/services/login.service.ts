import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loginUrl = 'http://localhost:8080/login';
  private apiUrl ="http://localhost:8080/usuario"
  private apiUrlConductor ="http://localhost:8080/conductor"

  constructor(private http: HttpClient) { }

  // Login
  loginUsuario(nombre: string, contrasena: string):Observable<any> {
    const body = { nombre, contrasena };
    return this.http.post<any>(this.loginUrl, body);
  }

  loginConductor(nombre: string, contrasena: string):Observable<any> {
    const body = { nombre, contrasena };
    return this.http.post<any>(`${this.apiUrlConductor}/login`, body);
  }

  // Crear Usuario
  createUsuario(usuario: usuario): Observable<usuario> {
    return this.http.post<usuario>(`${this.apiUrl}/crear`, usuario);
  }


}
