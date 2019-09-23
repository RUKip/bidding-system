import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Settings} from './settings';
import {concat} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  productId;
  http;
  settings;
  constructor(http: HttpClient, productId: number) {
    this.http = http;
    this.productId = productId;
    this.settings = new Settings();
  }

  getProductData(id) {
    const params = new HttpParams().set('id', id);
      return this.http.get(concat(this.settings.defaultUrl, '/api/product/${id}'), {responseType: 'json', params});
  }

  bid(productId, price) {
    // Send request!
  }
}
