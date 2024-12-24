import { usuario } from "./usuario";

export interface driver {
    idConductor: number;
    nombre: string;
    apellido: string;
    correo: string;
    telefono: string;
    contrasena: string;
    usuario:usuario
}