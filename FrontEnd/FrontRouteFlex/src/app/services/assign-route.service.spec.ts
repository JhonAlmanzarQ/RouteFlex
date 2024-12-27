import { TestBed } from '@angular/core/testing';

import { AssignRouteService } from './assign-route.service';

describe('AssignRouteService', () => {
  let service: AssignRouteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssignRouteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
