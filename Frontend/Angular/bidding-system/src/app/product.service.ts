import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Settings } from './settings';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  http;
  settings;
  constructor(http: HttpClient) {
    this.http = http;
    this.settings = new Settings();
  }

  getProductData(id: string) {
    const params = new HttpParams().set('id', id);
    const url: string = 'http://' + this.settings.defaultUrl + '/api/product/' + id;
    return this.http.get(url);
  }

  bid(productId, price) {
    // Send request!
  }
}
