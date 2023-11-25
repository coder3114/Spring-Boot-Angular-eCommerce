import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080/api';

  addToCart(productId: number, requestBody: any): Observable<any> {
    return this.httpClient.post(
      `${this.baseUrl}/addToCart/${productId}`,
      requestBody
    );
  }

  getCart() {}

  removeFromCart() {}

  computeCartTotals() {}
}
