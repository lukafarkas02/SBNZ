import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../core/service/admin.service';
import {NavigationComponent} from '../../../layout/navigation/navigation/navigation.component';

@Component({
  selector: 'app-admin-warnings',
  standalone: true,
  imports: [CommonModule, NavigationComponent],
  templateUrl: './admin-warnings.component.html',
  styleUrls: ['./admin-warnings.component.css']
})
export class AdminWarningsComponent implements OnInit {

  warnings: any[] = [];
  loading = true;
  errorMessage = '';

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadWarnings();
  }

  loadWarnings(): void {
    this.adminService.getAllWarnings().subscribe({
      next: (data) => {
        this.warnings = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to load warnings.';
        this.loading = false;
      }
    });
  }
}
