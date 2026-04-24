import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from '../models/patient.model';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private apiUrl = 'http://localhost:8081/api/patients';

  private headers = new HttpHeaders({
    'Authorization': 'Basic YWRtaW46YWRtaW4='
  });

  constructor(private http: HttpClient) {}

  getPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.apiUrl, { headers: this.headers });
  }

  getPatientById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.apiUrl}/${id}`, { headers: this.headers });
  }

  addPatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.apiUrl, patient, { headers: this.headers });
  }

  updatePatient(id: number, patient: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.apiUrl}/${id}`, patient, { headers: this.headers });
  }

  getPatientRisk(patId: number): Observable<any> {
    return this.http.get<any>(`http://localhost:8081/api/assessment/${patId}`, { headers: this.headers });
  }
}
