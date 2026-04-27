import { Routes } from '@angular/router';
import { authGuard } from '../guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    loadComponent: () => import('../pages/home/home').then(m => m.HomePage),
    canActivate: [authGuard]
  },
  {
    path: 'reading',
    loadComponent: () => import('../pages/reading/reading').then(m => m.ReadingPage),
    canActivate: [authGuard]
  },
  {
    path: 'devices/new',
    loadComponent: () => import('../pages/devices/device-registration').then(m => m.DeviceRegistrationPage),
    canActivate: [authGuard]
  },
  {
    path: 'login',
    loadComponent: () => import('../pages/login/login').then(m => m.LoginPage)
  },
  {
    path: 'register',
    loadComponent: () => import('../pages/cadastro/register').then(m => m.RegisterPage)
  },
  {
    path: 'documentation',
    loadComponent: () => import('../pages/documentation/documentation').then(m => m.DocumentationPage)
  },
];