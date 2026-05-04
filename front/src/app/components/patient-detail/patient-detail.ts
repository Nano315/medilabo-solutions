import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PatientService } from '../../services/patient.service';
import { Patient } from '../../models/patient.model';
import { NoteService } from '../../services/note.service';
import { Note } from '../../models/note.model';
import { AssessmentService } from '../../services/assessment.service';
import { Assessment } from '../../models/assessment.model';

@Component({
  selector: 'app-patient-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './patient-detail.html',
  styleUrl: './patient-detail.css'
})
export class PatientDetailComponent implements OnInit {
  patient = signal<Patient | null>(null);
  loading = signal(true);
  error = signal('');
  notes = signal<Note[]>([]);
  newNoteContent = signal('');
  assessment = signal<Assessment | null>(null);

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private patientService: PatientService,
    private noteService: NoteService,
    private assessmentService: AssessmentService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = Number(idParam);
      this.patientService.getPatientById(id).subscribe({
        next: (data) => {
          this.patient.set(data);
          this.loading.set(false);
          this.loadNotes(id);
          this.loadAssessment(id);
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

  loadNotes(patId: number): void {
    this.noteService.getPatientNotes(patId).subscribe({
      next: (data) => this.notes.set(data),
      error: (err) => console.error('PatientDetailComponent: Error getting notes', err)
    });
  }

  loadAssessment(patId: number): void {
    this.assessmentService.getPatientRisk(patId).subscribe({
      next: (data) => this.assessment.set(data),
      error: (err) => console.error('PatientDetailComponent: Error getting assessment', err)
    });
  }

  getRiskClass(risk: string | undefined): string {
    if (!risk) return '';
    switch (risk.toUpperCase()) {
      case 'NONE': return 'risk-none';
      case 'BORDERLINE': return 'risk-borderline';
      case 'IN DANGER': return 'risk-danger';
      case 'EARLY ONSET': return 'risk-early-onset';
      default: return '';
    }
  }

  saveNote(): void {
    const currentPatient = this.patient();
    const content = this.newNoteContent().trim();
    if (currentPatient && currentPatient.id && content) {
      const newNote: Note = {
        patId: currentPatient.id,
        patient: `${currentPatient.prenom} ${currentPatient.nom}`,
        note: content
      };
      
      this.noteService.addNote(newNote).subscribe({
        next: () => {
          this.newNoteContent.set('');
          this.loadNotes(currentPatient.id!);
        },
        error: (err) => console.error('PatientDetailComponent: Error adding note', err)
      });
    }
  }

  goBack(): void {
    this.router.navigate(['/patients']);
  }
}
