import { Cart } from './cart';

export class CartItem {
  productId: number;
  imageUrl: string;
  name: string;
  unitPrice: number;

  quantity: number;
  totalPrice: number;

  constructor(cart: Cart, quantity: number) {
    this.productId = cart.product.id;
    this.name = cart.product.name;
    this.imageUrl = cart.product.imageUrl;
    this.unitPrice = cart.product.unitPrice;

    this.quantity = quantity;
    this.totalPrice = this.quantity * this.unitPrice;
  }
}
