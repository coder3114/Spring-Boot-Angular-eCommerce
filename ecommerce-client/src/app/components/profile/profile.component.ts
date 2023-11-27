import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';
import { map } from 'rxjs/operators';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent implements OnInit {
  profileJson: string = '';

  constructor(public auth: AuthService) {}

  ngOnInit(): void {
    // $ indicate that user is an observable
    this.auth.user$.subscribe(
      //Decoded ID Token
      // format the JSON with an indentation of 2 spaces
      (profile) => (this.profileJson = JSON.stringify(profile, null, 2))
    );
  }
}
