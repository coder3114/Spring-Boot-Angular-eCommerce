import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'ecommerce-client';

  @ViewChild('dropdown', { static: false }) dropdown?: ElementRef;

  mealTypes = new FormControl('');

  mealTypeList: string[] = [
    'Breakfast',
    'Lunch',
    'Dinner',
    'Snacks',
    'Desserts',
  ];
}
