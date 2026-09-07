// template.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PollutantValue {
  pollutantType: string;
  value: number;
}

export interface PollutantClassificationResult {
  pollutantType: string;
  value: number;
  status: string;
}

export interface ThresholdRow {
  pollutantType?: string;
  category: string;
  minValue: number;
  maxValue: number;
}

export interface SaveThresholdsResponse {
  success: boolean;
  pollutantType: string;
  ruleCount: number;
  totalGeneratedRules: number;
}

@Injectable({ providedIn: 'root' })
export class TemplateService {

  private baseUrl = 'http://localhost:8090/api/template';

  constructor(private http: HttpClient) {}

  getPollutantTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/pollutant-types`);
  }

  getAllThresholds(): Observable<ThresholdRow[]> {
    return this.http.get<ThresholdRow[]>(`${this.baseUrl}/thresholds`);
  }

  getThresholdsForPollutant(pollutantType: string): Observable<ThresholdRow[]> {
    return this.http.get<ThresholdRow[]>(`${this.baseUrl}/thresholds/${pollutantType}`);
  }

  saveThresholds(pollutantType: string, rules: ThresholdRow[]): Observable<SaveThresholdsResponse> {
    return this.http.post<SaveThresholdsResponse>(`${this.baseUrl}/thresholds/${pollutantType}`, rules);
  }

  classify(pollutants: PollutantValue[]): Observable<PollutantClassificationResult[]> {
    return this.http.post<PollutantClassificationResult[]>(`${this.baseUrl}/classify`, pollutants);
  }

  getGeneratedRules(): Observable<string> {
    return this.http.get(`${this.baseUrl}/generated-rules`, { responseType: 'text' });
  }
}
