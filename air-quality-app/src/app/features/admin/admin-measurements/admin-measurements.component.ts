import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../core/service/admin.service';
import {NavigationComponent} from '../../../layout/navigation/navigation/navigation.component';

@Component({
  selector: 'app-admin-measurements',
  standalone: true,
  imports: [CommonModule, NavigationComponent],
  templateUrl: './admin-measurements.component.html',
  styleUrls: ['./admin-measurements.component.css']
})
export class AdminMeasurementsComponent implements OnInit {

  measurements: any[] = [];
  loading = true;
  errorMessage = '';

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadMeasurements();
  }

  loadMeasurements(): void {
    this.adminService.getAllMeasurements().subscribe({
      next: (data) => {
        this.measurements = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to load measurements.';
        this.loading = false;
      }
    });
  }
}
