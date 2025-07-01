import {Component} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  imports: [
    FormsModule
  ],
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  user: any = {};

  constructor(private readonly authService: AuthService, private readonly router: Router) {
  }

  register() {
    this.authService.login(this.user).subscribe({
      next: data => { this.router.navigate(['/login']); },
      error: error => { console.error('Registration error: ', error); },
      complete() { console.log('success'); }
    })
  }
}
