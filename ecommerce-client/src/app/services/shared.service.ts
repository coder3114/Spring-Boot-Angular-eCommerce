// cart.service.ts

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Cart } from '../common/cart';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private cartItemsSubject: BehaviorSubject<Cart[]> = new BehaviorSubject<
    Cart[]
  >([]);
  cartItems$: Observable<Cart[]> = this.cartItemsSubject.asObservable();

  updateCartItems(items: Cart[]): void {
    this.cartItemsSubject.next(items);
  }
}
