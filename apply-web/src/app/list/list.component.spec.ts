import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPlanetsComponent } from './list.component';

describe('ListComponent', () => {
  let component: ListPlanetsComponent;
  let fixture: ComponentFixture<ListPlanetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListPlanetsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListPlanetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
