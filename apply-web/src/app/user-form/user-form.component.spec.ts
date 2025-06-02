import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';
import { UserFormComponent } from './user-form.component';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

describe('UserFormComponent', () => {
  let component: UserFormComponent;
  let fixture: ComponentFixture<UserFormComponent>;
  let userServiceSpy: jasmine.SpyObj<UserService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const userServiceMock = jasmine.createSpyObj('UserService', ['getUser', 'createUser', 'updateUser']);
    const routerMock = jasmine.createSpyObj('Router', ['navigate']);
    const activatedRouteMock = {
      paramMap: of(convertToParamMap({}))
    };

    await TestBed.configureTestingModule({
      imports: [FormsModule, UserFormComponent],
      providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    }).compileComponents();

    userServiceSpy = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize in create mode by default', () => {
    expect(component.isEditMode).toBeFalse();
    expect(component.user).toBeDefined();
    expect(component.user.id).toBe('');
    expect(component.user.name).toBe('');
    expect(component.user.email).toBe('');
  });

  it('should call createUser when submitting in create mode', () => {
    const testUser = new User('test-id', 'Test User', 'test@example.com');
    component.user = testUser;
    userServiceSpy.createUser.and.returnValue(of(testUser));

    component.onSubmit();

    expect(userServiceSpy.createUser).toHaveBeenCalledWith(testUser);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/users']);
  });

  // Additional tests would be added for edit mode, error handling, etc.
});
