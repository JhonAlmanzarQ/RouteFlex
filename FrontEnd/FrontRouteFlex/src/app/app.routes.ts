import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { VehicleComponent } from './vehicle/vehicle.component';

export const routes: Routes = [
    {
        path: '',
        component: LoginComponent
    },
    {
        path:"vehicle",
        component:VehicleComponent
    }
];
