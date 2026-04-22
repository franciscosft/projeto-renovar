import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    loadComponent: () => import('../pages/home/home').then(m => m.HomePage)
  },
  {
    path: 'coleta',
    loadComponent: () => import('../pages/coleta/coleta').then(m => m.ColetaPage)
  },
  {
    path: 'login',
    loadComponent: () => import('../pages/login/login').then(m => m.LoginPage)
  },
  {
    path: 'cadastro',
    loadComponent: () => import('../pages/cadastro/cadastro').then(m => m.CadastroPage)
  },
  {
    path: 'documentacao',
    loadComponent: () => import('../pages/documentacao/documentacao').then(m => m.DocumentacaoPage)
  },
];
