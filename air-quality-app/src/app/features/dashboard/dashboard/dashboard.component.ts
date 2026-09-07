import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';
import { ChartData, ChartOptions } from 'chart.js';
import { Subscription } from 'rxjs';

import { NavigationComponent } from '../../../layout/navigation/navigation/navigation.component';
import { AirQualityService } from '../../../core/service/air-quality.service';
import { AirQualityInfoResponse } from '../../../core/models/air-quality-info-response.model';
import { AirQualityEvaluationResponse } from '../../../core/models/AirQuilityEvaluationResponse';
import { PollutantResponse } from '../../../core/models/pollutant-response.model';
import { AirQualityRequestDTO } from '../../../core/models/air-quality-request-dto.model';
import { SimulationStateService, SimulationData } from '../../../core/service/simulator-state.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NavigationComponent, NgChartsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  userFirstName: string | null = '';
  userEmail: string | null = '';

  evaluationResponse: AirQualityEvaluationResponse | null = null;
  airQualityInfo: AirQualityInfoResponse | null = null;

  public lineChartData: ChartData<'line'> = {
    labels: [],
    datasets: [
      { data: [], label: 'PM2.5', borderColor: 'blue', fill: false },
      { data: [], label: 'PM10', borderColor: 'red', fill: false }
    ]
  };

  public lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
    plugins: { legend: { display: true } },
    scales: {
      x: { title: { display: true, text: 'Time' } },
      y: { title: { display: true, text: 'μg/m³' } }
    }
  };

  public lineChartType: 'line' = 'line';

  private simulationSub!: Subscription;

  private currentWeather = {
    temperature: 20.0,
    humidity: 65.0,
    windSpeed: 3.5,
    precipitation: 0.0,
    pressure: 1013.0,
    weatherImpact: 'Stabilni vremenski uslovi.'
  };

  constructor(
    private airQualityService: AirQualityService,
    private simulationState: SimulationStateService
  ) {}

  ngOnInit(): void {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        const userObj = JSON.parse(userStr);
        this.userFirstName = userObj.firstName || '';
        this.userEmail = userObj.email || '';
      }
    }

    this.simulationSub = this.simulationState.currentSimulation$.subscribe(data => {
      this.sendMeasurementToBackend(data);
    });
  }

  ngOnDestroy(): void {
    if (this.simulationSub) {
      this.simulationSub.unsubscribe();
    }
  }

  sendMeasurementToBackend(simData: SimulationData): void {
    if (!this.userEmail) {
      return;
    }

    // Синхронизујемо локални приказ времена са подацима из сервиса
    this.currentWeather.temperature = simData.temperature;
    this.currentWeather.humidity = simData.humidity;
    this.currentWeather.windSpeed = simData.windSpeed;

    const body: AirQualityRequestDTO = {
      email: this.userEmail,
      measurement: {
        pollutants: [
          { pollutantType: 'PM2_5', value: simData.pm25 },
          { pollutantType: 'PM10', value: simData.pm10 },
          { pollutantType: 'NO2', value: simData.no2 },
          { pollutantType: 'O3', value: simData.o3 },
          { pollutantType: 'CO2', value: simData.co2 }
        ]
      },
      weather: {
        temperature: simData.temperature,
        humidity: simData.humidity,
        windSpeed: simData.windSpeed,
        precipitation: simData.precipitation ? 1.0 : 0.0,
        pressure: simData.pressure
      }
    };

    this.airQualityService.evaluate(body).subscribe({
      next: (response: any) => {
        this.evaluationResponse = response;
        this.airQualityInfo = response.airQualityInfo;
      },
      error: error => {
        console.error('Error sending measurement:', error);
      }
    });
  }

  get pollutants(): PollutantResponse[] {
    return this.airQualityInfo?.input?.pollutantMeasurment?.pollutants ?? [];
  }

  get airCategory(): string {
    return this.airQualityInfo?.airCategory ?? 'N/A';
  }

  getAirQualityColor(category?: string): string {
    switch (category) {
      case 'GOOD': return '#2e7d32';       // dark green
      case 'MODERATE': return '#9ccc65';   // light green (svetlo zelena, umesto žute)
      case 'POOR': return '#f9a825';       // orange/amber
      case 'VERY_POOR': return '#e53935';  // red
      case 'HAZARDOUS': return '#7b1fa2';  // purple
      default: return '#9e9e9e';           // gray
    }
  }

  get weatherConditions() {
    return {
      ...this.currentWeather,
      ...(this.evaluationResponse?.weatherConditions || {})
    };
  }

  get outdoorActivityMessages(): string[] {
    const plan = this.evaluationResponse?.outdoorActivityPlan;
    if (!plan) return [];
    return [plan.suggestedAlternative, plan.note].filter((msg): msg is string => Boolean(msg));
  }

  get healthMessages(): string[] {
    const reminder = this.evaluationResponse?.healthCheckReminder;
    if (!reminder) return [];
    const messages = [reminder.message];
    if (reminder.urgent) {
      messages.push('Urgent health warning.');
    }
    return messages;
  }

  get institutionMessages(): string[] {
    const command = this.evaluationResponse?.smartHomeCommand;
    if (!command) return [];
    return [];
  }

  get hasMessages(): boolean {
    return (
      this.outdoorActivityMessages.length > 0 ||
      this.healthMessages.length > 0 ||
      this.institutionMessages.length > 0
    );
  }
}
