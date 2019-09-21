import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  initial_container: Container = {id: 1, name: 'Emtpy', status: 'No status'};
  containers = [this.initial_container];
  constructor() { }

  ngOnInit() {
  }

}

export class Container {
  id: number;
  name: string;
  status: string;
}
