import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { ProductListComponent } from './product-list/product-list.component';
import { AdminComponent } from './admin/admin.component';
import { MainComponent } from './main/main.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ProductComponent } from './product/product.component';
import {ProductService} from './product.service';
import {ProductsService} from './products.service';

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    AdminComponent,
    MainComponent,
    ProductComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: MainComponent },
      { path: 'admin', component: AdminComponent },
      { path: 'product/:productId', component: ProductComponent },
      ]),
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot()
  ],
  providers: [
    ProductService,
    ProductsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
