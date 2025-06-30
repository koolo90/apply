import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCelestialBodiesComponent } from './list.component';

describe('ListComponent', () => {
  let component: ListCelestialBodiesComponent;
  let fixture: ComponentFixture<ListCelestialBodiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListCelestialBodiesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListCelestialBodiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
