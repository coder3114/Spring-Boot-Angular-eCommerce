import { Component, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '@auth0/auth0-angular';
import { CartService } from 'src/app/services/cart.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { SharedService } from 'src/app/services/shared.service';
import { Router } from '@angular/router';
import { Cart } from 'src/app/common/cart';
import { MatSnackBar } from '@angular/material/snack-bar';

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
    public auth: AuthService,
    private sharedService: SharedService,
    public router: Router,
    private snackBar: MatSnackBar
  ) {
    // Subscribe to cart items changes
    this.subscription = this.sharedService.cartItems$.subscribe((items) => {
      this.cartItems = items;
      this.totalQuantity = this.cartItems.length;
      // the accumulator starts with an initial value of 0
      // for each cart item, the product's unit price is added to the total
      this.totalPrice = +items
        .reduce((total: number, cartItem: Cart) => {
          return total + cartItem.product.unitPrice;
        }, 0)
        .toFixed(2);
    });

    // Subscribe to authentication state changes
    this.auth.user$.subscribe((user) => {
      if (user && user.sub) {
        const userId = user.sub;
        this.refreshCart(userId);
      }
    });
  }

  cartItems: Cart[] = [];
  totalQuantity: number = this.cartItems.length;
  totalPrice: number = 0;
  private subscription: Subscription;

  ngOnInit() {
    this.getCartDetail();
  }

  getCartDetail() {
    this.auth.user$.subscribe(
      (user) => {
        if (user && user.sub) {
          const userId = user.sub;
          this.cartService.getCart(userId).subscribe(
            (data: any) => {
              // Update cart information when cart data is received
              this.refreshCart(userId);
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

  refreshCart(userId: string) {
    this.cartService.getCart(userId).subscribe(
      (data: any) => {
        // Update cart information when cart data is received
        this.totalQuantity = data.data.length;

        console.log(data.data);
        // update cart data to read .data after improving the API responseEntity
        // Calculate total price based on the received cart data
        this.totalPrice = +data.data
          .reduce((total: number, cartItem: Cart) => {
            return total + cartItem.product.unitPrice;
          }, 0)
          .toFixed(2);
      },
      (error) => {
        console.error('Error getting cart:', error);
      }
    );
  }

  navigateToCartPage() {
    this.router.navigate(['/cart']);
  }

  showLoginMessage(): void {
    this.snackBar.open('Please log in to view your cart.', 'Close', {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }

  ngOnDestroy(): void {
    // Unsubscribe to prevent memory leaks
    this.subscription.unsubscribe();
  }
}
