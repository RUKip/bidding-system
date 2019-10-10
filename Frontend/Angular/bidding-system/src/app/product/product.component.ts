import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProductService} from '../product.service';
import {Product} from '../product';
import {Observable, Subscription} from 'rxjs';
import {Bid} from '../bid';
import {BidService} from '../bid.service';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
  providers: [ProductService]
})
export class ProductComponent implements OnInit, OnDestroy {
  bids: Observable<Bid[]>;
  currentProduct: Product;
  productId: string;
  product: Product;
  private bidSub: Subscription;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private productService: ProductService,
    private bidService: BidService) { }

    bidForm;
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.productId = params.get('productId');
      console.log(this.productId);

      this.productService.getProductData(this.productId)
        .subscribe((data: JSON) => {
          console.log(data);
          const element = data[0];
          this.product = {
            id: element['_id']['$oid'],
            name: element['name'],
            description: element['description']
          };
        });
    });

    //TODO: get Bids by id on start, from the web socket
    this.bidSub = this.bidService.currentProduct.subscribe(product => this.currentProduct = product);

    this.bidForm = this.formBuilder.group({
      price: '0'
    });
  }

  ngOnDestroy() {
    this.bidSub.unsubscribe();
  }

  makeBid(bid) {
    this.bidService.bid(bid);
  }

  onSubmit(price) {
    const bid: Bid = { productId: this.productId, price };
    this.makeBid(bid);
    this.bidForm.reset();
  }

}
