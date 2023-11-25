import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
import { Product } from 'src/app/common/product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

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
    public auth: AuthService
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

  handleListProduct() {
    this.productService.getProductList().subscribe((data) => {
      this.allProducts = data;
      this.filteredProducts = data;

      this.route.queryParams.subscribe((params) => {
        if (params['mealTypes'] != null) {
          let mealTypes = params['mealTypes'];
          if (Object.keys(mealTypes).length != 0) {
            this.filteredProducts = [];
            // call listProducts from here with the new params,
            // then by setting this.products to something else it will make the content re - render
            // or filter products here so don't have to do another sql call
            this.filterProducts(mealTypes);
          } else {
            this.filteredProducts = this.allProducts;
          }
        }
      });
    });
  }

  filterProducts(meal: object) {
    for (let mealType of Object.values(meal)) {
      let addedProducts = this.allProducts.filter(
        (product) => product.mealType === mealType
      );
      for (let addProduct of addedProducts) {
        this.filteredProducts.push(addProduct);
      }
    }
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
        if (user) {
          const userId = user.sub;
          console.log('testing ✅');
          console.log(userId);
          this.cartService.addToCart(productId, userId).subscribe(
            (data: any) => {
              this.cartItems = data;
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
