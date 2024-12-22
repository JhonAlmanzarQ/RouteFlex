import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../services/login.service';
import { FormsModule } from '@angular/forms';

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

  constructor(private loginservice: LoginService) { }


  displayLoginForm() {
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

  login() {
    this.loginservice.login(this.name, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        console.log(response);
      },
      error: (err) => {
        this.mensaje = 'Error al iniciar sesión.';
        console.error(err);
      }
    });
  }

}
