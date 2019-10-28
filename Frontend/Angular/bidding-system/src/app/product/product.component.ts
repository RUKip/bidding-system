import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProductService} from '../product.service';
import {Product} from '../product';
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
  bids: number[];
  productId: string;
  product: Product;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private productService: ProductService,
    private bidService: BidService) { }

    bidForm;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.productId = params.get('productId');
      this.bidService.currentBids.subscribe(bids => this.bids = bids);
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
    this.bidForm = this.formBuilder.group({
      price: '0'
    });

    this.bidService.socket.addEventListener('open', event => {
        this.bidService.socket.send('{"init":"' + this.productId + '"}');
    }
    );
  }

  ngOnDestroy() {
    this.bidService.socket.close();
  }

  makeBid(bid) {
    this.bidService.bid(bid);
  }

  onSubmit(value) {
    const bidValue: string = value.price.toString();
    const bid: Bid = {productId: this.productId, price: bidValue};
    this.makeBid(bid);
    this.bidForm.reset();
  }

}
