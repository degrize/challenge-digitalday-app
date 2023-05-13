import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreoccupationComponent } from './preoccupation.component';

describe('PreoccupationComponent', () => {
  let component: PreoccupationComponent;
  let fixture: ComponentFixture<PreoccupationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PreoccupationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PreoccupationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
