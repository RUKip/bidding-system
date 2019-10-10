import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import {ProductService} from '../product.service';

@Component({
  selector: 'app-sell',
  templateUrl: './sell.component.html',
  styleUrls: ['./sell.component.css']
})
export class SellComponent implements OnInit {
  productService;
  sellForm;
  constructor(private formBuilder: FormBuilder, private productSerivce: ProductService) {
    this.productService = productSerivce;
    this.sellForm = this.formBuilder.group({
      name: '',
      description: ''
    });
  }

  onSubmit(productData) {
    const json = JSON.stringify(productData);
    this.productSerivce.createProduct(json).subscribe();
    this.sellForm.reset();
  }

  ngOnInit() {
  }

}
