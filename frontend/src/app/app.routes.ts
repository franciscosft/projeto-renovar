import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    loadComponent: () => import('../pages/home/home').then(m => m.HomePage)
  },
  {
    path: 'reading',
    loadComponent: () => import('../pages/coleta/reading').then(m => m.ReadingPage)
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
    loadComponent: () => import('../pages/documentacao/documentation').then(m => m.DocumentationPage)
  },
];
