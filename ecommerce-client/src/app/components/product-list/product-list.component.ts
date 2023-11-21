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
  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.listProducts();
  }

  listProducts() {
    this.productService.getProductList().subscribe((data) => {
      this.allProducts = data;
      this.filteredProducts = data;
      this.route.queryParams.subscribe((params) => {
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
}
