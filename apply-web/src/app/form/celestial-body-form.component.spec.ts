import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CelestialBodyFormComponent } from './celestial-body-form.component';

describe('FormComponent', () => {
  let component: CelestialBodyFormComponent;
  let fixture: ComponentFixture<CelestialBodyFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CelestialBodyFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CelestialBodyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
