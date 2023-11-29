import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../common/product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080/api';

  getProductList(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.baseUrl}/products`);
  }

  searchProducts(theKeyword: string): Observable<Product[]> {
    const searchUrl = `${this.baseUrl}/search/${theKeyword}`;
    return this.httpClient.get<Product[]>(searchUrl);
  }

  getProductDetailById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(`${this.baseUrl}/products/` + id);
  }
}
