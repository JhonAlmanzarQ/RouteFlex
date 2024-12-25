import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/user.service';
import { usuario } from '../models/usuario';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-user',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  usuario: usuario = {
    idUsuario: 1,
    nombre: '',
    apellido: '',
    correo: '',
    telefono: '',
    contrasena: ''
  };

  usuarioEditable: usuario = {
    idUsuario: 1,
    nombre: '',
    apellido: '',
    correo: '',
    telefono: '',
    contrasena: ''
  };

  showForm: boolean = false;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    const idUser = 1;
    this.userService.listUsuarioId(idUser).subscribe({
      next: (response) => {
        this.usuario = response;
        this.usuarioEditable = { ...response }; 
      },
      error: (err) => {
        window.alert('Error al cargar usuario');
      }
    });
  }

  updateUsuario() {
    this.userService.updateUsuario(this.usuarioEditable).subscribe({
      next: () => {
        window.alert('Usuario actualizado correctamente');
        this.usuario = { ...this.usuarioEditable }; 
        this.toggleForm();
      },
      error: () => {
        window.alert('Error al actualizar usuario');
      }
    });
  }

  deleteUsuario(id: number): void {
    if (confirm('¿Estás seguro de eliminar este usuario?')) {
      this.userService.deleteUsuario(id).subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
        error: () => {
          window.alert('Error al eliminar usuario');
        }
      });
    }
  }

  toggleForm() {
    this.showForm = !this.showForm;
    if (this.showForm) {
      this.usuarioEditable = { ...this.usuario }; // Sincronizamos usuarioEditable antes de editar
    }
  }
}