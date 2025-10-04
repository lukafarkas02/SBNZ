import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8090/api/auth';

  constructor(private http: HttpClient) { }

  register(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, userData);
  }

  // login(loginData: any): Observable<any> {
  //   return this.http.post(`${this.baseUrl}/login`, loginData);
  // }

   login(loginData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/login`, loginData).pipe(
      tap((userData: any) => {
        // Čuvamo podatke (npr. token i email) u localStorage
        localStorage.setItem('user', JSON.stringify(userData));
      })
    );
  }

  getUser(): any {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('user');
  }

  logout(): void {
    localStorage.removeItem('user');
  }
}
