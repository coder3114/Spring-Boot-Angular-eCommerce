import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Cart } from 'src/app/common/cart';
import { CartService } from 'src/app/services/cart.service';
import { AuthService } from '@auth0/auth0-angular';
import { CartItem } from 'src/app/common/cart-item';

@Component({
  selector: 'app-cart-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart-page.component.html',
  styleUrl: './cart-page.component.css',
})
export class CartPageComponent {
  constructor(private cartService: CartService, public auth: AuthService) {}

  cart: Cart[] = [];
  cartItems: CartItem[] = [];
  totalQuantity: number = 0;
  totalPrice: number = 0;

  ngOnInit(): void {
    this.auth.user$.subscribe((user) => {
      if (user && user.sub) {
        const userId = user.sub;
        this.cartService.getCart(userId).subscribe((data: Cart[]) => {
          this.cart = data;

          // Group items by product ID
          const groupedItems = this.groupItemsByProductId();

          // Create CartItem objects directly using map
          this.cartItems = groupedItems.map((group) => {
            const firstItem = group[0];
            return new CartItem(firstItem, group.length);
          });

          this.totalQuantity = this.cart.length;
          // the accumulator starts with an initial value of 0
          // for each cart item, the product's unit price is added to the total
          this.totalPrice = +this.cart
            .reduce((total: number, cartItem: Cart) => {
              return total + cartItem.product.unitPrice;
            }, 0)
            .toFixed(2);

          console.log('cart items');
          console.log(this.cartItems);
        });
      }
    });
  }

  // Group items by product ID
  groupItemsByProductId(): Cart[][] {
    const groupedItemsMap = new Map<number, Cart[]>();
    this.cart.forEach((cartItem) => {
      const productId = cartItem.product.id;
      const group = groupedItemsMap.get(productId) || [];
      group.push(cartItem);
      groupedItemsMap.set(productId, group);
    });
    return Array.from(groupedItemsMap.values());
  }

  checkout() {}
}
