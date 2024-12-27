import { usuario } from "./usuario";

export interface route {
    idRuta: number;   
    overviewPolyline: string;    
    coordenadas: string[];
    direcciones: string[];
    usuario: usuario; 
}