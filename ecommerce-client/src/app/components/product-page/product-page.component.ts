import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Params, RouterModule } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-product-page',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatFormFieldModule, RouterModule],
  templateUrl: './product-page.component.html',
  styleUrl: './product-page.component.css',
})
export class ProductPageComponent implements OnInit {
  productDetail?: any;
  constructor(
    private route: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    let productId = this.route.snapshot.params['id'];
    this.getProductDetailById(productId);
  }

  getProductDetailById(id: number) {
    this.productService.getProductDetailById(id).subscribe((res) => {
      this.productDetail = res;
      console.log(this.productDetail);
    });
  }
}
