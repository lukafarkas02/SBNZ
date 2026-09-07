import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { NavigationComponent } from '../../../layout/navigation/navigation/navigation.component';
import { BackwardReasoningResult } from '../../../core/models/backward-reasoning-result.model';
import { MaskRecommendationResponse } from '../../../core/models/mask-recommendation-response.model';
import { BackwardAnalysisService } from '../../../core/service/backward-analysis.service';
import { BackwardQualityRequest } from '../../../core/models/backward-quality.model';
import { SimulationStateService } from '../../../core/service/simulator-state.service';
import {ActivityDiagnosisResponse} from '../../../core/models/activity-diagnosis-response';
import {BackwardActivityRequest} from '../../../core/models/backward-activity-request';
import { FormsModule } from '@angular/forms';

interface Pollutants {
  pm25: number;
  pm10: number;
  no2: number;
  o3: number;
  windSpeed: number;
  humidity: number;
  temperature: number;
  precipitation: boolean;
}

@Component({
  selector: 'app-recommendations',
  standalone: true,
  imports: [CommonModule, NavigationComponent, FormsModule],
  templateUrl: './recommendations.component.html',
  styleUrl: './recommendations.component.css'
})
export class RecommendationsComponent implements OnInit, OnDestroy {
  pollutants: Pollutants = {
    pm25: 60,
    pm10: 110,
    no2: 210,
    o3: 190,
    windSpeed: 1.0,
    humidity: 65,
    temperature: 20,
    precipitation: false
  };

  safetyResult: BackwardReasoningResult | null = null;
  maskResult: MaskRecommendationResponse | null = null;

  userEmail: string | null = '';
  private simulationSub!: Subscription;

  selectedActivity = 'RUNNING';

  durationMinutes = 30;

  activityResult: ActivityDiagnosisResponse | null = null;

  constructor(
    private backwardService: BackwardAnalysisService,
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

    // 1) Узимамо тренутне вредности из глобалног симулатора у тренутку отварања стране
    this.pollutants = this.simulationState.getCurrentValues();

    // 2) Претплатимо се на промене тако да се табела аутоматски освежава у реалном времену
    this.simulationSub = this.simulationState.currentSimulation$.subscribe(data => {
      this.pollutants = data;
    });
  }

  ngOnDestroy(): void {
    if (this.simulationSub) {
      this.simulationSub.unsubscribe();
    }
  }

  // checkSafety() {
  //   const req: BackwardQualityRequest = {
  //     ...this.pollutants,
  //     userEmail: this.userEmail!
  //   };
  //
  //   this.backwardService.evaluate(req).subscribe({
  //     next: (res) => {
  //       this.safetyResult = res;
  //       console.log('Safety response:', res);
  //     },
  //     error: (err) => console.error('Error evaluating safety:', err)
  //   });
  // }

  diagnoseActivity() {
    const req: BackwardActivityRequest = {
      userEmail: this.userEmail!,
      activityType: this.selectedActivity,
      durationMinutes: this.durationMinutes,
      windSpeed: this.pollutants.windSpeed,
      humidity: this.pollutants.humidity,
      temperature: this.pollutants.temperature
    };

    this.backwardService.activity(req).subscribe({
      next: res => {
        this.activityResult = res;},
      error: err => console.error(err)
    });

  }
}
