import { Component, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';
import { CartService } from 'src/app/services/cart.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-cart-status',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart-status.component.html',
  styleUrl: './cart-status.component.css',
})
export class CartStatusComponent implements OnDestroy {
  constructor(
    private cartService: CartService,
    private route: ActivatedRoute,
    public auth: AuthService,
    private sharedService: SharedService
  ) {
    // Subscribe to cart items changes
    this.subscription = this.sharedService.cartItems$.subscribe((count) => {
      this.totalQuantity = count;
    });
  }

  totalQuantity: number = 0;
  totalPrice: number = 0;
  private subscription: Subscription;

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      this.getCartDetail();
    });
  }

  getCartDetail() {
    this.auth.user$.subscribe(
      (user) => {
        if (user && user.sub) {
          const userId = user.sub;
          console.log('testing ✅');
          console.log(userId);
          this.cartService.getCart(userId).subscribe(
            (data: any) => {
              this.totalQuantity = data.length;
              // the accumulator starts with an initial value of 0
              // for each cart item, the product's unit price is added to the total
              this.totalPrice = data.reduce(
                (total: number, cartItem: { product: { unitPrice: any } }) => {
                  return total + cartItem.product.unitPrice;
                },
                0
              );
              console.log('🛒 cart item quantity');
              console.log(this.totalQuantity);
              console.log('💲 total price', this.totalPrice);
            },
            (error) => {
              console.error('Error getting cart:', error);
            }
          );
        }
      },
      (error) => {
        console.error('Error getting user:', error);
      }
    );
  }

  ngOnDestroy(): void {
    // Unsubscribe to prevent memory leaks
    this.subscription.unsubscribe();
  }
}
