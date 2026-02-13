import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { availabilityResolver } from './availability-resolver';

describe('availabilityResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => availabilityResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
