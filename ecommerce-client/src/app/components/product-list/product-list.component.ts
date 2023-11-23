import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/common/product';
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

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
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
      console.log(data);
      this.allProducts = data;
      this.filteredProducts = data;

      this.route.queryParams.subscribe((params) => {
        if (params['mealTypes'] != null) {
          let mealTypes = params['mealTypes'];
          console.log('mealTypes ' + typeof mealTypes);
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
    console.log(this.route.snapshot.paramMap.get('keyword'));
    let keyword = this.route.snapshot.paramMap.get('keyword');
    if (keyword) {
      theKeyword = keyword;
    }
    console.log('the key word is ' + theKeyword);
    this.productService.searchProducts(theKeyword).subscribe((data) => {
      this.filteredProducts = data;
      console.log(this.filteredProducts);
    });
    console.log(this.filteredProducts);
  }

  addToCart(product: Product) {
    console.log(`Add to cart: ${product.name} ${product.unitPrice}`);
  }
}
