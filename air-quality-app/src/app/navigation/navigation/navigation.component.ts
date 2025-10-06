import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent {
  menuOpen = false;
  
  constructor(private router: Router){}

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
}
