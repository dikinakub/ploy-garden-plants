import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockAddComponent } from './stock-add.component';

describe('StockAddComponent', () => {
  let component: StockAddComponent;
  let fixture: ComponentFixture<StockAddComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StockAddComponent]
    });
    fixture = TestBed.createComponent(StockAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
