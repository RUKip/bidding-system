import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) { }

  requestProducts() {
    return this.http.get('http://localhost/api/products/get'); //does work with localhost...
  }
}
