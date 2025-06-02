import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { UserListComponent } from './user-list.component';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let userService: UserService;
  let mockUsers: User[];

  beforeEach(async () => {
    mockUsers = [
      { id: '1', name: 'John Doe', email: 'john@example.com' },
      { id: '2', name: 'Jane Smith', email: 'jane@example.com' }
    ];

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, UserListComponent],
      providers: [UserService]
    }).compileComponents();

    userService = TestBed.inject(UserService);
    spyOn(userService, 'getUsers').and.returnValue(of(mockUsers));

    fixture = TestBed.createComponent(UserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load users on init', () => {
    expect(userService.getUsers).toHaveBeenCalled();
    expect(component.users).toEqual(mockUsers);
  });

  it('should display users in the table', () => {
    const compiled = fixture.nativeElement;
    const rows = compiled.querySelectorAll('tbody tr');

    expect(rows.length).toBe(2);
    expect(rows[0].cells[0].textContent).toContain('1');
    expect(rows[0].cells[1].textContent).toContain('John Doe');
    expect(rows[0].cells[2].textContent).toContain('john@example.com');

    expect(rows[1].cells[0].textContent).toContain('2');
    expect(rows[1].cells[1].textContent).toContain('Jane Smith');
    expect(rows[1].cells[2].textContent).toContain('jane@example.com');
  });

  it('should show message when no users are found', () => {
    component.users = [];
    fixture.detectChanges();

    const compiled = fixture.nativeElement;
    const alert = compiled.querySelector('.alert');

    expect(alert).toBeTruthy();
    expect(alert.textContent).toContain('No users found');
  });
});
