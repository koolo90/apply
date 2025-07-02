import {Component} from '@angular/core';
import {AuthService} from '../../services/auth.service';
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
  credentials: any = {};

  constructor(private readonly authService: AuthService) {
  }

  login() {
    this.authService.login(this.credentials);
  }
}
