import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { VehicleComponent } from './vehicle/vehicle.component';
import { DriverComponent } from './driver/driver.component';
import { PaqueteComponent } from './paquete/paquete.component';
import { UserComponent } from './user/user.component';

export const routes: Routes = [
    {
        path: '',
        component: LoginComponent
    },
    {
        path:"vehicle",
        component:VehicleComponent
    },
    {
        path:"driver",
        component:DriverComponent
    },
    {
        path:"paquete",
        component:PaqueteComponent
    },
    {
        path:"user",
        component:UserComponent
    }
];
