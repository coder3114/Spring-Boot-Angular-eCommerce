import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cart } from '../common/cart';

@Injectable({
  providedIn: 'root', // this tells Angular to provide the service in the root injector.
})
export class CartService {
  constructor(private httpClient: HttpClient) {} // inject HttpClient service into the CartService class

  private baseUrl = 'http://localhost:8080/api';

  addToCart(productId: number, requestBody: any): Observable<any> {
    return this.httpClient.post(
      `${this.baseUrl}/addToCart/${productId}`,
      requestBody
    );
  }

  getCart(userId: string): Observable<Cart[]> {
    const params = { userId: userId };
    return this.httpClient.get<Cart[]>(`${this.baseUrl}/cart`, {
      params,
    });
  }

  removeFromCart(productId: number, userId: string) {
    const params = { userId: userId };
    return this.httpClient.delete(`${this.baseUrl}/cart/${productId}`, {
      params,
    });
  }
}
