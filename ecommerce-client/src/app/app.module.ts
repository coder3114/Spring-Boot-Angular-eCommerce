import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ProductService } from './services/product.service';
import { ProductListComponent } from './components/product-list/product-list.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgIf, NgFor } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatMenuModule } from '@angular/material/menu';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';

import { MatCardModule } from '@angular/material/card';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { SideNavComponent } from './components/side-nav/side-nav.component';
import { ProductPageComponent } from './components/product-page/product-page.component';
import { SearchComponent } from './components/search/search.component';
import { CartStatusComponent } from './components/cart-status/cart-status.component';

import { AuthModule } from '@auth0/auth0-angular';
import { AuthButtonComponent } from './components/auth-button/auth-button.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CartPageComponent } from './components/cart-page/cart-page.component';

const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: 'search/:keyword', component: ProductListComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'products/:id', component: ProductPageComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'cart', component: CartPageComponent },
  {
    path: '**',
    component: NotFoundComponent,
  },
];
@NgModule({
  declarations: [AppComponent, ProductListComponent],
  providers: [ProductService],
  bootstrap: [AppComponent],
  imports: [
    BrowserModule,
    HttpClientModule, // register the HttpClientModule to provide the HttpClient service
    MatFormFieldModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    NgFor,
    MatMenuModule,
    MatListModule,
    MatButtonModule,
    MatCardModule,
    SideNavComponent,
    BrowserAnimationsModule,
    RouterModule.forRoot(routes),
    SearchComponent,
    CartStatusComponent,
    AuthButtonComponent,
    // Import the module into the application, with configuration
    AuthModule.forRoot({
      domain: 'dev-izrt4pljw28bbb8g.us.auth0.com',
      clientId: '6d8fwF2ry5nxqfZvcAOvHT0HNWHP5m5E',
      authorizationParams: {
        redirect_uri: window.location.origin,
      },
    }),
  ],
})
export class AppModule {}
