import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm!: FormGroup;
  submitted = false;
  errorMessage = '';

  constructor(private fb: FormBuilder, private authService: AuthService) {
    // this.registerForm = this.fb.group({
    //   firstName: ['', Validators.required],
    //   lastName: ['', Validators.required],
    //   email: ['', [Validators.required, Validators.email]],
    //   password: ['', Validators.required],
    //   confirmPassword: ['', Validators.required],

    //   // novo dodata polja
    //   userType: ['', Validators.required],
    //   riskType: [''],               // samo ako je rizična grupa
    //   institutionName: [''],        // samo ako je institucija
    //   institutionType: [''],        // samo ako je institucija
    //   institutionAddress: [''],     // samo ako je institucija
    //   location: ['', Validators.required]
    // });
  }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      userType: ['', Validators.required],
      riskType: [''],
      institutionName: [''],
      institutionType: [''],
      institutionAddress: [''],
      location: ['', Validators.required]
    });
  }


  // onSubmit() {
  //   if (this.registerForm.valid) {
  //     console.log('Register data:', this.registerForm.value);
  //     // ovde pozovi backend API za registraciju
  //   }
  // }

  onSubmit(): void {
    this.submitted = true;

    if (this.registerForm.invalid) return;

    // Pravimo payload prema tipu korisnika
    const formValue = this.registerForm.value;
    const payload: any = {
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      email: formValue.email,
      password: formValue.password,
      confirmPassword: formValue.confirmPassword,
      userType: formValue.userType,
      location: formValue.location
    };

    if (formValue.userType === 'risk_group') {
      payload.riskType = formValue.riskType;
    } else if (formValue.userType === 'institution') {
      payload.institutionName = formValue.institutionName;
      payload.institutionType = formValue.institutionType;
      payload.institutionAddress = formValue.institutionAddress;
    }

    console.log(payload);
    
    this.authService.register(payload).subscribe({
      next: (res) => {
        console.log('Uspešna registracija', res);
        alert('Registracija uspešna!');
        this.registerForm.reset();
      },
      error: (err) => {
        console.error('Greška pri registraciji', err);
        this.errorMessage = 'Došlo je do greške prilikom registracije.';
      }
    });
  }
}
