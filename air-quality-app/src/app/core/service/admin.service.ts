import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8090/api/admin';

  constructor(private http: HttpClient) {}

  getAllMeasurements(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/measurements`);
  }

  getAllWarnings(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/warnings`);
  }
}
