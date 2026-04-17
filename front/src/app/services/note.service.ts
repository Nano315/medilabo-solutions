import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Note } from '../models/note.model';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private apiUrl = 'http://localhost:8081/api/notes';

  private headers = new HttpHeaders({
    'Authorization': 'Basic YWRtaW46YWRtaW4='
  });

  constructor(private http: HttpClient) {}

  getPatientNotes(patId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/patient/${patId}`, { headers: this.headers });
  }

  addNote(note: Note): Observable<Note> {
    return this.http.post<Note>(this.apiUrl, note, { headers: this.headers });
  }
}
