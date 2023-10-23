import { TestBed } from '@angular/core/testing';

import { CrudService } from './crud.service';

describe('CrudService', () => {
  let service: CrudService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrudService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

// describe('CrudService', () => {
//   beforeEach(() => TestBed.configureTestingModule({}));

//   it('should be created', () => {
//     const service: CrudService = TestBed.get(CrudService);
//     expect(service).toBeTruthy();
//   });
// });