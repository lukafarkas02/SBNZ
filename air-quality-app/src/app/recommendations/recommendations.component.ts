import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NavigationComponent } from '../navigation/navigation/navigation.component';
import { BackwardReasoningResult } from '../models/backward-reasoning-result.model';
import { MaskRecommendationResponse } from '../models/mask-recommendation-response.model';
import { BackwardAnalysisService } from '../service/backward-analysis.service';
import { BackwardQualityRequest } from '../models/backward-quality.model';

interface Pollutants {
  pm25: number;
  pm10: number;
  no2: number;
  o3: number;
  windSpeed: number;
  precipitation: boolean;
}

@Component({
  selector: 'app-recommendations',
  imports: [CommonModule, NavigationComponent],
  templateUrl: './recommendations.component.html',
  styleUrl: './recommendations.component.css'
})
export class RecommendationsComponent implements OnInit{
  pollutants: Pollutants = {
    pm25: 60,
    pm10: 110,
    no2: 210,
    o3: 190,
    windSpeed: 1.0,
    precipitation: false
  };

  safetyResult: BackwardReasoningResult | null = null;
  maskResult: MaskRecommendationResponse | null = null;

  userEmail: string | null = '';

  constructor(private backwardService: BackwardAnalysisService) {}

  ngOnInit(): void {
    if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        const userObj = JSON.parse(userStr);
        this.userEmail = userObj.email || '';
        console.log("Korisnik:", userObj.email);
      } else {
        console.log("Nema korisnika u localStorage");
      }
    } else {
      console.warn("localStorage nije dostupan u ovom okruženju");
    }
  }

  private randomizePollutants(): void {
    this.pollutants = {
      pm25: this.randomInRange(36, 80),     // uvek veće od 35
      pm10: this.randomInRange(55, 150),    // uvek veće od 50
      no2: this.randomInRange(110, 250),    // uvek veće od 100
      o3: this.randomInRange(150, 220),     // uvek veće od 140
      windSpeed: this.randomInRange(1.0, 2.0),
      precipitation: Math.random() < 0.5    // true/false nasumično
    };
  }

  private randomInRange(min: number, max: number): number {
    return Math.round((Math.random() * (max - min) + min) * 10) / 10;
  }

  checkSafety() {
    const req: BackwardQualityRequest = {
      ...this.pollutants,
      userEmail: this.userEmail!
    };

    this.backwardService.evaluate(req).subscribe({
      next: (res) => {
        this.safetyResult = res;
        console.log('Safety response:', res);
      },
      error: (err) => console.error('Error evaluating safety:', err)
    });

    this.randomizePollutants();
  }

  checkMask() {
     const req: BackwardQualityRequest = {
      ...this.pollutants,
      userEmail: this.userEmail!
    };

    this.backwardService.mask(req).subscribe({
      next: (res) => {
        this.maskResult = res;
        console.log('Mask response:', res);
      },
      error: (err) => console.error('Error getting mask recommendation:', err)
    });

      this.randomizePollutants();
  }

}
