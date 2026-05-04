import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Assessment } from '../models/assessment.model';

@Injectable({
  providedIn: 'root'
})
export class AssessmentService {

  private apiUrl = 'http://localhost:8081/api/assessment';

  private headers = new HttpHeaders({
    'Authorization': 'Basic YWRtaW46YWRtaW4='
  });

  constructor(private http: HttpClient) {}

  getPatientRisk(patId: number): Observable<Assessment> {
    return this.http.get<Assessment>(`${this.apiUrl}/${patId}`, { headers: this.headers });
  }
}
