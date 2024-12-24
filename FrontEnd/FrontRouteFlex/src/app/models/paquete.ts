import { usuario } from "./usuario";

export interface paquete {
    idPaqueteEnvio:number;
    nombre:String;
    numero:number;
    direccion:String;
    peso:number;
    fecha:String
    usuario: usuario;
}