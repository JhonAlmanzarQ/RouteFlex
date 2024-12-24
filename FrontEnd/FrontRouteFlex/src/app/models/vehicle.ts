import { usuario } from "./usuario";

export interface vehicle {
    idVehiculo: number;
    tipoVehiculo: string;
    peso: number;
    marca: string;
    placa: string;
    estado: boolean;
    usuario: usuario;

}