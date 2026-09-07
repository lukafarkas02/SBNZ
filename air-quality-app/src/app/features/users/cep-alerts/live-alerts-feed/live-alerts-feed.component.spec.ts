import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveAlertsFeedComponent } from './live-alerts-feed.component';

describe('LiveAlertsFeedComponent', () => {
  let component: LiveAlertsFeedComponent;
  let fixture: ComponentFixture<LiveAlertsFeedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LiveAlertsFeedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LiveAlertsFeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
