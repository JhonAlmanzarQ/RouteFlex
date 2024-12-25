import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../services/login.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  showSignupForm: boolean = false;
  showLoginForm: boolean = false;

  name: string = '';
  password: string = '';
  mensaje: string = '';
  userType: string = '';

  newUser: any = {
    nombre: '',
    apellido: '',
    correo: '',
    contrasena: '',
    telefono: ''
  };

  constructor(private loginservice: LoginService, private router: Router) { }


  displayLoginForm(userType: string) {
    this.userType = userType;
    this.showLoginForm = true;
    this.showSignupForm = false;
  }
  displaySignupForm() {
    this.showSignupForm = true;
    this.showLoginForm = false; 
  }
  hideForm() {
    this.showSignupForm = false;
    this.showLoginForm = false;
  }


  login(){
    if(this.userType === 'usuario') {
      this.loginUser();
    } else {
      this.loginDriver();
    }
  }

  loginUser() {
    this.loginservice.loginUsuario(this.name, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.router.navigate(['/driver']); 
      },
      error: (err) => {
        this.mensaje = 'Usuario o contraseña incorrectos.';
        window.alert('Error al iniciar sesión: Nombre o contraseña incorrectos');
      }
    });
  }

  loginDriver() {
    this.loginservice.loginConductor(this.name, this.password).subscribe({
      next: (response) => {
        this.router.navigate(['/vehicle']);
      },
      error: (err) => {
        window.alert('Error al iniciar sesión: Nombre o contraseña incorrectos');
      }
    });
  }

  createUser() {

    if (!this.newUser.nombre || !this.newUser.apellido || !this.newUser.correo || !this.newUser.contrasena || !this.newUser.telefono) {
      window.alert('Todos los campos son obligatorios');
      return;
    }
    this.loginservice.createUsuario(this.newUser).subscribe({
      next: (response) => {
        console.log('Usuario creado:', response);
        this.mensaje = 'Usuario creado exitosamente.';
        this.hideForm();
      },
      error: (err) => {
        console.error('Error al crear usuario:', err);
        this.mensaje = 'Error al crear el usuario. Inténtalo de nuevo.';
      }
    })
  }

}
