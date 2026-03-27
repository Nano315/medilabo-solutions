import { Routes } from '@angular/router';
import { PatientListComponent } from './components/patient-list/patient-list';
import { PatientDetailComponent } from './components/patient-detail/patient-detail';
import { PatientFormComponent } from './components/patient-form/patient-form';

export const routes: Routes = [
  { path: 'patients', component: PatientListComponent },
  { path: 'patients/add', component: PatientFormComponent },
  { path: 'patients/edit/:id', component: PatientFormComponent },
  { path: 'patients/:id', component: PatientDetailComponent },
  { path: '', redirectTo: '/patients', pathMatch: 'full' }
];
