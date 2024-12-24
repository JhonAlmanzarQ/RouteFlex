import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaqueteService } from '../services/paquete.service';
import { paquete } from '../models/paquete';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-paquete',
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './paquete.component.html',
  styleUrl: './paquete.component.css'
})
export class PaqueteComponent implements OnInit {

  paquetes: paquete[] = [];

  newPaquete: any = {
    usuario: {
      idUsuario: 1,
    },
    nombre: '',
    numero: 0,
    direccion: '',
    pesoPaquete: 0,
    fecha: ''
  };

  showForm: boolean = false;

  isEdit: boolean = false;

  currentPaquete: any = null;


  constructor(private paqueteService: PaqueteService, private router: Router) {}

  ngOnInit(): void {
    const idUsuario = 1;
    this.paqueteService.listPaquete(idUsuario).subscribe({
      next: (response) => {
        this.paquetes = response;
      },
      error: (err) => {
        window.alert('Error al listar paquete');
      }
    })
  }

  createPaquete() {
    if(this.isEdit) {
      this.updatePaquete();
    }else{
      this.paqueteService.createPaquete(this.newPaquete).subscribe({
        next: (response) => {
          this.paquetes.push(response);
          this.resetForm();
          this.ngOnInit();
          this.showForm = false;
        },
        error: (err) => {
          window.alert('Error al crear paquete');
        }
      })
    }
  }

  cancelForm() {
    this.resetForm();
    this.showForm = false;
  }

  resetForm() {
    this.isEdit = false;
    this.newPaquete = {
      usuario: {
        idUsuario: 1,
      },
      nombre: '',
      numero: 0,
      direccion: '',
      pesoPaquete: 0,
      fecha: ''
    };
  }

  editPaquete(paquete: any) {
    this.isEdit = true;
    this.currentPaquete = { ...paquete }; 
    this.showForm = true;
    this.newPaquete = { ...paquete };
  }

  updatePaquete() {
    this.paqueteService.updatePaquete(this.newPaquete).subscribe({
      next: (response) => {
        this.showForm = false; 
        this.isEdit = false;
        this.ngOnInit();
        console.log(response);
      },
      error: (err) => {
        window.alert('Error al actualizar paquete');
        console.log(err);
      }
    });
  }

  deletePaquete(idPaquete: number): void {
    if (confirm('¿Estás seguro de eliminar este vehículo?')) {
      this.paqueteService.deletePaquete(idPaquete).subscribe({
        next: (response) => {
          this.paquetes = this.paquetes.filter(paquete => paquete.idPaqueteEnvio!== idPaquete);
        },
        error: (err) => {
          window.alert('Error al eliminar paquete');
        }
      })
  }
  }

}
