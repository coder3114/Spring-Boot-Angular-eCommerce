import { Component, HostBinding, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SelectMultipleExample } from '../select-multiple/select-multiple.component';
import { SidenavService } from './side-nav.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-side-nav',
  standalone: true,
  imports: [CommonModule, SelectMultipleExample, RouterModule],
  templateUrl: './side-nav.component.html',
  styleUrl: './side-nav.component.css',
})
export class SideNavComponent {
  resizingEvent = {
    isResizing: false,
    startingCursorX: 0,
    startingWidth: 0,
  };

  constructor(public sidenavService: SidenavService, public router: Router) {}

  @HostBinding('class.resizing')
  get isResizing(): boolean {
    return this.resizingEvent.isResizing;
  }

  startResizing(event: MouseEvent): void {
    this.resizingEvent = {
      isResizing: true,
      startingCursorX: event.clientX,
      startingWidth: this.sidenavService.sidenavWidth,
    };
  }

  @HostListener('window:mousemove', ['$event'])
  updateSidenavWidth(event: MouseEvent) {
    // No need to even continue if we're not resizing
    if (!this.resizingEvent.isResizing) {
      return;
    }

    // 1. Calculate how much mouse has moved on the x-axis
    const cursorDeltaX = event.clientX - this.resizingEvent.startingCursorX;

    // 2. Calculate the new width according to initial width and mouse movement
    const newWidth = this.resizingEvent.startingWidth + cursorDeltaX;

    // 3. Set the new width
    this.sidenavService.setSidenavWidth(newWidth);
  }

  @HostListener('window:mouseup')
  stopResizing() {
    this.resizingEvent.isResizing = false;
  }
}
