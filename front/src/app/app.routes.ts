import { Routes } from '@angular/router';
import { PatientListComponent } from './components/patient-list/patient-list';
import { PatientDetailComponent } from './components/patient-detail/patient-detail';

export const routes: Routes = [
  { path: 'patients', component: PatientListComponent },
  { path: 'patients/:id', component: PatientDetailComponent },
  { path: '', redirectTo: '/patients', pathMatch: 'full' }
];
