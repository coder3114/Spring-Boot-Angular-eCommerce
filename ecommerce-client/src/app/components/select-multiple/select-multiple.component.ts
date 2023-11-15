import { Component } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

/** @title Select with multiple selection */
@Component({
  selector: 'app-select-multiple',
  templateUrl: 'select-multiple.component.html',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
  ],
})
export class SelectMultipleExample {
  meals = new FormControl('');
  mealList: string[] = ['Breakfast', 'Lunch', 'Dinner'];
}
