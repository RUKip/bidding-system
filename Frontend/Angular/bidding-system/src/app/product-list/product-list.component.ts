import { Component, OnInit } from '@angular/core';
import {ProductsService} from '../products.service';
import {Product} from '../product';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products = [];

  constructor(private productsSerivce: ProductsService) { }

  ngOnInit() {
    this.getProducts();
  }

  getProducts() {
    this.productsSerivce.requestProducts()
      .subscribe((data: JSON[]) => {
          this.products = [];
          console.log(data);

          data.forEach(element => {
            const product: Product = {
              id: element['_id']['$oid'],
              name: element['name'],
              description: element['description']
            };
            this.products.push(product);
          });
        }
      );
  }

}
