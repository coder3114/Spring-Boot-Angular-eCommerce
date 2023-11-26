import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
import { Product } from 'src/app/common/product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  allProducts: Product[] = [];
  filteredProducts: Product[] = [];
  searchMode: boolean = false;
  cartItems: any;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private route: ActivatedRoute,
    public auth: AuthService,
    private sharedService: SharedService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
  }

  listProducts() {
    // this.searchMode = this.route.snapshot.paramMap.has('keyword');
    this.route.paramMap.subscribe((param) => {
      this.searchMode = param.has('keyword');
    });
    if (this.searchMode) {
      this.handleSearchProducts();
    } else this.handleListProduct();
  }

  filterProducts(meal: object) {
    this.filteredProducts = []; // Reset filteredProducts before applying filters
    for (let mealType of Object.values(meal)) {
      let addedProducts = this.allProducts.filter(
        (product) => product.mealType === mealType
      );
      // use the spread operator (...) to create a new array that merge the two arrays
      this.filteredProducts = [...this.filteredProducts, ...addedProducts];
    }
  }

  handleListProduct() {
    this.productService.getProductList().subscribe((data) => {
      this.allProducts = data;

      this.route.queryParams.subscribe((params) => {
        if (params['mealTypes'] != null) {
          let mealTypes = params['mealTypes'];
          if (Object.keys(mealTypes).length !== 0) {
            this.filterProducts(mealTypes);
          } else {
            this.filteredProducts = this.allProducts;
          }
        } else {
          this.filteredProducts = this.allProducts;
        }
      });
    });
  }

  handleSearchProducts() {
    let theKeyword: string = '';
    let keyword = this.route.snapshot.paramMap.get('keyword');
    if (keyword) {
      theKeyword = keyword;
    }
    this.productService.searchProducts(theKeyword).subscribe((data) => {
      this.filteredProducts = data;
    });
  }

  addToCart(productId: number) {
    console.log(`Add to cart: ${productId}`);
    this.auth.user$.subscribe(
      (user) => {
        if (user && user.sub) {
          const userId = user.sub;
          console.log('testing ✅');
          console.log(userId);
          this.cartService.addToCart(productId, userId).subscribe(
            (data: any) => {
              this.cartService.getCart(userId).subscribe((data: any) => {
                this.cartItems = data;
                console.log(this.cartItems);
                this.sharedService.updateCartItems(this.cartItems);
              });
            },
            (error) => {
              console.error('Error adding to cart:', error);
            }
          );
        }
      },
      (error) => {
        console.error('Error getting user:', error);
      }
    );
  }
}
