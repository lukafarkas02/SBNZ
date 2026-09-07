import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminWarningsComponent } from './admin-warnings.component';

describe('AdminWarningsComponent', () => {
  let component: AdminWarningsComponent;
  let fixture: ComponentFixture<AdminWarningsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminWarningsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminWarningsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
