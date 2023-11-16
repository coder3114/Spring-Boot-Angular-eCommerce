import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSliderModule } from '@angular/material/slider';

/** @title Select with multiple selection */
@Component({
  selector: 'app-select-multiple',
  templateUrl: 'select-multiple.component.html',
  styleUrls: ['select-multiple.component.css'],
  encapsulation: ViewEncapsulation.None,
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatSliderModule,
  ],
})
export class SelectMultipleExample {
  formatLabel(value: number): string {
    if (value > 0) {
      return value + ' min';
    }

    return `🚫Lazy`;
  }
  meals = new FormControl('');
  mealList: string[] = ['Breakfast', 'Lunch', 'Dinner', 'Dessert', 'Snack'];
  cuisines = new FormControl('');
  cuisineList: string[] = [
    'Mediterranean',
    'Chinese',
    'Mexican',
    'Italian',
    'Middle Eastern',
    'Indian',
    'Greek',
    'American',
    'Japanese',
    'Thai',
    'Vietnamese',
  ];
  dietPrefs = new FormControl('');
  dietPrefsList: string[] = [
    'Vegetarian',
    'Vegan',
    'Gluten-Free',
    'Dairy-Free',
    'Nut-Free',
    'Sugar-Free',
    'Organic',
  ];
  ingredients = new FormControl('');
  ingredientsList: string[] = [
    'Tofu',
    'Avocado',
    'Kale',
    'Chickpeas',
    'Salmon',
    'Sweet Potato',
    'Spinach',
    'Almonds',
    'Oats',
    'Quinoa',
    'Greek Yogurt',
    'Coconut Milk',
    'Eggplant',
    'Cauliflower',
    'Chia Seeds',
    'Tomatoes',
    'Chicken Breast',
    'Broccoli',
    'Peppers',
    'Garlic',
    'Onions',
    'Brown Rice', // Added Brown Rice
  ];
  priceRanges = new FormControl('');
  priceRangeList: string[] = [
    'Under £10',
    '£10 - £20',
    '£20 - £50',
    '£50 - £100',
    'Over £100',
  ];
}
