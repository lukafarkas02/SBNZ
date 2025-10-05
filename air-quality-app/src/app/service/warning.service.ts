import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Warning } from '../models/warning.model';

@Injectable({
  providedIn: 'root'
})
export class WarningService {

  private baseUrl = 'http://localhost:8090/api/warnings';

  constructor(private http: HttpClient) { }

  getWarningsByEmail(email: string): Observable<Warning[]> {
    return this.http.get<Warning[]>(`${this.baseUrl}/${email}`);
  }
}
