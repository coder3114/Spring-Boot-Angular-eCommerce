import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../common/product';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl = 'http://localhost:8080/api/products';

  getProductList(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.baseUrl);
    //   .pipe(map((response) => response.products));
  }

  searchProducts(theKeyword: string): Observable<Product[]> {
    const searchUrl = `http://localhost:8080/api/search/${theKeyword}`;
    return this.httpClient.get<Product[]>(searchUrl);
    // .pipe(map((response) => response.products));
  }

  getProductDetailById(id: number): Observable<any> {
    return this.httpClient.get<Product>(
      'http://localhost:8080/api/products/' + id
    );
  }
}
