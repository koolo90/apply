import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink]
})
export class UserFormComponent implements OnInit {
  user: User = new User('', '');
  isEditMode = false;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Check if we're in edit mode by looking for an id parameter
    this.route.paramMap.subscribe(params => {
      const userId = params.get('id');
      if (userId) {
        this.isEditMode = true;
        this.loadUser(userId);
      }
    });
  }

  loadUser(id: string): void {
    this.userService.getUser(id).subscribe(
      user => {
        this.user = user;
      },
      error => {
        console.error('Error loading user:', error);
      }
    );
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.userService.updateUser(this.user).subscribe(
        result => {
          this.router.navigate(['/users']);
        },
        error => {
          console.error('Error updating user:', error);
        }
      );
    } else {
      this.userService.createUser(this.user).subscribe(
        result => {
          this.router.navigate(['/users']);
        },
        error => {
          console.error('Error creating user:', error);
        }
      );
    }
  }
}
