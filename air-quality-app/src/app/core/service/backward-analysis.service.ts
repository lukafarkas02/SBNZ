import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  BackwardQualityRequest,
} from '../models/backward-quality.model';
import { BackwardReasoningResult } from '../models/backward-reasoning-result.model';
import { MaskRecommendationResponse } from '../models/mask-recommendation-response.model';
import {ActivityDiagnosisResponse} from '../models/activity-diagnosis-response';
import {BackwardActivityRequest} from '../models/backward-activity-request';

@Injectable({
  providedIn: 'root'
})
export class BackwardAnalysisService {

  private baseUrl = 'http://localhost:8090/api/backward'; // prilagodi ako koristiš drugačiji port/backend URL

  constructor(private http: HttpClient) {}

  evaluate(request: BackwardQualityRequest): Observable<BackwardReasoningResult> {
    return this.http.post<BackwardReasoningResult>(`${this.baseUrl}/evaluate`, request);
  }

  mask(request: BackwardQualityRequest): Observable<MaskRecommendationResponse> {
    return this.http.post<MaskRecommendationResponse>(`${this.baseUrl}/mask`, request);
  }

  activity(req: BackwardActivityRequest) {
    return this.http.post<ActivityDiagnosisResponse>(
      `${this.baseUrl}/activity`, req);

  }
}
