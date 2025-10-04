import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationComponent } from '../../navigation/navigation/navigation.component';
import { AirQualityInfoResponse } from '../../models/air-quality-info-response.model';
import { AirQualityService } from '../../service/air-quality.service';
import { PollutantResponse } from '../../models/pollutant-response.model';
import { NgChartsModule } from 'ng2-charts';
import { ChartData, ChartOptions, ChartType } from 'chart.js';
import { PollutantHistoryDTO } from '../../models/pollutant-history-dto.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NavigationComponent, NgChartsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{

  airQualityInfo: AirQualityInfoResponse | null = null;

  userFirstName: string | null = '';
  userEmail: string | null = '';

  gaugeRotation: number = 0;
  gaugeColor: string = "";

  public lineChartData: ChartData<'line'> = {
    labels: [], // ovde idu timestampi
    datasets: [
      { data: [], label: 'PM2.5', borderColor: 'blue', fill: false },
      { data: [], label: 'PM10', borderColor: 'red', fill: false }
    ]
  };
  public lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
    plugins: { legend: { display: true } },
    scales: { x: { title: { display: true, text: 'Time' } }, y: { title: { display: true, text: 'μg/m³' } } }
  };
  public lineChartType: 'line' = 'line';

  private simulationInterval: any;

  private currentPollutants = {
    PM2_5: 0,
    PM10: 0,
    NO2: 0,
    O3: 0
  };

  constructor(private airQualityService : AirQualityService) {}

  ngOnInit(): void {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        const userObj = JSON.parse(userStr);
        this.userFirstName = userObj.firstName || '';
        this.userEmail = userObj.email || '';
        console.log("Korisnik:", userObj.email);
      } else {
        console.log("Nema korisnika u localStorage");
      }

      //this.fetchAirQuality();
      this.initPollutants();
      this.startSimulation();
      this.loadLast24hData();
    } else {
      console.warn("localStorage nije dostupan u ovom okruženju");
    }
  }

  ngOnDestroy(): void {
    if (this.simulationInterval) clearInterval(this.simulationInterval);
  }

  randomizeValue(min: number, max: number): number {
    return Math.round((Math.random() * (max - min) + min) * 10) / 10;
  }

  initPollutants() {
    this.currentPollutants = {
      PM2_5: this.randomizeValue(15, 60),
      PM10: this.randomizeValue(20, 80),
      NO2: this.randomizeValue(25, 100),
      O3: this.randomizeValue(30, 120)
    };
  }

  randomStep(maxStep: number): number {
    return Math.round((Math.random() * 2 * maxStep - maxStep) * 10) / 10;
  }

  clamp(value: number, min: number, max: number): number {
    return Math.max(min, Math.min(max, value));
  }

  generateMeasurementWithDrift() {
    const stepPM25 = this.randomStep(3);
    const stepPM10 = this.randomStep(3);
    const stepNO2 = this.randomStep(5);
    const stepO3 = this.randomStep(5);

    this.currentPollutants.PM2_5 = this.clamp(this.currentPollutants.PM2_5 + stepPM25, 0, 100);
    this.currentPollutants.PM10 = this.clamp(this.currentPollutants.PM10 + stepPM10, 0, 100);
    this.currentPollutants.NO2 = this.clamp(this.currentPollutants.NO2 + stepNO2, 0, 100);
    this.currentPollutants.O3 = this.clamp(this.currentPollutants.O3 + stepO3, 0, 100);

    const round1 = (v: number) => Math.round(v * 10) / 10;

    return {
      pollutants: [
        { pollutantType: 'PM2_5', value: round1(this.currentPollutants.PM2_5) },
        { pollutantType: 'PM10', value: round1(this.currentPollutants.PM10) },
        { pollutantType: 'NO2', value: round1(this.currentPollutants.NO2) },
        { pollutantType: 'O3', value: round1(this.currentPollutants.O3) }
      ]
    };
  }

  sendRandomMeasurement() {

    if (!this.userEmail) return;

    const email = this.userEmail;
    const measurement = this.generateMeasurementWithDrift();
    const body = { email, measurement };

    this.airQualityService.evaluate(body).subscribe({
      next: (res) => {
        this.airQualityInfo = res;
        console.log('Updated AirQualityInfo:', res);
      },
      error: (err) => console.log('Error sending measurement:', err)
    });
  }

  startSimulation() {
    this.sendRandomMeasurement();
    this.simulationInterval = setInterval(() => {
      this.sendRandomMeasurement();
    }, 10_000);
  }

  get pollutants(): PollutantResponse[] {
    return this.airQualityInfo?.input?.pollutantMeasurment?.pollutants || [];
  }

  get alerts(): string[] {
    return this.airQualityInfo?.warnings || [];
  }

  get airCategory(): string {
    return this.airQualityInfo?.airCategory || 'N/A';
  }

  get recommendation(): string {
    return this.airQualityInfo?.recommendation?.content || '';
  }

  getAirQualityColor(category: string | undefined): string {
  switch (category) {
    case 'GOOD':
      return 'green';
    case 'MODERATE':
      return 'yellow';
    case 'POOR':
      return 'orange';
    case 'VERY_POOR':
      return 'red';
    case 'HAZARDOUS':
      return 'purple';
    default:
      return 'gray';
  }
}

  loadLast24hData() {
  this.airQualityService.getLast24hPollutants().subscribe({
    next: (data: PollutantHistoryDTO[]) => {
      console.log(data);
      const labels = data.map(d => new Date(d.timestamp).toLocaleTimeString());
      const pm25Data = data.map(d => d.pm25);
      const pm10Data = data.map(d => d.pm10);

      // Kreiraj novi objekat da Chart.js vidi promenu
      this.lineChartData = {
        labels: labels,
        datasets: [
          { data: pm25Data, label: 'PM2.5', borderColor: 'blue', fill: false },
          { data: pm10Data, label: 'PM10', borderColor: 'red', fill: false }
        ]
      };
    },
    error: (err) => console.error('Error loading last 24h data', err)
  });
}


}
