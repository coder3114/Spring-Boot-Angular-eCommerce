import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Cart } from 'src/app/common/cart';
import { CartService } from 'src/app/services/cart.service';
import { AuthService } from '@auth0/auth0-angular';
import { CartItem } from 'src/app/common/cart-item';
import { HttpClient } from '@angular/common/http';
import { Stripe, loadStripe } from '@stripe/stripe-js';
import { SharedService } from 'src/app/services/shared.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-cart-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart-page.component.html',
  styleUrl: './cart-page.component.css',
})
export class CartPageComponent {
  constructor(
    private cartService: CartService,
    public auth: AuthService,
    private httpClient: HttpClient,
    private sharedService: SharedService,
    private snackBar: MatSnackBar
  ) {}

  cart: Cart[] = [];
  cartItems: CartItem[] = [];
  totalQuantity: number = 0;
  totalPrice: number = 0;
  stripe: Stripe | null = null;

  ngOnInit(): void {
    this.auth.user$.subscribe((user) => {
      if (user && user.sub) {
        const userId = user.sub;
        this.refreshCart(userId);
      }
    });

    this.loadStripe();
  }

  // Group items by product ID in the frontend

  // SELECT product_id, ARRAY_AGG(cart_item) AS grouped_items
  // The ARRAY_AGG function aggregates rows into an array, grouping them by the product_id.
  // FROM cart
  // WHERE user_id = user.sub in frontend
  // GROUP BY product_id;

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

  refreshCart(userId: string) {
    this.cartService.getCart(userId).subscribe(
      (data: Cart[]) => {
        this.cart = data;
        this.sharedService.updateCartItems(this.cart);
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
      },
      (error) => {
        console.error('❌ Error refreshing cart:', error);
      }
    );
  }

  removeProduct(productId: number) {
    this.cartService.removeFromCart(productId).subscribe(
      () => {
        // After successful removal, refresh the cart items
        this.auth.user$.subscribe((user) => {
          if (user && user.sub) {
            const userId = user.sub;
            this.refreshCart(userId);
          }
        });
      },
      (error) => {
        console.error('❌ Error deleting product:', error);
      }
    );
  }

  loadStripe() {
    loadStripe(
      'pk_test_51O72RLBgJydn0KVff08ILsMhps6QF5t2Ju3RXuNAxx23Xcox0rKQFODGZ6afpihh6tTEGHMJZSTRPdB4hoDAArG800CuAsuJor'
    ).then((stripe) => {
      this.stripe = stripe;
    });
  }

  handleCheckout() {
    if (this.stripe) {
      this.httpClient
        .post('http://localhost:8080/api/payment-intent', {
          amount: this.totalPrice,
          currency: 'gbp',
        })
        .subscribe((session: any) => {
          // Redirect to Checkout
          this.stripe!.redirectToCheckout({ sessionId: session.id })
            .then((result: any) => {
              if (result.error) {
                console.error(result.error.message);
              }
            })
            .catch((error: any) => {
              console.error(error);
            });
        });
    } else {
      console.error('Stripe is not initialized');
    }
  }

  // need to use webhooks on server side to handle actions after the user returns from the Stripe Checkout page
  // if checkout.session.completed -> clearing the shopping cart, popup message, or sending confirmation emails

  // Payment successful, clear the shopping cart
  // this.clearShoppingCart();
  // this.showPaymentSuccessMessage();

  clearShoppingCart() {
    this.cart = [];
    this.cartItems = [];
    this.totalQuantity = 0;
    this.totalPrice = 0;
    this.sharedService.updateCartItems(this.cart);
  }

  showPaymentSuccessMessage(): void {
    this.snackBar.open(
      'You have successfully ordered your healthy meals. Enjoy!',
      'Close',
      {
        duration: 3000,
        verticalPosition: 'top',
        horizontalPosition: 'center',
      }
    );
  }
}
