import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PatientService } from '../../services/patient.service';
import { Patient } from '../../models/patient.model';

@Component({
  selector: 'app-patient-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './patient-list.html',
  styleUrl: './patient-list.css'
})
export class PatientListComponent implements OnInit {
  patients = signal<Patient[]>([]);
  loading = signal(true);
  error = signal('');

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    console.log('PatientListComponent: ngOnInit called');
    this.patientService.getPatients().subscribe({
      next: (data) => {
        console.log('PatientListComponent: Patients received', data);
        this.patients.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('PatientListComponent: Error receiving patients', err);
        this.error.set('Erreur lors du chargement des patients.');
        this.loading.set(false);
      }
    });
  }
}
