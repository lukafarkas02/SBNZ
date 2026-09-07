import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../../../core/service/auth.service';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent {
  menuOpen = false;

  constructor(private router: Router,
              public authService: AuthService){}

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  openDashboard(){
    this.router.navigate(['/dashboard']);
  }

  openWarnings(){
    this.router.navigate(['/warnings']);
  }

  openRecommendations(){
    this.router.navigate(['/recommendations']);
  }

  openTemplate(){
    this.router.navigate(['/template']);
  }

  openAdminMeasurements() {
    this.router.navigate(['/admin-measurements']);
  }

  openAdminWarnings() {
    this.router.navigate(['/admin-warnings']);
  }
}
