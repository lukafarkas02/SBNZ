import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { DashboardComponent } from './features/dashboard/dashboard/dashboard.component';
import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { WarningsComponent } from './features/users/warnings/warnings.component';
import { RecommendationsComponent } from './features/users/recommendations/recommendations.component';
import {TemplatePageComponent} from './features/admin/template/template-page/template-page.component';
import {AdminMeasurementsComponent} from './features/admin/admin-measurements/admin-measurements.component';
import {AdminWarningsComponent} from './features/admin/admin-warnings/admin-warnings.component';

export const routes: Routes = [
    { path: '', component: RegisterComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'warnings', component: WarningsComponent },
    { path: 'recommendations', component: RecommendationsComponent },
  { path: 'template', component: TemplatePageComponent },
  { path: 'admin-measurements', component: AdminMeasurementsComponent },
  { path: 'admin-warnings', component: AdminWarningsComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
