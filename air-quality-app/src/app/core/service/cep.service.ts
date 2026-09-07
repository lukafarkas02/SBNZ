// cep.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CepMeasurementRequest {
  email: string;
  pm25: number;
  pm10: number;
  no2: number;
  o3: number;
  co2: number;
  windSpeed: number;
  humidity: number;
  temperature: number;
  pressure: number;
  precipitation: boolean;
}

export interface Warning {
  type: string;
  content: string;
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class CepService {

  private baseUrl = 'http://localhost:8090/cep';

  constructor(private http: HttpClient) {}

  streamMeasurement(req: CepMeasurementRequest): Observable<Warning[]> {
    return this.http.post<Warning[]>(`${this.baseUrl}/stream-measurement`, req);
  }
}
