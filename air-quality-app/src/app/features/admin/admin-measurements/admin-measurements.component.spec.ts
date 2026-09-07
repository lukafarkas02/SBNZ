import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMeasurementsComponent } from './admin-measurements.component';

describe('AdminMeasurementsComponent', () => {
  let component: AdminMeasurementsComponent;
  let fixture: ComponentFixture<AdminMeasurementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminMeasurementsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminMeasurementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
