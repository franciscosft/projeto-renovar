import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: 'login.html',
  styleUrl: 'login.scss'
})
export class LoginPage {
  email = '';
  senha = '';

  constructor(private router: Router) {}

  voltar() {
    this.router.navigate(['/home']);
  }
}
