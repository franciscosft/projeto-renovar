import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: 'register.html',
  styleUrl: 'register.scss'
})
export class RegisterPage {
  name = '';
  email = '';
  password = '';
  confirmPassword = '';
  errorMessage = '';

  constructor(private router: Router, private authService: AuthService) {}

  submit() {
    this.errorMessage = '';
    this.authService.register(this.name, this.email, this.password).subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.errorMessage = 'Registration failed. Please try again.'
    });
  }

  goBack() {
    this.router.navigate(['/home']);
  }
}