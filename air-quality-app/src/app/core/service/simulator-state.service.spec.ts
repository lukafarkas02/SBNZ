import { TestBed } from '@angular/core/testing';

import { SimulatorStateService } from './simulator-state.service';

describe('SimulatorStateService', () => {
  let service: SimulatorStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SimulatorStateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
