import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  title = 'Auction system';
  constructor() { }

  ngOnInit() {
  }

  bid(productId, price) {
    // Send request!
  }
}
