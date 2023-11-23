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
    console.log(this.baseUrl);
    console.log(this.httpClient.get<Product[]>(this.baseUrl));
    return this.httpClient.get<Product[]>(this.baseUrl);
    //   .pipe(map((response) => response.products));
  }

  searchProducts(theKeyword: string): Observable<Product[]> {
    const searchUrl = `http://localhost:8080/api/search/${theKeyword}`;

    console.log(this.httpClient.get<Product[]>(searchUrl));
    return this.httpClient.get<Product[]>(searchUrl);
    // .pipe(map((response) => response.products));
  }

  getProductDetailById(id: number): Observable<any> {
    console.log('getProductDetailById' + this.baseUrl + '/' + id);
    return this.httpClient.get<Product>(
      'http://localhost:8080/api/products/' + id
    );
  }
}
