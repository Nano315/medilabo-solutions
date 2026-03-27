import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PatientService } from '../../services/patient.service';
import { Patient } from '../../models/patient.model';

@Component({
  selector: 'app-patient-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './patient-form.html',
  styleUrls: ['./patient-form.css']
})
export class PatientFormComponent implements OnInit {
  patientForm!: FormGroup;
  isEditMode = false;
  patientId: number | null = null;
  loading = false;
  error = '';

  private fb = inject(FormBuilder);
  private patientService = inject(PatientService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  ngOnInit(): void {
    this.initForm();
    this.checkEditMode();
  }

  private initForm(): void {
    this.patientForm = this.fb.group({
      prenom: ['', Validators.required],
      nom: ['', Validators.required],
      dateNaissance: ['', Validators.required],
      genre: ['', Validators.required],
      adressePostale: [''],
      telephone: ['']
    });
  }

  private checkEditMode(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.patientId = +idParam;
      this.loadPatientData(this.patientId);
    }
  }

  private loadPatientData(id: number): void {
    this.loading = true;
    this.patientService.getPatientById(id).subscribe({
      next: (patient) => {
        this.patientForm.patchValue({
          prenom: patient.prenom,
          nom: patient.nom,
          dateNaissance: patient.dateNaissance,
          genre: patient.genre,
          adressePostale: patient.adressePostale,
          telephone: patient.telephone
        });
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement du patient', err);
        this.error = 'Impossible de charger les données du patient';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.patientForm.invalid) {
      return;
    }

    this.loading = true;
    const patientData: Patient = this.patientForm.value;

    if (this.isEditMode && this.patientId) {
      this.patientService.updatePatient(this.patientId, patientData).subscribe({
        next: () => this.navigateBack(),
        error: this.handleError.bind(this)
      });
    } else {
      this.patientService.addPatient(patientData).subscribe({
        next: () => this.navigateBack(),
        error: this.handleError.bind(this)
      });
    }
  }

  onCancel(): void {
    this.navigateBack();
  }

  private navigateBack(): void {
    this.router.navigate(['/patients']);
  }

  private handleError(err: any): void {
    console.error('Erreur lors de la sauvegarde', err);
    this.error = 'Une erreur est survenue lors de l\'enregistrement';
    this.loading = false;
  }
}
