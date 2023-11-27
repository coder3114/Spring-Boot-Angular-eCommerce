import { Component, Inject } from '@angular/core';
import { CommonModule, DOCUMENT } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';
import { RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-auth-button',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './auth-button.component.html',
  styleUrl: './auth-button.component.css',
})
export class AuthButtonComponent {
  constructor(
    //This injects the global document object into the component to interact with the DOM directly if needed
    @Inject(DOCUMENT) public document: Document,
    public auth: AuthService,
    private snackBar: MatSnackBar
  ) {}

  showLoginMessage(): void {
    this.snackBar.open('Please log in to view your profile.', 'Close', {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }
}
