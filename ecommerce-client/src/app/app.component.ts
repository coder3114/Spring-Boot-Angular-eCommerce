import { Component, ElementRef, ViewChild } from '@angular/core';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'ecommerce-client';

  @ViewChild('dropdown', { static: false }) dropdown?: ElementRef;

  resizingEvent = {
    isResizing: false,
    startingCursorX: 0,
    startingWidth: 0,
  };
}
