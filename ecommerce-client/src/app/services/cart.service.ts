import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cart } from '../common/cart';

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

  getCart(userId: string): Observable<Cart[]> {
    const params = { userId: userId };
    console.log('param is ');
    console.log(params);
    return this.httpClient.get<Cart[]>('http://localhost:8080/api/cart', {
      params,
    });
  }

  removeFromCart(productId: number) {
    console.log(`http://localhost:8080/api/cart/${productId}`);

    return this.httpClient.delete(
      `http://localhost:8080/api/cart/${productId}`
    );
  }
}
