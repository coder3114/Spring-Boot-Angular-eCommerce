// cart.service.ts

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private cartItemsSubject: BehaviorSubject<number> =
    new BehaviorSubject<number>(0);
  cartItems$: Observable<number> = this.cartItemsSubject.asObservable();

  updateCartItems(count: number): void {
    console.log('subscribed✅');
    console.log(count);
    this.cartItemsSubject.next(count);
  }
}
