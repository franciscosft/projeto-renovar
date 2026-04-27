import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_CONFIG } from '../../config/api.config';
import { IndicatorDTO } from '../../models/indicator.dto';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-device-registration',
  standalone: true,
  imports: [FormsModule],
  templateUrl: 'device-registration.html',
  styleUrl: 'device-registration.scss'
})
export class DeviceRegistrationPage implements OnInit {
  name = '';
  latitude = '';
  longitude = '';
  indicators: IndicatorDTO[] = [];
  selectedIndicatorIds: number[] = [];
  errorMessage = '';

  private subscriptionHeaders = new HttpHeaders({ 'X-Subscription-Key': API_CONFIG.subscriptionKey });

  constructor(
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.http
      .get<IndicatorDTO[]>(`${API_CONFIG.baseUrl}/indicators`, { headers: this.subscriptionHeaders })
      .subscribe({
        next: indicators => this.indicators = indicators,
        error: () => {}
      });
  }

  toggleIndicator(id: string) {
    const numId = +id;
    const idx = this.selectedIndicatorIds.indexOf(numId);
    if (idx === -1) {
      this.selectedIndicatorIds.push(numId);
    } else {
      this.selectedIndicatorIds.splice(idx, 1);
    }
  }

  isSelected(id: string): boolean {
    return this.selectedIndicatorIds.includes(+id);
  }

  submit() {
    this.errorMessage = '';
    const payload = {
      name: this.name,
      coordinate: { latitude: +this.latitude, longitude: +this.longitude },
      userId: this.authService.getUserId(),
      indicators: this.selectedIndicatorIds.map(id => ({ id }))
    };
    this.http.post(`${API_CONFIG.baseUrl}/devices`, payload).subscribe({
      next: () => this.router.navigate(['/home']),
      error: () => this.errorMessage = 'Failed to register device. Please try again.'
    });
  }

  goBack() {
    this.router.navigate(['/home']);
  }
}