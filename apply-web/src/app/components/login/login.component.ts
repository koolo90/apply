import {Component} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [
    FormsModule
  ],
  styleUrl: './login.component.css'
})
export class LoginComponent {
  credentials: any;

  constructor(private readonly authService: AuthService, private readonly router: Router) {
  }

  login() {
    this.authService.login(this.credentials).subscribe({
      next: data => { this.router.navigate(['/login']); },
      error: error => { console.error('Login error: ', error); },
      complete() { console.log('success'); }
    })
  }
}
