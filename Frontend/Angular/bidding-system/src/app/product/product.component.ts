import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProductService} from '../product.service';
import {Product} from '../product';
import {Observable, Subscription} from 'rxjs';
import {Bid} from '../bid';
import {startWith} from 'rxjs/operators';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
  providers: [ProductService]
})
export class ProductComponent implements OnInit, OnDestroy {
  bids: Observable<string[]>;
  currentBid: string;
  productId;
  product: Product;
  private bidSub: Subscription;
  bid: Bid;

  constructor(private route: ActivatedRoute, private productService: ProductService) { }

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
    this.bidSub = this.productService.currentBid.pipe(
      startWith({ id: '', value: 'Make a bid'})
    ).subscribe(bid => this.currentBid = bid.id);
  }

  ngOnDestroy() {
    this.bidSub.unsubscribe();
  }

  editBid() {
    this.productService.bid(this.bid);
  }

}
