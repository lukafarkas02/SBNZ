// warnings-page.component.ts
import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import {NavigationComponent} from '../../../layout/navigation/navigation/navigation.component';
import {LiveAlertsFeedComponent, Warning} from '../cep-alerts/live-alerts-feed/live-alerts-feed.component';
import {SimulationData, SimulationStateService} from '../../../core/service/simulator-state.service';
import {CepMeasurementRequest, CepService} from '../../../core/service/cep.service';


@Component({
  selector: 'app-warnings',
  standalone: true,
  imports: [CommonModule, NavigationComponent, LiveAlertsFeedComponent],
  templateUrl: './warnings.component.html',
  styleUrls: ['./warnings.component.css']
})
export class WarningsComponent implements OnInit, OnDestroy {

  userEmail: string | null = '';
  latestAlerts: Warning[] | null = null;
  currentReading: SimulationData | null = null;

  private simulationSub!: Subscription;

  demoScenarios: { key: 'PM25_SPIKE' | 'CORRELATED_SURGE' | 'SUSTAINED_HAZARDOUS' | 'WIND_DROP' | 'RECOVERY' | 'STABLE'; label: string; icon: string }[] = [
    { key: 'PM25_SPIKE', label: 'PM2.5 Spike', icon: '📈' },
    { key: 'CORRELATED_SURGE', label: 'Correlated Surge', icon: '🚦' },
    { key: 'SUSTAINED_HAZARDOUS', label: 'Sustained Hazard', icon: '☠️' },
    { key: 'WIND_DROP', label: 'Wind Drop', icon: '⏳' },
    { key: 'RECOVERY', label: 'Air Recovering', icon: '🌤️' },
    { key: 'STABLE', label: 'Stabilize', icon: 'ℹ️' }
  ];

  constructor(
    private cepService: CepService,
    private simulationState: SimulationStateService
  ) {}

  ngOnInit(): void {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        const userObj = JSON.parse(userStr);
        this.userEmail = userObj.email || '';
      }
    }

    this.simulationSub = this.simulationState.currentSimulation$.subscribe(data => {
      this.currentReading = data;
      this.sendToCep(data);
    });
  }

  ngOnDestroy(): void {
    if (this.simulationSub) {
      this.simulationSub.unsubscribe();
    }
  }

  runScenario(key: 'PM25_SPIKE' | 'CORRELATED_SURGE' | 'SUSTAINED_HAZARDOUS' | 'WIND_DROP' | 'RECOVERY' | 'STABLE'): void {
    this.simulationState.triggerScenario(key);
  }

  private sendToCep(data: SimulationData): void {
    if (!this.userEmail) {
      return;
    }

    const req: CepMeasurementRequest = {
      email: this.userEmail,
      pm25: data.pm25,
      pm10: data.pm10,
      no2: data.no2,
      o3: data.o3,
      co2: data.co2,
      windSpeed: data.windSpeed,
      humidity: data.humidity,
      temperature: data.temperature,
      pressure: data.pressure,
      precipitation: data.precipitation
    };

    this.cepService.streamMeasurement(req).subscribe({
      next: (warnings: Warning[]) => {
        if (warnings && warnings.length > 0) {
          this.latestAlerts = warnings;
        }
      },
      error: err => console.error('CEP stream error:', err)
    });
  }
}
