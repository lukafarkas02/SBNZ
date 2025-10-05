import { Component, OnInit } from '@angular/core';
import { NavigationComponent } from '../navigation/navigation/navigation.component';
import { WarningService } from '../service/warning.service';
import { Warning } from '../models/warning.model';
import { CommonModule } from '@angular/common';
import { AirQualityService } from '../service/air-quality.service';
import { PollutantData } from '../models/pollutant-data.model';

@Component({
  selector: 'app-warnings',
  imports: [CommonModule, NavigationComponent],
  templateUrl: './warnings.component.html',
  styleUrl: './warnings.component.css'
})
export class WarningsComponent implements OnInit{

  userEmail: string | null = "";
  warnings: Warning[] = [];

  pollutantValues: PollutantData = { pm25: 0, pm10: 0, no2: 0, o3: 0, windSpeed: 0 };

  constructor(private warningService: WarningService, private qualityService: AirQualityService) { }

  ngOnInit(): void {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        const userObj = JSON.parse(userStr);
        this.userEmail = userObj.email || '';
        console.log("Korisnik:", userObj.email);

        this.loadWarnings();
        this.simulateCEP();
        console.log(this.warnings);
      } else {
        console.log("Nema korisnika u localStorage");
      }
    } else {
      console.warn("localStorage nije dostupan u ovom okruženju");
    }
  }

   loadWarnings(): void {
    this.warningService.getWarningsByEmail(this.userEmail!)
      .subscribe({
        next: (data) => this.warnings = data,
        error: (err) => console.error(err)
      });
  }

  simulateCEP(): void {
    if (!this.userEmail) return;

    const testDataSequence: PollutantData[] = [
      { pm25: 50.0, pm10: 50.0, no2: 42.0, o3: 30, windSpeed: 1.0 },
      { pm25: 45.0, pm10: 150.0, no2: 42.0, o3: 30, windSpeed: 1.0 },
      { pm25: 45.0, pm10: 150.0, no2: 42.0, o3: 30, windSpeed: 1.0 },
      { pm25: 45.0, pm10: 150.0, no2: 42.0, o3: 30, windSpeed: 1.0 },
      { pm25: 45.0, pm10: 150.0, no2: 42.0, o3: 30, windSpeed: 1.0 },
      { pm25: 70.0, pm10: 150.0, no2: 42.0, o3: 30, windSpeed: 1.0 }
    ];

    const sendData = (index: number) => {
      if (index >= testDataSequence.length) return;

      const body = { ...testDataSequence[index], userEmail: this.userEmail };
      this.pollutantValues = testDataSequence[index];

      this.qualityService.sendPollutantData(body).subscribe({
        next: () => {
          console.log(`Sent data #${index + 1}`);
          this.loadWarnings();
        },
        error: (err) => console.error(`Error sending data #${index + 1}`, err)
      });

      setTimeout(() => sendData(index + 1), 10000);
    };

    sendData(0);
  }

}
