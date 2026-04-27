import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: 'register.html',
  styleUrl: 'register.scss'
})
export class RegisterPage {
  name = '';
  lastName = '';
  email = '';
  password = '';
  confirmPassword = '';

  constructor(private router: Router) {}

  goBack() {
    this.router.navigate(['/home']);
  }
}
