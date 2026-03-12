import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientService } from './services/patient.service';
import { Patient } from './models/patient.model';

@Component({
  selector: 'app-root',
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  patients = signal<Patient[]>([]);
  loading = signal(true);
  error = signal('');

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    console.log('App: ngOnInit called (Zoneless mode)');
    this.patientService.getPatients().subscribe({
      next: (data) => {
        console.log('App: Patients received', data);
        this.patients.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('App: Error receiving patients', err);
        this.error.set('Erreur lors du chargement des patients.');
        this.loading.set(false);
      }
    });
  }
}
