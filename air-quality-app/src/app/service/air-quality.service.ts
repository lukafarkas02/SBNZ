import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AirQualityInfoResponse } from '../models/air-quality-info-response.model';
import { AirQualityRequestDTO } from '../models/air-quality-request-dto.model';
import { PollutantHistoryDTO } from '../models/pollutant-history-dto.model';
import { PollutantData } from '../models/pollutant-data.model';

@Injectable({
  providedIn: 'root'
})
export class AirQualityService {

  private baseUrl = 'http://localhost:8090/air-quality';

  constructor(private http: HttpClient) { }

  evaluate(input: AirQualityRequestDTO): Observable<AirQualityInfoResponse> {
    return this.http.post<AirQualityInfoResponse>(`${this.baseUrl}/evaluate`, input);
  }

  private apiUrl = 'http://localhost:8090/api/air';

  getLast24hPollutants(): Observable<PollutantHistoryDTO[]> {
    return this.http.get<PollutantHistoryDTO[]>(`${this.apiUrl}/pollutants/last24h`);
  }

  sendPollutantData(data: PollutantData): Observable<any> {
    return this.http.post(`${this.apiUrl}/analyzeLongTerm`, data);
  }
}
