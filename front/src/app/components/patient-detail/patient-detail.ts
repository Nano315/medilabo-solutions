import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { PatientService } from '../../services/patient.service';
import { Patient } from '../../models/patient.model';

@Component({
  selector: 'app-patient-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './patient-detail.html',
  styleUrl: './patient-detail.css'
})
export class PatientDetailComponent implements OnInit {
  patient = signal<Patient | null>(null);
  loading = signal(true);
  error = signal('');

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.patientService.getPatientById(id).subscribe({
        next: (data) => {
          this.patient.set(data);
          this.loading.set(false);
        },
        error: (err) => {
          console.error('PatientDetailComponent: Error getting patient', err);
          this.error.set('Erreur lors du chargement des informations du patient.');
          this.loading.set(false);
        }
      });
    } else {
      this.error.set('ID de patient invalide.');
      this.loading.set(false);
    }
  }

  goBack(): void {
    this.router.navigate(['/patients']);
  }
}
