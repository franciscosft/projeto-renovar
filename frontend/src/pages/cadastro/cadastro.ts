import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [FormsModule],
  templateUrl: 'cadastro.html',
  styleUrl: 'cadastro.scss'
})
export class CadastroPage {
  nome = '';
  sobrenome = '';
  email = '';
  senha = '';
  confirmarSenha = '';

  constructor(private router: Router) {}

  voltar() {
    this.router.navigate(['/home']);
  }
}
