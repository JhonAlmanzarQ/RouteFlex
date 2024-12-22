import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loginUrl = 'http://localhost:8080/login';

  constructor(private http: HttpClient) { }

  login(nombre: string, contrasena: string):Observable<any> {
    const body = { nombre, contrasena };
    return this.http.post<any>(this.loginUrl, body);
  }

}
