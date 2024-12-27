export interface assignRouteDto {
    idAsignarRuta: number; 
    ruta: RutaDTO;
    vehiculo: VehiculoDTO;
    conductor: ConductorDTO;
  }
  
  export interface RutaDTO {
    overviewPolyline: string;
    coordenadas: string[];
  }
  
  export interface VehiculoDTO {
    peso: number;
    placa: string;
    estado: boolean;
    marca: string;
    idVehiculo: number;
    tipoVehiculo: string;
  }

  export interface ConductorDTO {
    idConductor: number;
    nombre: string;
    apellido: string;
  }
  