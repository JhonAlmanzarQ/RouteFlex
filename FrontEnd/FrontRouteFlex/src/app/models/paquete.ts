import { usuario } from "./usuario";

export interface paquete {
    idPaqueteEnvio:number;
    nombre:String;
    numero:number;
    direccion:String;
    estado:String;
    fecha:String
    usuario: usuario;
}