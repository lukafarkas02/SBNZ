import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage = '';

  constructor(
    private fb: FormBuilder, 
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    const formValue = this.loginForm.value;
    const payload: any = {
      email: formValue.email,
      password: formValue.password
    };

    console.log(payload);

    this.authService.login(payload).subscribe({
      next: (res) => {
        console.log('Uspešna prijava na sistem', res);
        alert('Prijava uspesna!');
        this.loginForm.reset();

        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Greška pri prijavi', err);
        this.errorMessage = 'Došlo je do greške prilikom prijave.';
      }
    });
  }
}
